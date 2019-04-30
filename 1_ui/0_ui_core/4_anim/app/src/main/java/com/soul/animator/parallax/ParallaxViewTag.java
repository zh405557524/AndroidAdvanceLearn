package com.soul.animator.parallax;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/4/30 上午11:33
 * UpdateUser:
 * UpdateDate: 2019/4/30 上午11:33
 * UpdateRemark:
 */
public class ParallaxViewTag {

    protected int index;
    protected float xIn;
    protected float xOut;
    protected float yIn;
    protected float yOut;
    protected float alphaIn;
    protected float alphaOut;


    @Override
    public String toString() {
        return "ParallaxViewTag{" +
                "index=" + index +
                ", xIn=" + xIn +
                ", xOut=" + xOut +
                ", yIn=" + yIn +
                ", yOut=" + yOut +
                ", alphaIn=" + alphaIn +
                ", alphaOut=" + alphaOut +
                '}';
    }
}
