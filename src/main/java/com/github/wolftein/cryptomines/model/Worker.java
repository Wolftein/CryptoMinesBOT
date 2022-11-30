package com.github.wolftein.cryptomines.model;

import com.github.wolftein.cryptomines.CryptoMinesAPI;
import org.json.JSONObject;

import java.math.BigInteger;

public final class Worker {

    private BigInteger mClock;
    private BigInteger mID;
    private BigInteger mToken;
    private BigInteger mPrice;
    private double mSell;
    private double mProfit;
    private String mSeller;
    private String mBuyer;
    private long mLevel;
    private long mPower;
    private long mTries = 0;
    private long mBlock = 0;

    public Worker() {
    }
    
    public Worker(JSONObject object) {
        mID = object.getBigInteger("marketId");
        mToken = object.getBigInteger("tokenId");
        mSeller = object.getString("sellerAddress");
        mBuyer = object.getString("buyerAddress");
        mPrice = object.getBigInteger("price");

        final JSONObject nftData = object.getJSONObject("nftData");
        mLevel = nftData.getLong("level");
        mPower = nftData.getLong("minePower");
    }

    public BigInteger getClock() {
        return mClock;
    }

    public void setClock(BigInteger clock) {
        this.mClock = clock;
    }

    public BigInteger getID() {
        return mID;
    }

    public void setID(BigInteger id) {
        this.mID = id;
    }

    public double getSell() {
        return mSell;
    }

    public void setSell(double price) {
        this.mSell = price;
    }

    public double getProfit() {
        return mProfit;
    }

    public void setProfit(double price) {
        this.mProfit = price;
    }

    public BigInteger getToken() {
        return mToken;
    }

    public void setToken(BigInteger token) {
        this.mToken = token;
    }

    public BigInteger getPrice() {
        return mPrice;
    }

    public void setPrice(BigInteger price) {
        this.mPrice = price;
    }

    public String getSeller() {
        return mSeller;
    }

    public void setSeller(String seller) {
        this.mSeller = seller;
    }

    public String getBuyer() {
        return mBuyer;
    }

    public void setBuyer(String buyer) {
        this.mBuyer = buyer;
    }

    public long getLevel() {
        return mLevel;
    }

    public void setLevel(long level) {
        this.mLevel = level;
    }

    public long getPower() {
        return mPower;
    }

    public void setPower(long power) {
        this.mPower = power;
    }

    public long getTries() {
        return mTries;
    }

    public void setTries(long tries) {
        this.mTries = tries;
    }

    public long getBlock() {
        return mBlock;
    }

    public void setBlock(long block) {
        this.mBlock = block;
    }

    public boolean isSold() {
        return !mBuyer.equals("0x0000000000000000000000000000000000000000");
    }

    @Override
    public String toString() {
        return "[ID=" + mID + ", TOKEN=" + mToken + ", SELLER=" + mSeller + ", PRICE=" + CryptoMinesAPI.fromWei(mPrice) + ", \tLEVEL=" + mLevel + ", POWER=" + mPower;
    }
}
