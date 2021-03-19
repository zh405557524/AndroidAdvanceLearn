package com.soul.aop.invocationhandler;

/**
 * Description: TODO
 * Author: zhuMing
 * CreateDate: 2021/3/19 9:11
 * ProjectName: Aop
 * UpdateUser:
 * UpdateDate: 2021/3/19 9:11
 * UpdateRemark:
 */
public interface DBOperation {
    void insert();

    void delete();

    void update();

    void save();
}
