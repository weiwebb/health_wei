package com.itheima.health.dao;

import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    List<Setmeal> findAll();

    Setmeal findDetailById(int id);
}
