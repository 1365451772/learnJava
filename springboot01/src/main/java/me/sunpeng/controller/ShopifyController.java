package me.sunpeng.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sp
 * @date 2021-11-16 15:57
 */
@RestController
@RequestMapping("/api/shopify")
@Api(tags = "shopify 数据")
public class ShopifyController {

    @PostMapping("/getCreateOrderJsonByWebHook")
    @ApiOperation(value = "shopify创建订单后通过webhook推送的订单数据", notes = "shopify创建订单后通过webhook推送的订单数据")
    public ResponseEntity getCreateOrderJsonByWebHook(HttpServletRequest request){
       return null;

    }


}
