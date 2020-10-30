package com.itheima.health;

import com.itheima.health.Utils.SMSUtils;

public class SMSMain {
    public static void main(String[] args) throws Exception{
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,"13543300177","888888");
    }
}
