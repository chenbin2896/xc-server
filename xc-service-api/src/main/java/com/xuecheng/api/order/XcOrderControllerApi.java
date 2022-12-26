package com.xuecheng.api.order;

import com.xuecheng.framework.domain.order.XcOrders;
import com.xuecheng.framework.domain.order.request.CreateOrderRequest;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author User
 * @date 2019/12/7
 * @description
 */
@Tag(name = "订单管理", description = "订单管理")
public interface XcOrderControllerApi {
    @Operation(summary = "创建订单")
    ResponseResult createOrder(String userId, CreateOrderRequest createOrderRequest);

    @Operation(summary = "订单列表")
    ResponseResult list(int page, int size, XcOrders xcOrders);

    @Operation(summary = "根据ID查询订单")
    ResponseResult getOrderById(String orderNum);


}
