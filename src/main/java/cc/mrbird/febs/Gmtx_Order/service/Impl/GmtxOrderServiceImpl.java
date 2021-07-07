package cc.mrbird.febs.Gmtx_Order.service.Impl;


import cc.mrbird.febs.OKExcel.entity.Gmtx_Order;
import cc.mrbird.febs.OKExcel.entity.OKContent;
import cc.mrbird.febs.OKExcel.mapper.Gmtx_OrderMapper;
import cc.mrbird.febs.OKExcel.service.GmtxOrderService;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MrBird
 */
@Slf4j
@Service
@RequiredArgsConstructor
@DS("muhai")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GmtxOrderServiceImpl extends ServiceImpl<Gmtx_OrderMapper, Gmtx_Order> implements GmtxOrderService {

    @Override
    public List<Gmtx_Order> findGmtxOrderList(Gmtx_Order gmtx_order) {
        LambdaQueryWrapper<Gmtx_Order> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(gmtx_order.getOrderSn())) {
            queryWrapper.eq(Gmtx_Order::getOrderSn, gmtx_order.getOrderSn());
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Gmtx_Order findGmtxOrder(Gmtx_Order gmtxOrder) {
        LambdaQueryWrapper<Gmtx_Order> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(gmtxOrder.getOrderSn())) {
            queryWrapper.like(Gmtx_Order::getOrderSn, gmtxOrder.getOrderSn());
        }
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateGmtxOrder(OKContent okContent,Gmtx_Order gmtxOrder) {
        QueryWrapper<Gmtx_Order> queryWrapper = new QueryWrapper<Gmtx_Order>();
        //订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
        gmtxOrder.setOrderState(30);
        queryWrapper.eq("order_id", gmtxOrder.getOrderId());
        queryWrapper.eq("store_id", gmtxOrder.getStoreId());

        int i = baseMapper.update(gmtxOrder, queryWrapper);
//        System.out.println("影响的行数：" + i);
        return i;
    }
}
