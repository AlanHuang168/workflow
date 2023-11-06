package org.apache.alan.wrokflow.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class BigDecimalUtil {
    /**
     * 价格类型 保留小数点 2
     */
    public static final int PRICE_DECIMAL_POINT = 2;

    /**
     * 价格类型 保留小数点 6
     */
    public static final int SIX_PRICE_DECIMAL_POINT = 6;

    /**
     * 重量类型保留小数点 3
     */
    public static final int WEIGHT_DECIMAL_POINT = 3;

    /**
     * 金额如果为空则返回0
     * @param num
     * @return
     */
    public static BigDecimal Null2bigDecimalZERO(BigDecimal num) {
        if (num==null){
            return BigDecimal.ZERO;
        }
        return num;
    }

    /**
     * string 转换成  BigDecimal，如果为null  变换成0
     * @param str
     * @return
     */
    public static BigDecimal str2BigDecimal(String str) {
        if (str==null){
            return BigDecimal.ZERO;
        }
        return new BigDecimal(str);
    }

    /**
     * 金额相关计算方法：四舍五入 保留2位小数点
     */
    public static class Amount {

        public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.add(num1, num2,PRICE_DECIMAL_POINT);
        }
        public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.multiply(num1,num2, PRICE_DECIMAL_POINT);
        }
        public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.subtract(num1,num2,PRICE_DECIMAL_POINT);
        }
        public static BigDecimal divide(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.divide(num1,num2,PRICE_DECIMAL_POINT);
        }
    }

    /**
     * 金额相关计算方法：四舍五入 保留6位小数点
     */
    public static class AmountSix {

        public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.add(num1, num2,SIX_PRICE_DECIMAL_POINT);
        }
        public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.multiply(num1,num2, SIX_PRICE_DECIMAL_POINT);
        }
        public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.subtract(num1,num2,SIX_PRICE_DECIMAL_POINT);
        }
        public static BigDecimal divide(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.divide(num1,num2,SIX_PRICE_DECIMAL_POINT);
        }
    }

    /**
     * 重量相关计算方法：四舍五入 保留3位小数点
     */
    public static class Weight {

        public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.add(num1, num2,WEIGHT_DECIMAL_POINT);
        }
        public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.multiply(num1,num2, WEIGHT_DECIMAL_POINT);
        }
        public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.subtract(num1,num2,WEIGHT_DECIMAL_POINT);
        }
        public static BigDecimal divide(BigDecimal num1, BigDecimal num2) {
            return BigDecimalUtil.divide(num1,num2,WEIGHT_DECIMAL_POINT);
        }
    }

    /**
     * BigDecimal 加法 num1 + num2
     * 未做非空校验
     *
     * @param num1
     * @param num2
     * @param point 请使用BigDecimalUtils.PRICE_DECIMAL_POINT | BigDecimalUtils.WEIGHT_DECIMAL_POINT
     * @return BigDecimal
     */
    public static BigDecimal add(BigDecimal num1, BigDecimal num2, int point) {
        return setScale(num1.add(num2), point);
    }

    /**
     * BigDecimal 乘法 num1 x num2
     * 未做非空校验
     *
     * @param num1
     * @param num2
     * @param point 请使用BigDecimalUtils.PRICE_DECIMAL_POINT | BigDecimalUtils.WEIGHT_DECIMAL_POINT
     * @return BigDecimal
     */
    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2, int point) {
        return setScale(num1.multiply(num2), point);
    }

    /**
     * BigDecimal 减法 num1 - num2
     * 未做非空校验
     *
     * @param num1
     * @param num2
     * @param point 请使用BigDecimalUtils.PRICE_DECIMAL_POINT | BigDecimalUtils.WEIGHT_DECIMAL_POINT
     * @return BigDecimal
     */
    public static BigDecimal subtract(BigDecimal num1, BigDecimal num2, int point) {
        return setScale(num1.subtract(num2), point);
    }

    /**
     * BigDecimal 除法 num1/num2
     *
     * 分子分母有一个为0  结果都为0
     *
     * @param num1
     * @param num2
     * @param point 请使用BigDecimalUtils.PRICE_DECIMAL_POINT | BigDecimalUtils.WEIGHT_DECIMAL_POINT
     * @return BigDecimal
     */
    public static BigDecimal divide(BigDecimal num1, BigDecimal num2, int point) {
        if (BigDecimalUtil.isEmpty(num1)||BigDecimalUtil.isEmpty(num2)){
            return BigDecimal.ZERO;
        }
        return num1.divide(num2, point, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(BigDecimal num1, BigDecimal num2, int point,RoundingMode roundingMode) {
        if (BigDecimalUtil.isEmpty(num1)||BigDecimalUtil.isEmpty(num2)){
            return BigDecimal.ZERO;
        }
        return num1.divide(num2, point,roundingMode);
    }


    /**
     * 设置小数点类型为 四舍五入
     *
     * @param num
     * @param point
     * @return BigDecimal
     */
    public static BigDecimal setScale(BigDecimal num, int point) {
        return num.setScale(point, RoundingMode.HALF_UP);
    }

    /**
     * 比较 num1 是否大于 num2
     *
     * @param num1
     * @param num2
     * @return boolean
     */
    public static boolean isGreaterThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0;
    }

    /**
     * 比较 num1 是否大于等于 num2
     *
     * @param num1
     * @param num2
     * @return boolean
     */
    public static boolean isGreaterOrEqual(BigDecimal num1, BigDecimal num2) {
        return isGreaterThan(num1, num2) || equals(num1, num2);
    }

    /**
     * 比较 num1 是否小于 num2
     *
     * @param num1
     * @param num2
     * @return boolean
     */
    public static boolean isLessThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) < 0;
    }

    /**
     * 比较 num1 是否小于等于 num2
     *
     * @param num1
     * @param num2
     * @return boolean
     */
    public static boolean isLessOrEqual(BigDecimal num1, BigDecimal num2) {
        return isLessThan(num1, num2) || equals(num1, num2);
    }

    /**
     * 比较 num1 是否等于 num2
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean equals(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 0;
    }

    /**
     * 计算 num1 / num2 的百分比
     *
     * @param num1
     * @param num2
     * @return String
     */
    public static String getPercentage(BigDecimal num1, BigDecimal num2) {
        BigDecimal result = num1.divide(num2, 4, RoundingMode.HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        return percent.format(result.doubleValue());
    }

    /**
     * 计算 num1 / num2 的百分比
     *
     * @param num1
     * @param num2
     * @param point 保留几位小数
     * @return String
     */
    public static BigDecimal bigDecimalPercent(Integer num1, Integer num2, int point) {
        if (num1 == null || num2 == null) {
            return BigDecimal.ZERO;
        }
        if (num2.equals(Integer.valueOf(0))) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimalNum1 = new BigDecimal(num1);
        BigDecimal bigDecimalNum2 = new BigDecimal(num2);
        return bigDecimalPercent(bigDecimalNum1, bigDecimalNum2, point);
    }

    /**
     * 计算 num1 / num2 的百分比
     *
     * @param num1
     * @param num2
     * @param point 保留几位小数
     * @return String
     */
    public static BigDecimal bigDecimalPercent(BigDecimal num1, BigDecimal num2, int point) {
        if (num1 == null || num2 == null) {
            return BigDecimal.ZERO;
        }
        if (equals(BigDecimal.ZERO, num2)) {
            return BigDecimal.ZERO;
        }
        BigDecimal percent = num1.divide(num2, point + 2, RoundingMode.HALF_UP);
        return percent.multiply(new BigDecimal(100)).setScale(point);
    }

    /**
     * 判断num是否为空 或者 零
     *
     * @param num
     * @return String
     */
    public static Boolean isEmpty(BigDecimal num) {
        return null == num || equals(BigDecimal.ZERO, num);
    }

    public static Boolean isAllEmpty(BigDecimal... num) {
        boolean isAllEmpty=true;
        for (BigDecimal bigDecimal : num) {
            if (isNotEmpty(bigDecimal)){
                isAllEmpty=false;
                break;
            }
        }
        return isAllEmpty;
    }


    /**
     * 判断num是否 不等于null 并且不等于零
     *
     * @param num
     * @return String
     */
    public static Boolean isNotEmpty(BigDecimal num) {
        return ! isEmpty(num);
    }

    /**
     * 转换为万
     *
     * @param num
     * @param point
     * @return
     */
    public static BigDecimal convertTenThousand(BigDecimal num, int point) {
        return num.divide(new BigDecimal(10000), point, RoundingMode.HALF_UP);
    }

    /**
     * 转换为负数
     *
     * @param num
     * @return
     */
    public static BigDecimal convertToMinusNumber(BigDecimal num) {
        if (isLessOrEqual(num, BigDecimal.ZERO)) {
            return num;
        }
        return BigDecimal.ZERO.subtract(num);
    }



}
