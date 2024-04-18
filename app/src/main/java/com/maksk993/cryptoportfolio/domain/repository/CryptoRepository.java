package com.maksk993.cryptoportfolio.domain.repository;

import com.maksk993.cryptoportfolio.domain.models.DataReceivedCallBack;

public interface CryptoRepository {
    void getData(DataReceivedCallBack callBack);
}
