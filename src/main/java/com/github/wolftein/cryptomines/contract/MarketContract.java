package com.github.wolftein.cryptomines.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
public class MarketContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_BUYNFT = "buyNFT";

    public static final String FUNC_GETCURRENTNUMBEROFITEMS = "getCurrentNumberOfItems";

    public static final String FUNC_GETETERNALADDRESS = "getEternalAddress";

    public static final String FUNC_GETMARKETITEM = "getMarketItem";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GETSPACESHIPADDRESS = "getSpaceshipAddress";

    public static final String FUNC_GETWORKERADDRESS = "getWorkerAddress";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    public static final String FUNC_RECOVERSPACESHIPS = "recoverSpaceships";

    public static final String FUNC_RECOVERWORKERS = "recoverWorkers";

    public static final String FUNC_RECOVERFUNDS = "recoverfunds";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SELLNFT = "sellNFT";

    public static final String FUNC_SETFLEETCONTRACT = "setFleetContract";

    public static final String FUNC_SETSELLFEE = "setSellFee";

    public static final String FUNC_SETTAXRECEIVER = "setTaxReceiver";

    public static final String FUNC_SETUPBANROLE = "setupBanRole";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_UNLISTNFT = "unlistNFT";

    public static final Event ROLEADMINCHANGED_EVENT = new Event("RoleAdminChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));

    public static final Event ROLEGRANTED_EVENT = new Event("RoleGranted",
            Arrays.asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));

    public static final Event ROLEREVOKED_EVENT = new Event("RoleRevoked",
            Arrays.asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));

    public static final Event EVENTBUYNFT_EVENT = new Event("eventBuyNFT",
            Arrays.asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));

    public static final Event EVENTSELLNFT_EVENT = new Event("eventSellNFT",
            Arrays.asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));

    public static final Event EVENTSELLNFT2_EVENT = new Event("eventSellNFT",
            Arrays.asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));

    public static final Event EVENTUNLISTNFT_EVENT = new Event("eventUnlistNFT",
            Arrays.asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));

    protected MarketContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected MarketContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<RoleAdminChangedEventResponse> getRoleAdminChangedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<RoleAdminChangedEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEADMINCHANGED_EVENT, log);
            RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
            typedResponse.log = log;
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            return typedResponse;
        });
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEADMINCHANGED_EVENT));
        return roleAdminChangedEventFlowable(filter);
    }

    public List<RoleGrantedEventResponse> getRoleGrantedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEGRANTED_EVENT, transactionReceipt);
        ArrayList<RoleGrantedEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEGRANTED_EVENT, log);
            RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
            typedResponse.log = log;
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            return typedResponse;
        });
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEGRANTED_EVENT));
        return roleGrantedEventFlowable(filter);
    }

    public List<RoleRevokedEventResponse> getRoleRevokedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEREVOKED_EVENT, transactionReceipt);
        ArrayList<RoleRevokedEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEREVOKED_EVENT, log);
            RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
            typedResponse.log = log;
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            return typedResponse;
        });
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEREVOKED_EVENT));
        return roleRevokedEventFlowable(filter);
    }

    public List<EventBuyNFTEventResponse> getEventBuyNFTEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTBUYNFT_EVENT, transactionReceipt);
        ArrayList<EventBuyNFTEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventBuyNFTEventResponse typedResponse = new EventBuyNFTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.marketId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sellerAddress = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.buyerAddress = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventBuyNFTEventResponse> eventBuyNFTEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTBUYNFT_EVENT, log);
            EventBuyNFTEventResponse typedResponse = new EventBuyNFTEventResponse();
            typedResponse.log = log;
            typedResponse.marketId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sellerAddress = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.buyerAddress = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventBuyNFTEventResponse> eventBuyNFTEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTBUYNFT_EVENT));
        return eventBuyNFTEventFlowable(filter);
    }

    public List<EventSellNFTEventResponse> getEventSellNFTEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTSELLNFT_EVENT, transactionReceipt);
        ArrayList<EventSellNFTEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventSellNFTEventResponse typedResponse = new EventSellNFTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.marketId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sellerAddress = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventSellNFTEventResponse> eventSellNFTEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTSELLNFT_EVENT, log);
            EventSellNFTEventResponse typedResponse = new EventSellNFTEventResponse();
            typedResponse.log = log;
            typedResponse.marketId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sellerAddress = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventSellNFTEventResponse> eventSellNFTEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTSELLNFT_EVENT));
        return eventSellNFTEventFlowable(filter);
    }

    public List<EventUnlistNFTEventResponse> getEventUnlistNFTEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTUNLISTNFT_EVENT, transactionReceipt);
        ArrayList<EventUnlistNFTEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventUnlistNFTEventResponse typedResponse = new EventUnlistNFTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.marketId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sellerAddress = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventUnlistNFTEventResponse> eventUnlistNFTEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTUNLISTNFT_EVENT, log);
            EventUnlistNFTEventResponse typedResponse = new EventUnlistNFTEventResponse();
            typedResponse.log = log;
            typedResponse.marketId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sellerAddress = (String) eventValues.getNonIndexedValues().get(3).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventUnlistNFTEventResponse> eventUnlistNFTEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTUNLISTNFT_EVENT));
        return eventUnlistNFTEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> DEFAULT_ADMIN_ROLE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULT_ADMIN_ROLE,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> buyNFT(BigInteger _marketId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUYNFT,
                Collections.singletonList(new Uint256(_marketId)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getCurrentNumberOfItems() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCURRENTNUMBEROFITEMS,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getEternalAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETETERNALADDRESS,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<MarketItem> getMarketItem(BigInteger _marketId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMARKETITEM,
                Collections.singletonList(new Uint256(_marketId)),
                Collections.singletonList(new TypeReference<MarketItem>() {}));
        return executeRemoteCallSingleValueReturn(function, MarketItem.class);
    }

    public RemoteFunctionCall<byte[]> getRoleAdmin(byte[] role) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETROLEADMIN,
                Collections.singletonList(new Bytes32(role)),
                Collections.singletonList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> getSpaceshipAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSPACESHIPADDRESS,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getWorkerAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETWORKERADDRESS,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> grantRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GRANTROLE,
                Arrays.asList(new Bytes32(role),
                        new Address(160, account)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> hasRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HASROLE,
                Arrays.asList(new Bytes32(role), new Address(160, account)),
                Collections.singletonList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _eternalToken, String _spaceship, String _workers, String _cryptomine) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE,
                Arrays.asList(new Address(160, _eternalToken),
                        new Address(160, _spaceship),
                        new Address(160, _workers),
                        new Address(160, _cryptomine)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> onERC721Received(String param0, String param1, BigInteger param2, byte[] param3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ONERC721RECEIVED,
                Arrays.asList(new Address(160, param0),
                        new Address(160, param1),
                        new Uint256(param2),
                        new org.web3j.abi.datatypes.DynamicBytes(param3)),
                Collections.singletonList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> recoverSpaceships(String _from, String _to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECOVERSPACESHIPS,
                Arrays.asList(new Address(160, _from),
                        new Address(160, _to)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> recoverWorkers(String _from, String _to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECOVERWORKERS,
                Arrays.asList(new Address(160, _from),
                        new Address(160, _to)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> recoverfunds(String _from, String _to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECOVERFUNDS,
                Arrays.asList(new Address(160, _from),
                        new Address(160, _to)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEROLE,
                Arrays.asList(new Bytes32(role),
                        new Address(160, account)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVOKEROLE,
                Arrays.asList(new Bytes32(role),
                        new Address(160, account)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sellNFT(BigInteger _nftType, BigInteger _tokenId, BigInteger _price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SELLNFT,
                Arrays.asList(new Uint8(_nftType),
                        new Uint256(_tokenId),
                        new Uint256(_price)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFleetContract(String _fleets) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFLEETCONTRACT,
                Collections.singletonList(new Address(160, _fleets)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSellFee(BigInteger _sellFee) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSELLFEE,
                Collections.singletonList(new Uint256(_sellFee)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTaxReceiver(String _taxReceiver) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETTAXRECEIVER,
                Collections.singletonList(new Address(160, _taxReceiver)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setupBanRole() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETUPBANROLE,
                Collections.emptyList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE,
                Collections.singletonList(new Bytes4(interfaceId)),
                Collections.singletonList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> unlistNFT(BigInteger _marketId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNLISTNFT,
                Collections.singletonList(new Uint256(_marketId)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static MarketContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MarketContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MarketContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MarketContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class MarketItem extends StaticStruct {
        public BigInteger marketId;

        public BigInteger nftType;

        public BigInteger tokenId;

        public String sellerAddress;

        public String buyerAddress;

        public BigInteger price;

        public MarketItem(BigInteger marketId, BigInteger nftType, BigInteger tokenId, String sellerAddress, String buyerAddress, BigInteger price) {
            super(new Uint256(marketId),new Uint8(nftType),new Uint256(tokenId),new Address(sellerAddress),new Address(buyerAddress),new Uint256(price));
            this.marketId = marketId;
            this.nftType = nftType;
            this.tokenId = tokenId;
            this.sellerAddress = sellerAddress;
            this.buyerAddress = buyerAddress;
            this.price = price;
        }

        public MarketItem(Uint256 marketId, Uint8 nftType, Uint256 tokenId, Address sellerAddress, Address buyerAddress, Uint256 price) {
            super(marketId,nftType,tokenId,sellerAddress,buyerAddress,price);
            this.marketId = marketId.getValue();
            this.nftType = nftType.getValue();
            this.tokenId = tokenId.getValue();
            this.sellerAddress = sellerAddress.getValue();
            this.buyerAddress = buyerAddress.getValue();
            this.price = price.getValue();
        }
    }

    public static class RoleAdminChangedEventResponse extends BaseEventResponse {
        public byte[] role;

        public byte[] previousAdminRole;

        public byte[] newAdminRole;
    }

    public static class RoleGrantedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }

    public static class RoleRevokedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }

    public static class EventBuyNFTEventResponse extends BaseEventResponse {
        public BigInteger marketId;

        public BigInteger nftType;

        public BigInteger tokenId;

        public String sellerAddress;

        public String buyerAddress;

        public BigInteger price;
    }

    public static class EventSellNFTEventResponse extends BaseEventResponse {
        public BigInteger marketId;

        public BigInteger nftType;

        public BigInteger tokenId;

        public String sellerAddress;

        public BigInteger price;
    }

    public static class EventUnlistNFTEventResponse extends BaseEventResponse {
        public BigInteger marketId;

        public BigInteger nftType;

        public BigInteger tokenId;

        public String sellerAddress;
    }
}