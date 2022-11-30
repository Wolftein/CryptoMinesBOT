package com.github.wolftein.cryptomines;

import com.github.wolftein.cryptomines.contract.*;
import com.github.wolftein.cryptomines.model.Worker;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.web3j.abi.*;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.protocol.websocket.events.*;
import org.web3j.protocol.websocket.events.Log;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Async;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.wolftein.cryptomines.contract.MarketContract.EVENTBUYNFT_EVENT;
import static com.github.wolftein.cryptomines.contract.MarketContract.EVENTSELLNFT_EVENT;

public final class Crypto {

    public interface Listener {
        boolean onFilter(Worker worker);

        void onBuy(Worker worker, TransactionReceipt result);

        void onSell(Worker worker, TransactionReceipt result);

        void onSold(Worker worker);
    }

    private final static List<String> PROXIE_CONTRACT = new ArrayList<String>() {{
        {
            add("0x3949e580aDAFcFc83cE2D02de7091805323DDe84");
        }
    }};

    private final static String WALLET_CONTRACT = "0xD44FD09d74cd13838F137B590497595d6b3FEeA4";
    private final static String MARKET_CONTRACT = "0xe0Dd2E782c406914971e12243023D61acd5E2958";
    private final static String WORKER_CONTRACT = "0x6053b8FC837Dc98C54F7692606d632AC5e760488";

    private final OkHttpClient mConnector = new OkHttpClient.Builder().build();

    private final Listener mListener;

    private Web3j mBlockchain;
    private Queue<ProxieContract> mProxieContracts = new ArrayDeque<>();
    private WalletContract mWalletContract;
    private MarketContract mMarketContract;
    private WorkerContract mWorkerContract;

    private ConcurrentLinkedQueue<Worker> mPending = new ConcurrentLinkedQueue<>();

    public Crypto(Listener listener) {
        mListener = listener;
    }

    public void poll() {
        final Worker worker = mPending.poll();

        if (worker != null) {
            mMarketContract.sellNFT(BigInteger.valueOf(0), worker.getToken(), toWei(worker.getSell()))
                    .sendAsync()
                    .exceptionally(t -> null)
                    .thenAccept(t -> mListener.onSell(worker, t));
        }
    }

    public void connect(String key) {
        final WebSocketService service = new WebSocketService("wss://speedy-nodes-nyc.moralis.io/6d567f32992c38bc49563ec1/bsc/mainnet/ws", false);
        try {
            service.connect();
        } catch (ConnectException e) {
            e.printStackTrace();
        }

        //final Service service = new UnixIpcService("/mnt/disks/ssd-array/geth.ipc");

        mBlockchain = Web3j.build(service, 2500L, Async.defaultExecutorService());

        final TransactionManager manager = new RawTransactionManager(mBlockchain, Credentials.create(key), 56);

        mWalletContract = WalletContract.load(WALLET_CONTRACT, mBlockchain, manager, new CryptoGasProvider());
        try {
            //mWalletContract.approve("0x3949e580aDAFcFc83cE2D02de7091805323DDe84", toWei(100_000)).send();
            //System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String contract : PROXIE_CONTRACT) {
            mProxieContracts.add(ProxieContract.load(contract, mBlockchain, manager, new CryptoGasProvider()));
        }

        mMarketContract = MarketContract.load(MARKET_CONTRACT, mBlockchain, manager, new CryptoGasProvider());
        mWorkerContract = WorkerContract.load(WORKER_CONTRACT, mBlockchain, manager, new CryptoGasProvider());
     /*
        mMarketContract.eventBuyNFTEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST)
                .subscribe(event -> {
                    if (event.nftType.longValueExact() != 0) return; // We only care about workers

                    final Worker worker = new Worker();
                    worker.setID(event.marketId);
                    worker.setToken(event.tokenId);
                    worker.setPrice(event.price);
                    worker.setSeller(event.sellerAddress);
                    worker.setBuyer(event.buyerAddress);

                    mListener.onSold(worker);
                });

        mMarketContract.eventSellNFTEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST)
                .subscribe(event -> {
                    if (event.nftType.longValueExact() != 0) return; // We only care about workers

                    final Worker worker = new Worker();
                    worker.setID(event.marketId);
                    worker.setToken(event.tokenId);
                    worker.setPrice(event.price);
                    worker.setSeller(event.sellerAddress);
                    worker.setBuyer("0x0000000000000000000000000000000000000000");

                    final Function<WorkerContract.WorkerStruct, Worker> onCompletion = workerInfo -> {
                        worker.setLevel(workerInfo.level.longValue());
                        worker.setPower(workerInfo.minePower.longValue());
                        return worker;
                    };
                    mWorkerContract.getTokenDetails(worker.getToken()).sendAsync()
                            .thenApply(onCompletion)
                            .thenApply(mListener::onFilter).thenAccept(success -> {
                        if (success) {
                            buy(worker, t -> mListener.onBuy(worker, t));
                        }
                    });
                });
        */

        mBlockchain.logsNotifications(
                Collections.singletonList(mMarketContract.getContractAddress()),
                Collections.emptyList()).subscribe(this::onNotification);
    }

    public double getBalance(String address) {
        try {
            final EthGetBalance ethBalance = mBlockchain.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            return fromWei(ethBalance.getBalance());
        } catch (IOException exception) {
            return -1.0;
        }
    }

