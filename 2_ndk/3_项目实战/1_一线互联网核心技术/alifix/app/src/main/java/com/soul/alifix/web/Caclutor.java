package com.soul.alifix.web;

import com.soul.alifix.Replace;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-07-01 17:42
 * UpdateUser:
 * UpdateDate: 2019-07-01 17:42
 * UpdateRemark:
 */
public class Caclutor {

    @Replace(clazz = "com.soul.alifix.Caclutor",method = "caculator")
    public int caculator() {
        return 10;
    }

    public int caculator2() {
        return 10;
    }


}
