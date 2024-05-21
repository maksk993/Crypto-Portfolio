package com.maksk993.cryptoportfolio.domain.repository;

import com.maksk993.cryptoportfolio.domain.models.DataReceivedCallBack;

public interface CryptoRepository {
    void getPrices(DataReceivedCallBack callBack);
}
