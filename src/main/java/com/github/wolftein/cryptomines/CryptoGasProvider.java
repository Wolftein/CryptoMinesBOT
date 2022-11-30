package com.github.wolftein.cryptomines;

import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public final class CryptoGasProvider implements ContractGasProvider {
    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(500_000L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(5_100_000_005L);
    private final static BigInteger GAS_PRICE_LOW = BigInteger.valueOf(5_000_000_000L);

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        if (contractFunc.equalsIgnoreCase("buyNFT")) {
            return GAS_PRICE;
        }
        return GAS_PRICE_LOW;
    }

    public BigInteger getGasPrice() {
        return GAS_PRICE;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        return GAS_LIMIT;
    }

    @Override
    public BigInteger getGasLimit() {
        return GAS_LIMIT;
    }
}
