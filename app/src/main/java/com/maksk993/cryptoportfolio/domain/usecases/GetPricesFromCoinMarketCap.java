package com.maksk993.cryptoportfolio.domain.usecases;

import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository;

public class GetPricesFromCoinMarketCap {
    private final CryptoRepository repository;

    public GetPricesFromCoinMarketCap(CryptoRepository repository){
        this.repository = repository;
    }
    public void execute(){
        repository.getData();
    }
}
