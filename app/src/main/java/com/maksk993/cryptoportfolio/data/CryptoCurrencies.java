package com.maksk993.cryptoportfolio.data;

public enum CryptoCurrencies {
    BTC, USDT, ETH, BNB, TON, TRX, DOT, NEAR, MATIC, LTC;

    public int getCoinMarketCapId(){
        switch (this){
            case BTC:
                return 1;
            case USDT:
                return 825;
            case ETH:
                return 1027;
            case BNB:
                return 1839;
            case TON:
                return 11419;
            case TRX:
                return 1958;
            case DOT:
                return 6636;
            case NEAR:
                return 6535;
            case MATIC:
                return 3890;
            case LTC:
                return 2;
            default:
                throw new IllegalArgumentException("No such currency.");
        }
    }
}
