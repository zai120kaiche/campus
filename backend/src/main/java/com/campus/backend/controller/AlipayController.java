package com.campus.backend.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.campus.backend.common.lang.Result;
import com.campus.backend.config.AlipayConfig;
import com.campus.backend.entity.Alipay;
import com.campus.backend.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-03-01
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;

    @PostMapping("/pay")
    public HashMap<String, Object> pay(@RequestBody Alipay param) throws AlipayApiException {
        Integer uid = 1;

        AlipayConfig aliPayConfig = new AlipayConfig();
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.gatewayUrl,
                aliPayConfig.app_id, aliPayConfig.merchant_private_key, aliPayConfig.FORMAT, aliPayConfig.charset,
                aliPayConfig.alipay_public_key, aliPayConfig.sign_type);

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        String idStr = IdUtil.getSnowflakeNextIdStr();//生成的流水号
        model.setOutTradeNo(idStr);//设置商家这边的流水单号 后台写
        model.setSubject(param.getSubject());//设置商品名称
        model.setTotalAmount(param.getTotal_amount());//设置付款金额
        model.setBody(param.getBody());//设置商品描述
        model.setTimeoutExpress("10m");//设置过期时间 格式：xxm 后台写
//        model.setProductCode(qrPayParam.getProduct_code());//设置产品码 - 二维码支付的话没有该功能


        request.setBizModel(model);//存入请求信息
        request.setNotifyUrl(aliPayConfig.notify_url);//设置通知地址
        request.setReturnUrl(aliPayConfig.return_url);//设置返回地址,二维码支付此功能无效

//        生成表单，二维码支付无表单
//        String form = alipayClient.pageExecute(request).getBody();

//        封装生成请求对象
        AlipayTradePrecreateResponse response = alipayClient.execute(request);

//        发送请求，获取返回信息
        System.out.println(response.getBody());

        HashMap<String, Object> map = new HashMap<>();


//        成功
        if (response.isSuccess()) {
            System.out.println("调用成功");
            JSONObject responJson = JSONUtil.parseObj(response.getBody());
            JSONObject rsj = (JSONObject) responJson.get("alipay_trade_precreate_response");
            String qr_code = (String) rsj.get("qr_code");//获取二维码地址
            map.put("qrcode", qr_code);
            map.put("out_trade_no", idStr);

            return map;


        } else {
            JSONObject responJson = JSONUtil.parseObj(response.getBody());
            JSONObject rsj = (JSONObject) responJson.get("alipay_trade_precreate_response");
            String res = (String) rsj.get("sub_msg");//获取返回错误的信息
            map.put("sub_msg", res);
            return map;
        }
    }





}
