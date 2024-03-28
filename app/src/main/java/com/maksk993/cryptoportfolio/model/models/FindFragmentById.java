package com.maksk993.cryptoportfolio.model.models;

public enum FindFragmentById {
    PORTFOLIO, HISTORY, SETTINGS, ADD_ASSET;

    static public int getInteger(FindFragmentById fragment){
        switch (fragment){
            case PORTFOLIO:
                return 0;
            case HISTORY:
                return 1;
            case SETTINGS:
                return 2;
            case ADD_ASSET:
                return 3;
            default:
                throw new IllegalArgumentException("Illegal value.");
        }
    }

    public int getInteger(){
        switch (this){
            case PORTFOLIO:
                return 0;
            case HISTORY:
                return 1;
            case SETTINGS:
                return 2;
            case ADD_ASSET:
                return 3;
            default:
                throw new IllegalArgumentException("Illegal value.");
        }
    }
}
