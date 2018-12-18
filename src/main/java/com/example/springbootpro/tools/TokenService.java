package com.example.springbootpro.tools;

import com.alibaba.fastjson.JSON;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanShopGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ying.tian on 2017/9/4.
 */
public class TokenService {
    @Value("${youzan.token.get.url}")
    private String youzanTokenUrl;
    @Value("${youzan.client_id}")
    private String youzanClientId;
    @Value("${youzan.client_secret}")
    private String youzanClientSecret;
    @Value("${youzan.grant_type}")
    private String youzanGrantType;
    @Value("${youzan.kdt_id}")
    private String youzanKdtId;
    private String token;
    private Logger logger = LoggerFactory.getLogger(TokenService.class);
    public  String getToken(){
        if(!StringUtils.isEmpty(token)){
            return  token;
        }
        HttpHeaders requestHeaders = createHttpHeader();
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("client_id", youzanClientId);
        paramMap.add("client_secret",youzanClientSecret);
        paramMap.add("grant_type", youzanGrantType);
        paramMap.add("kdt_id",youzanKdtId);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.postForObject(youzanTokenUrl, requestEntity, String.class);
            if (StringUtils.isNotBlank(result)&&result.contains("access_token")) {
                        token = JSON.parseObject(result).getString("access_token");
                        logger.info("token: " + token);
                        System.out.println(token);
                    return token;
            }
        } catch (RestClientException e) {
            logger.error("can not connect the url:" + youzanTokenUrl, e);
        }
        return "6e02eb7631e93a33b744c6446be0afc4";
    }

    public void setToken(String token){
        this.token=token;
    }

    private HttpHeaders createHttpHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return requestHeaders;
    }

    public  String getTokenTest(){
        if(!StringUtils.isEmpty(token)){
            return  token;
        }
        HttpHeaders requestHeaders = createHttpHeader();
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
//        paramMap.add("client_id", "48e80cc5e7b3617d5d");
        paramMap.add("client_id", "01320589d05d124004");
//        paramMap.add("client_secret","5a8a8561cb243c307a51730e814382f6");
        paramMap.add("client_secret","1e0bee4b7fcf401f76a8265025e36a7c");
        paramMap.add("grant_type", "silent");
//        paramMap.add("kdt_id","18686158");
        paramMap.add("kdt_id","40036962");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.postForObject("https://open.youzan.com/oauth/token", requestEntity, String.class);
            if (StringUtils.isNotBlank(result)&&result.contains("access_token")) {
                token = JSON.parseObject(result).getString("access_token");
                logger.info("token: " + token);
                System.out.println(token);
                return token;
            }
        } catch (RestClientException e) {
            logger.error("can not connect the url:" + "https://open.youzan.com/oauth/token", e);
        }
        return "6e02eb7631e93a33b744c6446be0afc4";
    }


    public List<YouzanShopGetResult> getShop(){
        YZClient client = new DefaultYZClient(new Token(getTokenTest())); //new Sign(appKey, appSecret)
        YouzanShopGetParams youzanShopGetParams = new YouzanShopGetParams();

        YouzanShopGet youzanShopGet = new YouzanShopGet();
        youzanShopGet.setAPIParams(youzanShopGetParams);
        YouzanShopGetResult result = client.invoke(youzanShopGet);

        List<YouzanShopGetResult> list = new ArrayList<>();
        list.add(result);

        return  list;
    }

    public static void main(String[] args) {
       // ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/application-core.xml");

        TokenService service = new TokenService();
       /* String token = service.getToken();
        System.out.println(token);
        System.out.println(service.getTokenTest());*/
       service.getShop();
    }
}
