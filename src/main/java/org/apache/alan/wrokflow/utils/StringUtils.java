package org.apache.alan.wrokflow.utils;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return getRandomString(length,str);
    }

    public static String getRandomString(int length,String str){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandmoPwd(int length){
        return getRandomString(length,"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }


    public static String monenyToPenny(BigDecimal amount){
        // 单位分
        amount = amount.multiply(new BigDecimal(100));
        //取整
        amount = amount.setScale(0, RoundingMode.DOWN);
        return String.valueOf(amount);
    }

     public static String getUUId(int length) {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase().substring(0,length);
    }

    public static String numRandomGenerator() {
        long random = (long) ((Math.random() * 9.0 + 1) * Math.pow(10, 8 - 1));
        return Long.toString(random);
    }

    public static String dataMasking(String data) {
        String str = data.substring(0,data.length()-4);
        str = str.replaceAll(".","*");
        String str1 = data.substring(data.length()-4);
        return str+str1;
    }

    public static String strSpecialFilter(String str) {
        String regEx = "[\\u00A0\\s\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //将所有的特殊字符替换为空字符串
        return m.replaceAll("").trim();
    }
}
