package com.example.springbootpro.controller;


import com.example.springbootpro.service.YouZanShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/shop")
public class ShopController {

    @Autowired
    private YouZanShopService youZanShopService;

    public static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    public void findAllUser(){
        logger.info("请求已经到达");
        logger.debug("debug日志显示");
        youZanShopService.insertShop();
        logger.info("ddddd````````````````");
    }

}
