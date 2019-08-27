package com.yibi.core.constants;

/**
 * Created by Administrator on 2018/7/12 0012.
 */
public class CoinType {

    public static  final int NONE = -1; //无具体指向币种
    public static  final int ENC = 0;
    public static  final int YEZI = 1;
    public static  final int YT = 2;
    public static  final int OGM = 3;

    public static String getCoinName(Integer coinType){
        switch (coinType){
            case 0 : return "ECN";
            case 1 : return "ODIN";
            case 2 : return "YT";
            case 3 : return "OGM";
            default: return null;
        }
    }
}
