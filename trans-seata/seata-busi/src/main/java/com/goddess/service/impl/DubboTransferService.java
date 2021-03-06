package com.goddess.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.goddess.service.ReceiveService;
import com.goddess.service.TransferService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@org.springframework.stereotype.Service
public class DubboTransferService implements TransferService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Reference
    private ReceiveService receiveService;

    @GlobalTransactional
    public String transfer(int money) {
        int resultUser = jdbcTemplate.update("INSERT INTO bank_a(money,user_name)VALUES (?,?)",-money,"james");

        receiveService.receiveMoney(money);
        if (money > 20){
            throw new RuntimeException("money too large");
        }
        return resultUser+"-";
    }

}
