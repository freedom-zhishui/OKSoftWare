package cc.mrbird.febs.OKExcel.service.Impl;


import cc.mrbird.febs.OKExcel.entity.Gmtx_Order_Common;
import cc.mrbird.febs.OKExcel.mapper.Gmtx_Order_CommonMapper;
import cc.mrbird.febs.OKExcel.service.GmtxOrderCommonService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author MrBird
 */
@Slf4j
@Service
@RequiredArgsConstructor
@DS("muhai")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GmtxOrderCommonServiceImpl extends ServiceImpl<Gmtx_Order_CommonMapper, Gmtx_Order_Common> implements GmtxOrderCommonService {

    @Override
    public int updateOrderCommon(Gmtx_Order_Common orderCommon) {
        QueryWrapper<Gmtx_Order_Common> queryWrapper = new QueryWrapper<Gmtx_Order_Common>();
        queryWrapper.eq("order_id", orderCommon.getOrderId());
        queryWrapper.eq("store_id", orderCommon.getStoreId());

        int i = baseMapper.update(orderCommon, queryWrapper);
        System.out.println("影响的行数：" + i);

        System.out.println("影响的行数：" + i);


        return i;
    }
}
