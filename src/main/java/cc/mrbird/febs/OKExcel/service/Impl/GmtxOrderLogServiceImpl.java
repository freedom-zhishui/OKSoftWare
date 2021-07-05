package cc.mrbird.febs.OKExcel.service.Impl;


import cc.mrbird.febs.OKExcel.entity.Gmtx_Order_Common;
import cc.mrbird.febs.OKExcel.entity.Gmtx_Order_Log;
import cc.mrbird.febs.OKExcel.mapper.Gmtx_Order_CommonMapper;
import cc.mrbird.febs.OKExcel.mapper.Gmtx_Order_LogMapper;
import cc.mrbird.febs.OKExcel.service.GmtxOrderLogService;
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
public class GmtxOrderLogServiceImpl extends ServiceImpl<Gmtx_Order_LogMapper, Gmtx_Order_Log> implements GmtxOrderLogService {

    @Override
    public void saveLog(Gmtx_Order_Log orderLog) {
        this.save(orderLog);

    }
}
