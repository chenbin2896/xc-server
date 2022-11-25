package com.xuecheng.order.dao;

import com.xuecheng.framework.domain.order.XcOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator.
 */
public interface XcOrderRepository extends JpaRepository<XcOrders, String> {

    List<XcOrders> findAllByEndTimeBeforeAndStatusEquals(Date date, String status);
}
