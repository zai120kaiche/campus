package com.campus.backend.service;

import com.alipay.api.AlipayApiException;
import com.campus.backend.entity.Alipay;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author campus
 * @since 2023-03-01
 */
public interface AlipayService extends IService<Alipay> {
    /**
     * 发起支付
     * @param outTradeNo    订单编号（唯一）
     * @param totalAmount   订单价格
     * @param subject       商品名称
     */
    String goPay(String outTradeNo, BigDecimal totalAmount, String subject) throws Exception;

    /**
     * 交易查询
     * @param outTradeNo 订单编号（唯一）
     */
    String query(String outTradeNo) throws AlipayApiException;

    /**
     * 交易关闭
     * @param outTradeNo（唯一）
     */
    String close(String outTradeNo) throws AlipayApiException;

}
