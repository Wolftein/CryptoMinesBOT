package com.github.wolftein.cryptomines.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
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
@SuppressWarnings("rawtypes")
public class ProxieContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_DOBUY = "DoBuy";

    public static final String FUNC_DODESTROY = "DoDestroy";

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    @Deprecated
    protected ProxieContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ProxieContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ProxieContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ProxieContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> DoBuy(BigInteger _Token, BigInteger _Worker, BigInteger _Price) {
        final Function function = new Function(
                FUNC_DOBUY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_Token),
                        new org.web3j.abi.datatypes.generated.Uint256(_Worker),
                        new org.web3j.abi.datatypes.generated.Uint256(_Price)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> DoDestroy() {
        final Function function = new Function(
                FUNC_DODESTROY,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> onERC721Received(String param0, String param1, BigInteger param2, byte[] param3) {
        final Function function = new Function(FUNC_ONERC721RECEIVED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0),
                        new org.web3j.abi.datatypes.Address(160, param1),
                        new org.web3j.abi.datatypes.generated.Uint256(param2),
                        new org.web3j.abi.datatypes.DynamicBytes(param3)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    @Deprecated
    public static ProxieContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ProxieContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ProxieContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ProxieContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ProxieContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ProxieContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ProxieContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ProxieContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}