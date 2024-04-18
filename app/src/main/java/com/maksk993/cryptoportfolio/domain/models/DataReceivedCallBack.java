package com.maksk993.cryptoportfolio.domain.models;

public interface DataReceivedCallBack {
    void dataReceived(String symbol, float price);
}
