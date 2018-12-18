package com.example.springbootpro.service.impl;

import com.example.springbootpro.mapper.YouZanShopMapper;
import com.example.springbootpro.service.YouZanShopService;
import com.example.springbootpro.tools.TokenService;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanShopGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class YouZanShopServiceImpl implements YouZanShopService {

    @Autowired
    private YouZanShopMapper youZanShopMapper;

    public List<YouzanShopGetResult> getShop(){
        YZClient client = new DefaultYZClient(new Token(new TokenService().getTokenTest())); //new Sign(appKey, appSecret)
        YouzanShopGetParams youzanShopGetParams = new YouzanShopGetParams();

        YouzanShopGet youzanShopGet = new YouzanShopGet();
        youzanShopGet.setAPIParams(youzanShopGetParams);
        YouzanShopGetResult result = client.invoke(youzanShopGet);

        List<YouzanShopGetResult> list = new ArrayList<>();
        list.add(result);

        return  list;
    }

    @Override
    public void insertShop() {
        List<YouzanShopGetResult> list = getShop();
        if(!ObjectUtils.isEmpty(list)){
            youZanShopMapper.insertShop(list);
        }
    }

    /*public static void main(String[] args) {
        YouZanShopServiceImpl shopService = new YouZanShopServiceImpl();
        shopService.getShop();
    }*/
}
