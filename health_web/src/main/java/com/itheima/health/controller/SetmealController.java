package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.Utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //获取原有图片名称,截取到后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成唯一文件名,拼接后缀名
        String filename = UUID.randomUUID() + extension;
        //调用七牛云上传文件
        //- 返回数据给页面
        //{
        //    flag:
        //    message:
        //    data:{
        //        imgName: 图片名,
        //        domain: QiNiuUtils.DOMAIN
        //    }
        //}
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), filename);
            Map<String, String> map = new HashMap<String, String>();
            map.put("imgName", filename);
            map.put("domain", QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {

        /**
         * 生成移动端静态页面,整合项目
         * 修改添加、修改、删除的方法，添加相应的redis操作
         */
        // 调用服务添加
        Integer setmealId = setmealService.add(setmeal, checkgroupIds);
        Jedis jedis = jedisPool.getResource();
        String key = "setmeal:static:html";
        jedis.sadd(key, setmealId + "|1|" + System.currentTimeMillis());
        jedis.close();
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //掉用业务
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
    }

    @GetMapping("findById")
    public Result findById(int id) {
        //调用业务
        Setmeal setmeal = setmealService.findById(id);
        // 前端要显示图片需要全路径
        // 封装到map中，解决图片路径问题
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("setmeal", setmeal);
        resultMap.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, resultMap);
    }

    /**
     * 通过id查询选中的检查组ids
     */
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id) {
        List<Integer> checkgroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkgroupIds);
    }

    /**
     * 更新套餐
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        // 调用服务修改
        setmealService.update(setmeal, checkgroupIds);
        Jedis jedis = jedisPool.getResource();
        jedis.sadd("setmeal:static:html", setmeal.getId() + "|1|" + System.currentTimeMillis());
        jedis.close();
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    // 删除套餐
    @PostMapping("/deleteById")
    public Result deleteById(Integer id) {
        setmealService.deleteById(id);
        Jedis jedis = jedisPool.getResource();
// 操作符0代表删除
        jedis.sadd("setmeal:static:html", id + "|0|" + System.currentTimeMillis());
        jedis.close();
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}

