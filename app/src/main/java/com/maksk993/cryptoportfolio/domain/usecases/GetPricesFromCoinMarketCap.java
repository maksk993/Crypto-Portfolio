package com.maksk993.cryptoportfolio.domain.usecases;

import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository;
import com.maksk993.cryptoportfolio.domain.models.DataReceivedCallBack;

public class GetPricesFromCoinMarketCap {
    private final CryptoRepository repository;

    public GetPricesFromCoinMarketCap(CryptoRepository repository){
        this.repository = repository;
    }
    public void execute(DataReceivedCallBack callBack){
        repository.getPrices(callBack);
    }
}
