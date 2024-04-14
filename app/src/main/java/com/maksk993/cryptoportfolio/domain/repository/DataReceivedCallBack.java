package com.maksk993.cryptoportfolio.domain.repository;

public interface DataReceivedCallBack {
    void dataReceived(String symbol, float price);
}
