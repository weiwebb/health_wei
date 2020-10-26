package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    //通过日期来查询预约设置
    OrderSetting findByOrderDate(Date orderDate);

    //更新可预约数量
    void updateNumber(OrderSetting orderSetting);

    //添加预约设置
    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    void editNumberByOrderDate(OrderSetting orderSetting);
}
