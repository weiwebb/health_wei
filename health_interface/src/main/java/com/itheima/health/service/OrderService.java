package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Order;

import java.util.Map;

public interface OrderService {
    /**
     * 提交预约
     * @param paramMap
     * @return
     */
    Order submitOrder(Map<String, String> paramMap) throws HealthException;

    Map<String, Object> findById(int id);
}
