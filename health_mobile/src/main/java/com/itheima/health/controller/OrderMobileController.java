package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderMobileController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 提交预约
     * @param paraMap
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> paraMap){
        //验证码校验
        Jedis jedis = jedisPool.getResource();
        //手机号码
        String telephone = paraMap.get("telephone");
        //验证码的key
        String key = RedisMessageConstant.SENDTYPE_ORDER + ":" + telephone;
        //redis中的验证码
        String codeInRedis = jedis.get(key);
        if (StringUtil.isEmpty(codeInRedis)){
            //没值 重新发送
            return new Result(false, "请重新获取验证码!");
        }
        // 前端传的验证码
        String validateCode = paraMap.get("validateCode");
        if(!codeInRedis.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //移除redis中的验证码,防止重复提交
       // jedis.del(key);//测试时可注释掉
        paraMap.put("orderType", Order.ORDERTYPE_WEIXIN);
        //预约成功页面展示时需要用到的id
        Order order = orderService.submitOrder(paraMap);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);

    }
    /**
     * 查询预约信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        // 调用服务查询订单信息
        Map<String,Object> orderInfo = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }
}
