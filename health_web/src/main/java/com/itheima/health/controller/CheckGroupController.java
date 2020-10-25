package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;


    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务
       PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
       //响应
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,pageResult);
    }


    /**
     *添加检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        //调用业务
        checkGroupService.add(checkGroup,checkitemIds);
        //响应
        return  new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     *通过id获取检查组
     */
    @GetMapping("/findById")
    public Result findById(int checkGroupId){
        //调用业务ceng
        CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
        //响应
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 通过检查组id查询选中的检查项id
     * @param checkGroupId
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int checkGroupId){
        List<Integer> checkGroupIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroupIds);
    }

    /**
     * 提交修改
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody  CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> all = checkGroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,all);
    }
}
