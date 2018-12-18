package com.example.springbootpro.mapper;

import com.example.springbootpro.entity.User;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetResult;

import java.util.List;

public interface YouZanShopMapper {

    int insertShop(List<YouzanShopGetResult> list);
}