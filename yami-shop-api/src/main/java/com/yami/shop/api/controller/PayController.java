/*
 * Copyright (c) 2018-2999 广州亚米信息科技有限公司 All rights reserved.
 *
 * https://www.gz-yami.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.yami.shop.api.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.yami.shop.api.config.ApiConfig;
import com.yami.shop.bean.app.param.PayParam;
import com.yami.shop.bean.pay.PayInfoDto;
import com.yami.shop.common.util.Arith;
import com.yami.shop.common.util.IPHelper;
import com.yami.shop.security.service.YamiUser;
import com.yami.shop.security.util.SecurityUtils;
import com.yami.shop.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.UUID;


@RestController
@RequestMapping("/p/order")
@Api(tags = "订单接口")
@AllArgsConstructor
public class PayController {

    private final PayService payService;

    private final ApiConfig apiConfig;

    private final WxPayService wxMiniPayService;

    /**
     * 支付接口
     */
    @PostMapping("/pay")
    @ApiOperation(value = "根据订单号进行支付", notes = "根据订单号进行支付")
    @SneakyThrows
    public ResponseEntity<WxPayMpOrderResult> pay(@RequestBody PayParam payParam) {
        YamiUser user = SecurityUtils.getUser();
        String userId = user.getUserId();
        String openId = user.getBizUserId();


        PayInfoDto payInfo = payService.pay(userId, payParam);

        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody(payInfo.getBody());
        orderRequest.setOutTradeNo(payInfo.getPayNo());
        orderRequest.setTotalFee((int) Arith.mul(payInfo.getPayAmount(), 100));
        orderRequest.setSpbillCreateIp(IPHelper.getIpAddr());
        orderRequest.setNotifyUrl(apiConfig.getDomainName() + "/notice/pay/order");
        orderRequest.setTradeType(WxPayConstants.TradeType.MWEB);
        orderRequest.setOpenid(openId);

        return ResponseEntity.ok(wxMiniPayService.createOrder(orderRequest));
    }

    /**
     * 普通支付接口
     */
    @PostMapping("/normalPay")
    @ApiOperation(value = "根据订单号进行支付", notes = "根据订单号进行支付")
    @SneakyThrows
    public ResponseEntity<Boolean> normalPay(@RequestBody PayParam payParam) {

        String wxPayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String appid = "";
        String mch_id = "1609138353";
        String nonce_str =    UUID.randomUUID().toString()  ;
        String sign = "";
        String body = "";
        String out_trade_no = "";
        String spbill_create_ip = "";
        String notify_url = "";
        String trade_type = "MWEB";
        String scene_info = "MWEB";

        String stringA="";
//        String stringSignTemp=stringA+"&key=192006250b4c09247ec02edce69f6a2d" //注：key为商户平台设置的密钥key
//
//        sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7" //注：MD5签名方式
//
//
//        HMAC-SHA256签名方式：
//
//        stringSignTemp=stringA+"&key=192006250b4c09247ec02edce69f6a2d" //注：key为商户平台设置的密钥key
//
//        sign=hash_hmac("sha256",stringSignTemp,key).toUpperCase()="6A9AE1657590FD6257D693A078E1C3E4BB6BA4DC30B23E0EE2496E54170DACD6" //注：HMAC-SHA256签名方式，部分语言的hmac方法生成结果二进制结果，需要调对应函数转化为十六进制字符串。


        YamiUser user = SecurityUtils.getUser();
        String userId = user.getUserId();
        PayInfoDto pay = payService.pay(userId, payParam);

        // 根据内部订单号更新order settlement
        payService.paySuccess(pay.getPayNo(), "");

        return ResponseEntity.ok(true);
    }
}