    public List<Worker> getList(int level, int minPower, int maxPower, int count) {
        final Request request = new Request.Builder()
                .url("https://api.cryptomines.app/api/workers?level=" + level + "&page=1&limit=" + count + "&sort=eternal&mpfrom=" + minPower + "&mpto=" + maxPower)
                .get()
                .build();

        final List<Worker> result = new LinkedList<>();

        try {
            final Response response = mConnector.newCall(request).execute();

            final JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            final JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); ++i) {
                result.add(new Worker(jsonArray.getJSONObject(i)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void getWorkers(String address, Consumer<Worker> callback) {
        mWorkerContract.getMyWorkers(address).sendAsync().thenAccept(list -> {
            for (Object token : list) {
                try {
                    final WorkerContract.WorkerStruct struct = mWorkerContract.getTokenDetails((BigInteger) token).send();

                    final Worker worker = new Worker();
                    worker.setSeller(address);
                    worker.setBuyer("0x0000000000000000000000000000000000000000");
                    worker.setToken((BigInteger) token);
                    worker.setLevel(struct.level.longValue());
                    worker.setPower(struct.minePower.longValue());

                    callback.accept(worker);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });
    }

    public List<Worker> getUnlist(String address) {
        final Request request = new Request.Builder()
                .url("https://api.cryptomines.app/api/workers/seller?sellerAddress=" + address + "&page=1&limit=1000")
                .get()
                .build();
        final List<Worker> result = new LinkedList<>();

        try {
            final Response response = mConnector.newCall(request).execute();

            final JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
            final JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); ++i) {
                final Worker worker = new Worker(jsonArray.getJSONObject(i));

                result.add(worker);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void unlist(Worker worker) {
        mMarketContract.unlistNFT(worker.getID()).sendAsync();
    }

    public void buy(Worker worker, Consumer<TransactionReceipt> callback) {
        final ProxieContract proxy = mProxieContracts.poll();
        proxy.DoBuy(
                worker.getID(),
                worker.getToken(),
                worker.getPrice()).sendAsync().exceptionally(t -> null).thenAccept(callback);
        mProxieContracts.add(proxy);
    }

    public void sell(Worker worker) {
        mPending.add(worker);
    }

    public static BigInteger toWei(double value) {
        return Convert.toWei(BigDecimal.valueOf(value), Convert.Unit.ETHER).toBigInteger();
    }

    public static double fromWei(BigInteger value) {
        return Convert.fromWei(BigDecimal.valueOf(value.longValue()), Convert.Unit.ETHER).doubleValue();
    }

    private void onNotification(LogNotification notification) {
        final Log result = notification.getParams().getResult();

        switch (result.getTopics().get(0)) {
            case "0x04bb509429865f02852b66b17030d7b33694e0c5fc3d4ebfc1a39d38fcfbcfa3": {
                final EventValues values = staticExtractEventParameters(EVENTBUYNFT_EVENT, result);

                final BigInteger nftType = (BigInteger) values.getNonIndexedValues().get(1).getValue();
                if (nftType.longValueExact() != 0) return; // We only care about workers

                final BigInteger marketId = (BigInteger) values.getNonIndexedValues().get(0).getValue();
                final BigInteger tokenId = (BigInteger) values.getNonIndexedValues().get(2).getValue();
                final String sellerAddress = (String) values.getNonIndexedValues().get(3).getValue();
                final String buyerAddress = (String) values.getNonIndexedValues().get(4).getValue();
                final BigInteger price = (BigInteger) values.getNonIndexedValues().get(5).getValue();

                final Worker worker = new Worker();
                worker.setID(marketId);
                worker.setToken(tokenId);
                worker.setPrice(price);
                worker.setSeller(sellerAddress);
                worker.setBuyer(buyerAddress);

                mListener.onSold(worker);
            }
            break;
            case "0xa90ace1ffc3b7f278ea386b7d7e3b1c04c85d2dc00489a8b463092384274d217": {
                final EventValues values = staticExtractEventParameters(EVENTSELLNFT_EVENT, result);

                final BigInteger nftType = (BigInteger) values.getNonIndexedValues().get(1).getValue();
                if (nftType.longValueExact() != 0) return; // We only care about workers

                final String sellerAddress = (String) values.getNonIndexedValues().get(3).getValue();

                final BigInteger marketId = (BigInteger) values.getNonIndexedValues().get(0).getValue();
                final BigInteger tokenId = (BigInteger) values.getNonIndexedValues().get(2).getValue();
                final BigInteger price = (BigInteger) values.getNonIndexedValues().get(4).getValue();

                final Worker worker = new Worker();
                worker.setID(marketId);
                worker.setToken(tokenId);
                worker.setPrice(price);
                worker.setSeller(sellerAddress);
                worker.setBuyer("0x0000000000000000000000000000000000000000");

                final Function<WorkerContract.WorkerStruct, Worker> onCompletion = workerInfo -> {
                    worker.setLevel(workerInfo.level.longValue());
                    worker.setPower(workerInfo.minePower.longValue());
                    return worker;
                };
                mWorkerContract.getTokenDetails(worker.getToken()).sendAsync()
                        .thenApply(onCompletion)
                        .thenApply(mListener::onFilter).thenAccept(success -> {
                    if (success) {
                        buy(worker, t -> mListener.onBuy(worker, t));
                    }
                });
            }
            break;
        }
    }

    @SuppressWarnings("rawtypes")
    private EventValues staticExtractEventParameters(Event event, Log log) {
        final List<String> topics = log.getTopics();

        if (topics == null || topics.size() == 0) {
            return null;
        }

        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues =
                FunctionReturnDecoder.decode(log.getData(), event.getParameters());

        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value =
                    FunctionReturnDecoder.decodeIndexedValue(
                            topics.get(i + 1), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }
}
