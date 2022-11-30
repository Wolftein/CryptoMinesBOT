package com.github.wolftein.cryptomines.model;

import org.json.JSONObject;
import org.web3j.utils.Convert;

import java.math.BigInteger;

public final class Worker {

    private BigInteger mID;
    private BigInteger mToken;
    private BigInteger mPrice;
    private String mSeller;
    private String mBuyer;
    private long mLevel;
    private long mPower;
    private double mSell;
    private double mProfit;

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

    public double getEternal() {
        return Convert.fromWei(mPrice.toString(), Convert.Unit.ETHER).doubleValue();
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

    public boolean isSold() {
        return !mBuyer.equals("0x0000000000000000000000000000000000000000");
    }

    @Override
    public String toString() {
        return "[ID=" + mID + ", TOKEN=" + mToken + ", SELLER=" + mSeller + ", PRICE=" + getEternal() + ", \tLEVEL=" + mLevel + ", POWER=" + mPower;
    }
}
