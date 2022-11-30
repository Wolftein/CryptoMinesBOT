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
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
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

@SuppressWarnings("rawtypes")
public class StonesContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_ADDFUELTOFLEET = "addFuelToFleet";

    public static final String FUNC_ADDTOFLEET = "addToFleet";

    public static final String FUNC_APPROVEETERNAL = "approveEternal";

    public static final String FUNC_BURNFLEET = "burnFleet";

    public static final String FUNC_BURNSPACESHIP = "burnSpaceship";

    public static final String FUNC_BURNWORKER = "burnWorker";

    public static final String FUNC_CLAIMTOKENREWARDS = "claimTokenRewards";

    public static final String FUNC_CONTRACTFLEET = "contractFleet";

    public static final String FUNC_GETCONTRACTCOSTETERNAL = "getContractCostEternal";

    public static final String FUNC_GETCURRENTDAY = "getCurrentDay";

    public static final String FUNC_GETDAYSFORCLAIM = "getDaysForClaim";

    public static final String FUNC_GETFLEETMINTCOSTPERNFTETERNAL = "getFleetMintCostPerNftEternal";

    public static final String FUNC_GETFUELCOSTETERNAL = "getFuelCostEternal";

    public static final String FUNC_GETMAXSPACESHIPS = "getMaxSpaceships";

    public static final String FUNC_GETMAXWORKERS = "getMaxWorkers";

    public static final String FUNC_GETMINEPOWER = "getMinePower";

    public static final String FUNC_GETMINEREWARDETERNAL = "getMineRewardEternal";

    public static final String FUNC_GETMINTCOSTETERNAL = "getMintCostEternal";

    public static final String FUNC_GETMYSPACESHIPS = "getMySpaceships";

    public static final String FUNC_GETMYTIMEFORCLAIM = "getMyTimeForClaim";

    public static final String FUNC_GETMYWORKERS = "getMyWorkers";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GETTOKENREWARDS = "getTokenRewards";

    public static final String FUNC_GOMINE = "goMine";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_INCREASECURRENTDAY = "increaseCurrentDay";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_MINTFLEET = "mintFleet";

    public static final String FUNC_QUEUEMINT = "queueMint";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVEALNFT = "revealNFT";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SEEDGASFUNDER = "seedGasFunder";

    public static final String FUNC_SETBURNREWARD = "setBurnReward";

    public static final String FUNC_SETFLEETFUNCTIONSCONTRACT = "setFleetFunctionsContract";

    public static final String FUNC_SETFLEETMINTCOSTPERNFTINWEI = "setFleetMintCostPerNftInWei";

    public static final String FUNC_SETFUELUNITCOSTINWEI = "setFuelUnitCostInWei";

    public static final String FUNC_SETMAXSPACESHIP = "setMaxSpaceship";

    public static final String FUNC_SETVRFCHAINLINK = "setVRFChainlink";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_USDTOETERNAL = "usdToEternal";

    public static final Event ROLEADMINCHANGED_EVENT = new Event("RoleAdminChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Bytes32>(true) {
            }, new TypeReference<Bytes32>(true) {
            }));

    public static final Event ROLEGRANTED_EVENT = new Event("RoleGranted",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));

    public static final Event ROLEREVOKED_EVENT = new Event("RoleRevoked",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));

    public static final Event EVENTBURNSPACESHIP_EVENT = new Event("eventBurnSpaceship",
            Collections.singletonList(new TypeReference<Uint256>() {
            }));

    public static final Event EVENTBURNWORKER_EVENT = new Event("eventBurnWorker",
            Collections.singletonList(new TypeReference<Uint256>() {
            }));

    public static final Event EVENTCLAIMREWARDS_EVENT = new Event("eventClaimRewards",
            Arrays.asList(new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Address>() {
            }));

    public static final Event EVENTCONTRACTWORKER_EVENT = new Event("eventContractWorker",
            Collections.emptyList());

    public static final Event EVENTGOMINE_EVENT = new Event("eventGoMine",
            Arrays.asList(new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));

    public static final Event EVENTMINTSPACESHIP_EVENT = new Event("eventMintSpaceship",
            Collections.singletonList(new TypeReference<Uint256>() {
            }));

    public static final Event EVENTMINTWORKER_EVENT = new Event("eventMintWorker",
            Collections.singletonList(new TypeReference<Uint256>() {
            }));

    public static final Event EVENTQUEUEMINT_EVENT = new Event("eventQueueMint",
            Arrays.asList(new TypeReference<Uint8>() {
            }, new TypeReference<Address>() {
            }));

    public static final Event EVENTREVEALNFT_EVENT = new Event("eventRevealNFT",
            Arrays.asList(new TypeReference<Uint8>() {
            }, new TypeReference<Address>() {
            }));

    protected StonesContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected StonesContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public List<EventBurnSpaceshipEventResponse> getEventBurnSpaceshipEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTBURNSPACESHIP_EVENT, transactionReceipt);
        ArrayList<EventBurnSpaceshipEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventBurnSpaceshipEventResponse typedResponse = new EventBurnSpaceshipEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventBurnSpaceshipEventResponse> eventBurnSpaceshipEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTBURNSPACESHIP_EVENT, log);
            EventBurnSpaceshipEventResponse typedResponse = new EventBurnSpaceshipEventResponse();
            typedResponse.log = log;
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventBurnSpaceshipEventResponse> eventBurnSpaceshipEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTBURNSPACESHIP_EVENT));
        return eventBurnSpaceshipEventFlowable(filter);
    }

    public List<EventBurnWorkerEventResponse> getEventBurnWorkerEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTBURNWORKER_EVENT, transactionReceipt);
        ArrayList<EventBurnWorkerEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventBurnWorkerEventResponse typedResponse = new EventBurnWorkerEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventBurnWorkerEventResponse> eventBurnWorkerEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTBURNWORKER_EVENT, log);
            EventBurnWorkerEventResponse typedResponse = new EventBurnWorkerEventResponse();
            typedResponse.log = log;
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventBurnWorkerEventResponse> eventBurnWorkerEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTBURNWORKER_EVENT));
        return eventBurnWorkerEventFlowable(filter);
    }

    public List<EventClaimRewardsEventResponse> getEventClaimRewardsEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTCLAIMREWARDS_EVENT, transactionReceipt);
        ArrayList<EventClaimRewardsEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventClaimRewardsEventResponse typedResponse = new EventClaimRewardsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.claimedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.claimedFee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventClaimRewardsEventResponse> eventClaimRewardsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTCLAIMREWARDS_EVENT, log);
            EventClaimRewardsEventResponse typedResponse = new EventClaimRewardsEventResponse();
            typedResponse.log = log;
            typedResponse.claimedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.claimedFee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(2).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventClaimRewardsEventResponse> eventClaimRewardsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTCLAIMREWARDS_EVENT));
        return eventClaimRewardsEventFlowable(filter);
    }

    public List<EventContractWorkerEventResponse> getEventContractWorkerEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTCONTRACTWORKER_EVENT, transactionReceipt);
        ArrayList<EventContractWorkerEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventContractWorkerEventResponse typedResponse = new EventContractWorkerEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventContractWorkerEventResponse> eventContractWorkerEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTCONTRACTWORKER_EVENT, log);
            EventContractWorkerEventResponse typedResponse = new EventContractWorkerEventResponse();
            typedResponse.log = log;
            return typedResponse;
        });
    }

    public Flowable<EventContractWorkerEventResponse> eventContractWorkerEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTCONTRACTWORKER_EVENT));
        return eventContractWorkerEventFlowable(filter);
    }

    public List<EventGoMineEventResponse> getEventGoMineEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTGOMINE_EVENT, transactionReceipt);
        ArrayList<EventGoMineEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventGoMineEventResponse typedResponse = new EventGoMineEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.roll = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.winChance = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.reward = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.minePower = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.planet = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.experience = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.fleetLevel = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventGoMineEventResponse> eventGoMineEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTGOMINE_EVENT, log);
            EventGoMineEventResponse typedResponse = new EventGoMineEventResponse();
            typedResponse.log = log;
            typedResponse.roll = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.winChance = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.reward = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.minePower = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.planet = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.experience = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.fleetLevel = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventGoMineEventResponse> eventGoMineEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTGOMINE_EVENT));
        return eventGoMineEventFlowable(filter);
    }

    public List<EventMintSpaceshipEventResponse> getEventMintSpaceshipEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTMINTSPACESHIP_EVENT, transactionReceipt);
        ArrayList<EventMintSpaceshipEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventMintSpaceshipEventResponse typedResponse = new EventMintSpaceshipEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventMintSpaceshipEventResponse> eventMintSpaceshipEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTMINTSPACESHIP_EVENT, log);
            EventMintSpaceshipEventResponse typedResponse = new EventMintSpaceshipEventResponse();
            typedResponse.log = log;
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventMintSpaceshipEventResponse> eventMintSpaceshipEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTMINTSPACESHIP_EVENT));
        return eventMintSpaceshipEventFlowable(filter);
    }

    public List<EventMintWorkerEventResponse> getEventMintWorkerEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTMINTWORKER_EVENT, transactionReceipt);
        ArrayList<EventMintWorkerEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventMintWorkerEventResponse typedResponse = new EventMintWorkerEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventMintWorkerEventResponse> eventMintWorkerEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTMINTWORKER_EVENT, log);
            EventMintWorkerEventResponse typedResponse = new EventMintWorkerEventResponse();
            typedResponse.log = log;
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventMintWorkerEventResponse> eventMintWorkerEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTMINTWORKER_EVENT));
        return eventMintWorkerEventFlowable(filter);
    }

    public List<EventQueueMintEventResponse> getEventQueueMintEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTQUEUEMINT_EVENT, transactionReceipt);
        ArrayList<EventQueueMintEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventQueueMintEventResponse typedResponse = new EventQueueMintEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.minter = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventQueueMintEventResponse> eventQueueMintEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTQUEUEMINT_EVENT, log);
            EventQueueMintEventResponse typedResponse = new EventQueueMintEventResponse();
            typedResponse.log = log;
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.minter = (String) eventValues.getNonIndexedValues().get(1).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventQueueMintEventResponse> eventQueueMintEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTQUEUEMINT_EVENT));
        return eventQueueMintEventFlowable(filter);
    }

    public List<EventRevealNFTEventResponse> getEventRevealNFTEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTREVEALNFT_EVENT, transactionReceipt);
        ArrayList<EventRevealNFTEventResponse> responses = new ArrayList<>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventRevealNFTEventResponse typedResponse = new EventRevealNFTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.minter = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventRevealNFTEventResponse> eventRevealNFTEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTREVEALNFT_EVENT, log);
            EventRevealNFTEventResponse typedResponse = new EventRevealNFTEventResponse();
            typedResponse.log = log;
            typedResponse.nftType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.minter = (String) eventValues.getNonIndexedValues().get(1).getValue();
            return typedResponse;
        });
    }

    public Flowable<EventRevealNFTEventResponse> eventRevealNFTEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTREVEALNFT_EVENT));
        return eventRevealNFTEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> DEFAULT_ADMIN_ROLE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULT_ADMIN_ROLE,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> addFuelToFleet(BigInteger _tokenId, BigInteger _units, Boolean _useTokenRewards) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDFUELTOFLEET,
                Arrays.asList(new Uint256(_tokenId),
                        new Uint32(_units),
                        new Bool(_useTokenRewards)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addToFleet(BigInteger _tokenId, List<BigInteger> _workersId, List<BigInteger> _spaceshipsId, Boolean _useTokenRewards) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDTOFLEET,
                Arrays.asList(new Uint256(_tokenId),
                        new DynamicArray<>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(_workersId, Uint256.class)),
                        new DynamicArray<>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(_spaceshipsId, Uint256.class)),
                        new Bool(_useTokenRewards)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> approveEternal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVEETERNAL,
                Collections.emptyList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> burnFleet(BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BURNFLEET,
                Collections.singletonList(new Uint256(_tokenId)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> burnSpaceship(BigInteger _spaceshipId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BURNSPACESHIP,
                Collections.singletonList(new Uint256(_spaceshipId)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> burnWorker(BigInteger _workerId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BURNWORKER,
                Collections.singletonList(new Uint256(_workerId)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimTokenRewards() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMTOKENREWARDS,
                Collections.emptyList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> contractFleet(BigInteger _tokenId, BigInteger _uses, Boolean _useTokenRewards) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONTRACTFLEET,
                Arrays.asList(new Uint256(_tokenId),
                        new Uint8(_uses),
                        new Bool(_useTokenRewards)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getContractCostEternal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCONTRACTCOSTETERNAL,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getCurrentDay() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCURRENTDAY,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getDaysForClaim() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDAYSFORCLAIM,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getFleetMintCostPerNftEternal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFLEETMINTCOSTPERNFTETERNAL,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getFuelCostEternal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFUELCOSTETERNAL,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getMaxSpaceships() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMAXSPACESHIPS,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getMaxWorkers() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMAXWORKERS,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getMinePower() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMINEPOWER,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint16>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getMineRewardEternal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMINEREWARDETERNAL,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getMintCostEternal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMINTCOSTETERNAL,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getMySpaceships() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMYSPACESHIPS,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<DynamicArray<Uint256>>() {
                }));
        return new RemoteFunctionCall<>(function,
                () -> {
                    List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                    return convertToNative(result);
                });
    }

    public RemoteFunctionCall<BigInteger> getMyTimeForClaim() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMYTIMEFORCLAIM,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getMyWorkers() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMYWORKERS,
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<DynamicArray<Uint256>>() {
                }));
        return new RemoteFunctionCall<>(function,
                () -> {
                    List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                    return convertToNative(result);
                });
    }

    public RemoteFunctionCall<byte[]> getRoleAdmin(byte[] role) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETROLEADMIN,
                Collections.singletonList(new Bytes32(role)),
                Collections.singletonList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getTokenRewards(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENREWARDS,
                Collections.singletonList(new Address(160, _address)),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> goMine(BigInteger _fleetTokenId, BigInteger _planet) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GOMINE,
                Arrays.asList(new Uint256(_fleetTokenId),
                        new Uint256(_planet)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
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
                Arrays.asList(new Bytes32(role),
                        new Address(160, account)),
                Collections.singletonList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> increaseCurrentDay() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INCREASECURRENTDAY,
                Collections.emptyList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _eternalToken, String _spaceship, String _workers, String _oracle) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE,
                Arrays.asList(new Address(160, _eternalToken),
                        new Address(160, _spaceship),
                        new Address(160, _workers),
                        new Address(160, _oracle)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mintFleet(List<BigInteger> _workersId, List<BigInteger> _spaceshipsId, String _fleetName, Boolean _useTokenRewards) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINTFLEET,
                Arrays.asList(new DynamicArray<>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(_workersId, Uint256.class)),
                        new DynamicArray<>(
                                Uint256.class,
                                org.web3j.abi.Utils.typeMap(_spaceshipsId, Uint256.class)),
                        new org.web3j.abi.datatypes.Utf8String(_fleetName),
                        new Bool(_useTokenRewards)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> queueMint(BigInteger _nftType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_QUEUEMINT,
                Collections.singletonList(new Uint8(_nftType)),
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

    public RemoteFunctionCall<TransactionReceipt> revealNFT(BigInteger _nftType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVEALNFT,
                Collections.singletonList(new Uint8(_nftType)),
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

    public RemoteFunctionCall<TransactionReceipt> seedGasFunder(String _seedAllocator) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SEEDGASFUNDER,
                Collections.singletonList(new Address(160, _seedAllocator)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setBurnReward(BigInteger _fee) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETBURNREWARD,
                Collections.singletonList(new Uint8(_fee)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFleetFunctionsContract(String _fleetFunctions) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFLEETFUNCTIONSCONTRACT,
                Collections.singletonList(new Address(160, _fleetFunctions)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFleetMintCostPerNftInWei(BigInteger _wei) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFLEETMINTCOSTPERNFTINWEI,
                Collections.singletonList(new Uint256(_wei)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFuelUnitCostInWei(BigInteger _wei) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFUELUNITCOSTINWEI,
                Collections.singletonList(new Uint256(_wei)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setMaxSpaceship(BigInteger _maxSpaceships) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETMAXSPACESHIP,
                Collections.singletonList(new Uint8(_maxSpaceships)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVRFChainlink(String _vrfChainlink) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETVRFCHAINLINK,
                Collections.singletonList(new Address(160, _vrfChainlink)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE,
                Collections.singletonList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)),
                Collections.singletonList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> usdToEternal(BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_USDTOETERNAL,
                Collections.singletonList(new Uint256(_amount)),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static StonesContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StonesContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static StonesContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StonesContract(contractAddress, web3j, transactionManager, contractGasProvider);
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

    public static class EventBurnSpaceshipEventResponse extends BaseEventResponse {
        public BigInteger tokenId;
    }

    public static class EventBurnWorkerEventResponse extends BaseEventResponse {
        public BigInteger tokenId;
    }

    public static class EventClaimRewardsEventResponse extends BaseEventResponse {
        public BigInteger claimedAmount;

        public BigInteger claimedFee;

        public String user;
    }

    public static class EventContractWorkerEventResponse extends BaseEventResponse {
    }

    public static class EventGoMineEventResponse extends BaseEventResponse {
        public BigInteger roll;

        public BigInteger winChance;

        public BigInteger reward;

        public String user;

        public BigInteger minePower;

        public BigInteger planet;

        public BigInteger experience;

        public BigInteger fleetLevel;
    }

    public static class EventMintSpaceshipEventResponse extends BaseEventResponse {
        public BigInteger tokenId;
    }

    public static class EventMintWorkerEventResponse extends BaseEventResponse {
        public BigInteger tokenId;
    }

    public static class EventQueueMintEventResponse extends BaseEventResponse {
        public BigInteger nftType;

        public String minter;
    }

    public static class EventRevealNFTEventResponse extends BaseEventResponse {
        public BigInteger nftType;

        public String minter;
    }
}
