package com.example.a111111.diancan.ctd.inside;

import java.util.Comparator;
//按照年龄的升序排列     实现接口          泛型是自定义类，里面有要排序的内容
public class NumSort implements Comparator<dininglist>{

    @Override         //两个参数是泛型的对象
    public int compare(dininglist o1, dininglist o2) {
        //按照姓名的升序排列，前面加个负号就按照降序排列
        return -Integer.parseInt(o1.getNum())+Integer.parseInt(o2.getNum());
    }
}