package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.Utils.POIUtils;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    //上传文件
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            //读取excel内容
            List<String[]> strings = POIUtils.readExcel(excelFile);

            //装成List<OrderSetting>
            List<OrderSetting> orderSettingList = new ArrayList<OrderSetting>();

            //日期格式解析
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            Date orderDate = null;
            OrderSetting os = null;

            for (String[] dataArr : strings) {
                orderDate = sdf.parse(dataArr[0]);
                int number = Integer.valueOf(dataArr[1]);

                os = new OrderSetting(orderDate, number);
                orderSettingList.add(os);
            }

            //调用业务
            orderSettingService.add(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        //调用服务端查询
      List<Map> data =  orderSettingService.getOrderSettingByMonth(month);

      //响应
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,data);
    }
}
