package com.xuecheng.order.controller;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.order.XcOrderControllerApi;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.order.XcOrders;
import com.xuecheng.framework.domain.order.XcOrdersDetail;
import com.xuecheng.framework.domain.order.request.CreateOrderRequest;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.order.client.CourseBaseClient;
import com.xuecheng.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author User
 * @date 2019/12/7
 * @description
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController implements XcOrderControllerApi {

    @Autowired
    CourseBaseClient courseBaseClient;

    @Autowired
    OrderService orderService;

    @Override
    @Transactional
    @PostMapping("/create")
    public ResponseResult createOrder(@RequestAttribute String userId, @RequestBody CreateOrderRequest createOrderRequest) {
        XcOrders xcOrders = new XcOrders();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 2);

        xcOrders.setStartTime(new Date());
        xcOrders.setEndTime(calendar.getTime());
        xcOrders.setOrderNumber(String.valueOf(System.currentTimeMillis()));
        xcOrders.setUserId(userId);
        ResponseResult result = courseBaseClient.courseview(createOrderRequest.getCourseId());
        CourseView courseview = (CourseView) result.getData();
        xcOrders.setInitialPrice(courseview.getCourseMarket().getPrice_old() == null ? courseview.getCourseMarket().getPrice() : courseview.getCourseMarket().getPrice_old());
        xcOrders.setPrice(courseview.getCourseMarket().getPrice());
        xcOrders.setStatus("401001");

        XcOrdersDetail xcOrdersDetail = new XcOrdersDetail();
        xcOrdersDetail.setItemId(createOrderRequest.getCourseId());
        xcOrdersDetail.setItemNum(1);
        xcOrdersDetail.setItemPrice(courseview.getCourseMarket().getPrice());
        xcOrdersDetail.setOrderNumber(xcOrders.getOrderNumber());
        xcOrdersDetail.setStartTime(new Date());
        xcOrdersDetail.setEndTime(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
        xcOrdersDetail.setValid("204001");
        List<XcOrdersDetail> list = new ArrayList<>();
        list.add(xcOrdersDetail);
        String xcDetail = JSON.toJSONString(list);
        xcOrders.setDetails(xcDetail);
        ResponseResult responseResult = orderService.saveOrder(xcOrders);
        if (!responseResult.isSuccess()) {
            return responseResult;
        }
        ResponseResult responseResult1 = orderService.batchSaveXcOrderDetail(list);
        if (!responseResult1.isSuccess()) {
            return responseResult;
        }
        return ResponseResult.SUCCESS();
    }

    @Override
    @PostMapping("/list/{page}/{size}")
    public ResponseResult list(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody XcOrders xcOrders) {
        return ResponseResult.SUCCESS(orderService.list(page, size, xcOrders));
    }

    @Override
    @GetMapping("/get/{orderNum}")
    public ResponseResult getOrderById(@PathVariable("orderNum") String orderNum) {
        return ResponseResult.SUCCESS(orderService.get(orderNum));
    }

}
