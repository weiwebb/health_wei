package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     */
    Integer add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 查询套餐
     */
    List<Setmeal> findAll();

    /**
     * 查询套餐详情
     */
    Setmeal findDetailById(int id);

    /**
     * 分页查询套餐
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 通过id查询选中的检查组ids
     * @param id
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(int id);

    /**
     * 修改
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 通过id删除
     * @param id
     * @throws HealthException
     */
    void deleteById(Integer id) throws HealthException;


}
