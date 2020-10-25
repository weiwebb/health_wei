package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     *添加检查组与检查项的关系
     *      * @param checkGroupId 注意要取别名，类型相同
     * @param checkitemId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId) ;

    CheckGroup findById(int checkGroupId);

    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    void update(CheckGroup checkGroup);

    void deleteCheckGroupCheckItem(Integer id);

    Page<CheckGroup> findByCondition(String queryString);

    int findSetmealCountByCheckGroupId(int id);

    void deleteById(int id);
}
