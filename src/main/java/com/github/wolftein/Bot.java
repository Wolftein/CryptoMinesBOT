package com.github.wolftein;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.github.wolftein.cryptomines.CryptoMinesAPI;
import com.github.wolftein.cryptomines.model.Worker;
import org.decimal4j.util.DoubleRounder;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class Bot implements CryptoMinesAPI.Listener {
    private final static DecimalFormat FORMAT_PRICE = new DecimalFormat("##.####");

    private final static double MAX_TRADING = 3;
    private final static double MIN_TRADING = 1;

    private final WebhookClient DISCORD;

    private final static class Catalog {
        private List<Double> mValues = new ArrayList<>();
        private double mMedian;
        private int mSkip = 0;

        public void calculateMedian() {
            if (mValues.isEmpty()) return;
            mMedian = DoubleRounder.round(Utility.median(mValues, Double::compareTo), 3);
        }

        public double getMedian() {
            return mMedian;
        }

        public int getSize() {
            return mValues.size();
        }

        public void addValue(double value) {
            mValues.add(value);
        }

        public void removeValue(double value) {
            mValues.remove(value);
        }

        public List<Double> getValues() {
            return mValues;
        }
    }

    private final String mAddress;
    private final CryptoMinesAPI mCryptoMinesAPI;
    private Map<Long, Catalog> mCatalogs = new HashMap<>();
    private final boolean mBuyingMode;
    private final boolean mUnlistMode;

    private Set<BigInteger> mSales = new ConcurrentSkipListSet<>();
    private Set<BigInteger> mBuying = new ConcurrentSkipListSet<>();

    private final ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(3);

    private static class Report {
        long Succeeds = 0;
        long Fails = 0;
        long Solds = 0;
        double Spent = 0;
        double Earned = 0;
        double Difference = 0;
    }

    private final Report mReport = new Report();
    private final Report mPreviousReport = new Report();
    private boolean mLocked = false;

    private int mSettingTradePosition = 8;
    private int mSettingTradeCount = 2;
    private double mSettingTradeMin = 0.003;
    private double mSettingTradeMax = 0.225;
    private int mSettingTradeMinMP = 75;
    private int mSettingTradeMaxMP = 105;

    public Bot(String address, String key, String endpoint, boolean buyingMode, boolean unlistMode) throws IOException {
        mAddress = address;

        WebhookClientBuilder builder = new WebhookClientBuilder(
                "https://discord.com/api/webhooks/913453765280014417/A-U2wUtCW2qR9yl5C83woABOZsj4zCETznq6clod4BZMi7h2UP0kJIdRpiKLD4Kan_ZZ");
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("DISCORD");
            return thread;
        });
        builder.setWait(true);
        DISCORD = builder.build();

        mBuyingMode = buyingMode;
        mUnlistMode = unlistMode;

        mCryptoMinesAPI = new CryptoMinesAPI(this);
        mCryptoMinesAPI.connect(endpoint, key);

        try {
            DataInputStream in = new DataInputStream(new FileInputStream("Data.bin"));
            mReport.Succeeds = in.readLong();
            mReport.Fails = in.readLong();
            mReport.Solds = in.readLong();
            mReport.Spent = in.readDouble();
            mReport.Earned = in.readDouble();
            mPreviousReport.Succeeds = mReport.Succeeds;
            mPreviousReport.Fails = mReport.Fails;
            mPreviousReport.Solds = mReport.Solds;
            mPreviousReport.Earned = mReport.Earned;
            mPreviousReport.Spent = mReport.Spent;

            mReport.Difference = mPreviousReport.Difference = 0;
            mReport.Difference = in.readDouble();
            mPreviousReport.Difference = mReport.Difference;
        } catch (IOException exception) {
        }

        Properties prop = new Properties();
        String fileName = "Bot.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);

            mSettingTradePosition = Integer.parseInt(prop.getProperty("Trade.Position"));
            mSettingTradeCount    = Integer.parseInt(prop.getProperty("Trade.Count"));
            mSettingTradeMin = Double.parseDouble(prop.getProperty("Trade.Min"));
            mSettingTradeMax = Double.parseDouble(prop.getProperty("Trade.Max"));
            mSettingTradeMinMP = Integer.parseInt(prop.getProperty("Trade.MinMP"));
            mSettingTradeMaxMP = Integer.parseInt(prop.getProperty("Trade.MaxMP"));
        } catch (IOException ex) {
        }
    }

    public void run() {

        /*
        mScheduler.scheduleAtFixedRate(() -> {
            synchronized (mReport) {
                if (mPreviousReport.Succeeds != mReport.Succeeds
                        || mPreviousReport.Fails != mReport.Fails
                        || mPreviousReport.Solds != mReport.Solds) {

                    mPreviousReport.Succeeds = mReport.Succeeds;
                    mPreviousReport.Fails = mReport.Fails;
                    mPreviousReport.Solds = mReport.Solds;
                    mPreviousReport.Earned = mReport.Earned;
                    mPreviousReport.Spent = mReport.Spent;
                    mPreviousReport.Difference = mReport.Difference;

                    double balance = mCryptoMinesAPI.getBalance(mAddress);

                    final WebhookEmbed embed = new WebhookEmbedBuilder()
                            .setColor(0xADFF2F)
                            .setTitle(new WebhookEmbed.EmbedTitle("Estado (" + (mLocked ? "Pasivo" : "Activo") + ")", null))
                            .addField(new WebhookEmbed.EmbedField(false, "Balance", FORMAT_PRICE.format(balance)))
                            .addField(new WebhookEmbed.EmbedField(false, "Compras", String.valueOf(mPreviousReport.Succeeds)))
                            .addField(new WebhookEmbed.EmbedField(false, "Fallos", String.valueOf(mPreviousReport.Fails)))
                            .addField(new WebhookEmbed.EmbedField(false, "Ventas", String.valueOf(mPreviousReport.Solds)))
                            .addField(new WebhookEmbed.EmbedField(false, "Gastos", String.valueOf(mPreviousReport.Spent)))
                            .addField(new WebhookEmbed.EmbedField(false, "Ingresos", String.valueOf(mPreviousReport.Earned)))
                            .addField(new WebhookEmbed.EmbedField(false, "Ganancia", String.valueOf(mPreviousReport.Difference)))
                            .build();
                    DISCORD.send(embed);

                    try {
                        DataOutputStream out = new DataOutputStream(new FileOutputStream("Data.bin"));
                        out.writeLong(mPreviousReport.Succeeds);
                        out.writeLong(mPreviousReport.Fails);
                        out.writeLong(mPreviousReport.Solds);
                        out.writeDouble(mPreviousReport.Spent);
                        out.writeDouble(mPreviousReport.Earned);
                        out.writeDouble(mPreviousReport.Difference);
                    } catch (IOException e) {
                    }

                }
            }
        }, 0L, 1, TimeUnit.MINUTES);

         */
        mScheduler.scheduleAtFixedRate(mCryptoMinesAPI::poll, 0L, 5000, TimeUnit.MILLISECONDS);

        if (mBuyingMode) {
           mScheduler.scheduleAtFixedRate(
                    () -> onList(mCryptoMinesAPI.getList(2, mSettingTradeMinMP, mSettingTradeMaxMP, 10000)), 0L, 30, TimeUnit.SECONDS);
        } else {

            List<Worker> list = null;

            while (list == null || list.isEmpty()) {
                list = mCryptoMinesAPI.getList(2, 75, 100, 10000);
            }
            onList(list);

            if (mUnlistMode)
            {
                List<Worker> workers = mCryptoMinesAPI.getUnlist(mAddress);

                for (Worker worker : workers) {
                    if (worker.getPower() < mSettingTradeMinMP || worker.getPower() > mSettingTradeMaxMP) continue;

                    final Catalog catalog = mCatalogs.get(worker.getPower());

                    if (catalog != null && CryptoMinesAPI.fromWei(worker.getPrice()) > catalog.getMedian())
                    {

                        System.out.println("UNLIST:" + worker);

                        mCryptoMinesAPI.unlist(worker);

                        try {
                            Thread.sleep(5000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            mCryptoMinesAPI.getWorkers(mAddress, this::clear);
        }

    }

    private void onList(List<Worker> workers) {
        // sort all workers by power and price
        workers.sort((o1, o2) -> {
            final int power = (int) (o1.getPower() - o2.getPower());
            return (power == 0 ? o1.getPrice().compareTo(o2.getPrice()) : power);
        });

        final Map<Long, Catalog> catalogs = new HashMap<>();

        // calculate median price of each catalog
        for (Worker worker : workers) {
            Catalog catalog = catalogs.computeIfAbsent(worker.getPower(), t -> new Catalog());

            if (catalog.mSkip < mSettingTradePosition) { // 8
                ++catalog.mSkip;
            } else {
                if (catalog.getSize() < mSettingTradeCount) {
                    catalog.addValue(CryptoMinesAPI.fromWei(worker.getPrice()));
                }
            }
        }

        catalogs.forEach((k, v) -> {
            v.calculateMedian();
            if (v.getMedian() != 0) {
                System.out.println(k + ":" + FORMAT_PRICE.format(v.getMedian()));
            }
        });

        System.out.println("------------------------------------------------------------------------------------");

        if (!catalogs.isEmpty()) {
            mCatalogs = catalogs;
        }
    }

    private boolean isRangeAcceptable(Catalog first, Catalog second) {
        final double median1 = first.getMedian();
        final double median2 = second.getMedian();

        if (median1 > median2) {
            return (median2 * 100 / median1) >= 95;
        }
        return true;
    }

    private void clear(Worker worker) {

        Catalog catalog = mCatalogs.get(worker.getPower());

        if (catalog != null && catalog.getMedian() > 0) {
            List<Double> values = catalog.getValues();

            Catalog other = mCatalogs.get(worker.getPower() + 1);

            double sell = catalog.getMedian();

            if (other != null && !isRangeAcceptable(catalog, other)) {
                sell = other.getMedian();
            }

            sell = Double.parseDouble(FORMAT_PRICE.format(sell - 0.0001));

            worker.setProfit(0.0);
            worker.setSell(sell);

            mCryptoMinesAPI.sell(worker);
        }
    }

    @Override
    public boolean onFilter(Worker worker) {
        if (!mBuyingMode || mLocked) return false;

        final long power = worker.getPower();

        if (power < mSettingTradeMinMP || power > mSettingTradeMaxMP) return false;

        Catalog catalog = mCatalogs.get(worker.getPower());

        if (catalog != null && catalog.getMedian() > 0) {
            List<Double> values = catalog.getValues();

            Catalog other = mCatalogs.get(worker.getPower() + 1);

            double sell = catalog.getMedian();

            if (other != null && !isRangeAcceptable(catalog, other)) {
                sell = other.getMedian();
            }

            sell = Double.parseDouble(FORMAT_PRICE.format(sell - 0.0001));

            final double price = CryptoMinesAPI.fromWei(worker.getPrice());

            final double winning = (sell * 0.85) - 0.002;
            final double profit = winning - price;

            System.out.println(Double.parseDouble(FORMAT_PRICE.format(profit)) + "\t| " + worker);

            if (profit >= mSettingTradeMin && profit <= mSettingTradeMax) {

                worker.setProfit(profit);
                worker.setSell(sell);

                synchronized (this) {
                    if (mBuying.contains(worker.getToken())) return false;
                    mBuying.add(worker.getToken());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBuy(Worker worker, TransactionReceipt result) {
        final boolean success = (result != null && result.isStatusOK());

        if (success) {
            synchronized (this) {
                if (!mLocked && mReport.Spent - mReport.Earned >= MAX_TRADING) {
                    mLocked = true;
                }
            }

            synchronized (mReport) {
                ++mReport.Succeeds;
                mReport.Spent += CryptoMinesAPI.fromWei(worker.getPrice());
                mReport.Difference += (worker.getProfit());
            }

            mSales.remove(worker.getToken());

            mCryptoMinesAPI.sell(worker);
        } else {
            synchronized (mReport) {
                ++mReport.Fails;
            }
        }
    }

    @Override
    public void onSell(Worker worker, TransactionReceipt result) {
        final boolean success = (result != null && result.isStatusOK());

        if (!success && worker.getTries() < 4) {
            worker.setTries(worker.getTries() + 1);

            mCryptoMinesAPI.sell(worker);
        }
    }

    @Override
    public void onSold(Worker worker) {
        if (worker.getSeller().equalsIgnoreCase(mAddress) && !mSales.contains(worker.getToken())) {
            mBuying.remove(worker.getToken());
            mSales.add(worker.getToken());

            final double price = CryptoMinesAPI.fromWei(worker.getPrice());
            final double winning = price * 0.85;

            synchronized (mReport) {
                ++mReport.Solds;
                mReport.Earned += winning;
            }

            synchronized (this) {
                if (mLocked && mReport.Spent - mReport.Earned <= MIN_TRADING) {
                    mLocked = false;
                }
            }

        }
    }
}
