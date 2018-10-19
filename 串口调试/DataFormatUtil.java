package com.ccm.autoaudio.utils;

import java.text.DecimalFormat;

/**
 * Created by zry on 18/3/7.
 */

public class DataFormatUtil {




//    /**
//     * 获取包序号
//     * @param byte1 高8位
//     * @param byte2 低8位
//     * @return
//     */
//    public static int pinJie2ByteToInt(byte byte1, byte byte2){
//        int result = ((byte1 & 0x00FF) << 8) | (0x00FF & byte2);
//        return result;
//    }

    /**
     * 保留浮点数小数点后面几位
     * @param f 浮点数
     * @param format 浮点数的格式
     * @return
     */
    public static String formatFloat(float f, String format){
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f);
    }

    /**
     * 将两个字节拼接还原成有符号的整型数据
     * @param byte1
     * @param byte2
     * @return
     */
    public static int pinJie2ByteToInt(byte byte1, byte byte2){
        int result = byte1;
        result = (result << 8) | (0x00FF & byte2);
        return result;
    }

    /**
     * 整型数据拆分为长度为2的字节数组，低8位存放在序号小的元素，高8位存放在序号大的元素中
     * @param data
     * @return
     */
    public static byte[] chaiFenDataIntTo2Byte(int data){
        byte[] byteArray = new byte[2];
        byteArray[0] = (byte) data;
        byteArray[1] = (byte) (data >> 8);
        return byteArray;
    }


    /**
     * 将byte数组转成十六进制形式的字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

}
