package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     * 上传文件 批量导入
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

    /**
     * 通过月份查询预约设置信息
     * @param month
     * @return
     */
    List<Map> getOrderSettingByMonth(String month);
}
