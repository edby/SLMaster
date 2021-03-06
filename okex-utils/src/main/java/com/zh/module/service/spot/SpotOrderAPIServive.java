package com.zh.module.service.spot;

import com.zh.module.bean.spot.param.OrderParamDto;
import com.zh.module.bean.spot.param.PlaceOrderParam;
import com.zh.module.bean.spot.result.BatchOrdersResult;
import com.zh.module.bean.spot.result.Fills;
import com.zh.module.bean.spot.result.OrderInfo;
import com.zh.module.bean.spot.result.OrderResult;

import java.util.List;
import java.util.Map;

public interface SpotOrderAPIServive {
    /**
     * 添加订单
     *
     * @param order
     * @return
     */
    OrderResult addOrder(PlaceOrderParam order);

    /**
     * 批量下单
     *
     * @param order
     * @return
     */
    Map<String, List<OrderResult>> addOrders(List<PlaceOrderParam> order);

    /**
     * 取消单个订单 delete协议
     *
     *  @param order
     * @param orderId
     */
    OrderResult cancleOrderByOrderId(final PlaceOrderParam order, String orderId);

    /**
     * 取消单个订单 post协议
     *
     * @param order
     * @param orderId
     */
    OrderResult cancleOrderByOrderId_post(final PlaceOrderParam order, String orderId);

    /**
     * 批量取消订单 delete协议
     *
     * @param cancleOrders
     * @return
     */
    Map<String, BatchOrdersResult> cancleOrders(final List<OrderParamDto> cancleOrders);

    /**
     * 批量取消订单 post协议
     *
     * @param cancleOrders
     * @return
     */
    Map<String, BatchOrdersResult> cancleOrders_post(final List<OrderParamDto> cancleOrders);

    /**
     * 单个订单
     * @param product
     * @param orderId
     * @return
     */
    OrderInfo getOrderByOrderId(String product, String orderId);

    /**
     * 订单列表
     *
     * @param product
     * @param status
     * @param from
     * @param to
     * @param limit
     * @return
     */
    List<OrderInfo> getOrders(String product, String status, String from, String to, String limit);

    /**
     * 订单列表
     *
     * @param from
     * @param to
     * @param limit
     * @return
     */
    List<OrderInfo> getPendingOrders(String from, String to, String limit, String instrument_id);

    /**
     * 账单列表
     *
     * @param orderId
     * @param product
     * @param from
     * @param to
     * @param limit
     * @return
     */
    List<Fills> getFills(String orderId, String product, String from, String to, String limit);
}
