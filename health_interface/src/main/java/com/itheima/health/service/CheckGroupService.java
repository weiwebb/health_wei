package com.itheima.health.service;

import com.itheima.health.pojo.CheckGroup;

public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);
}
