package com.itheima.health.service;

import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 查询套餐
     */
    List<Setmeal> findAll();

    /**
     * 查询套餐详情
     */
    Setmeal findDetailById(int id);
}
