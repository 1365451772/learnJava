package me.sunpeng.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sp
 * @date 2021-11-16 15:57
 */
@RestController
@RequestMapping("/api/shopify")
@Api(tags = "shopify 数据")
public class ShopifyController {

    @PostMapping(value = "/getCreateOrderJsonByWebHook",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "shopify创建订单后通过webhook推送的订单数据", notes = "shopify创建订单后通过webhook推送的订单数据")
    public String getCreateOrderJsonByWebHook(@RequestBody String content){
       return content;
    }


}
