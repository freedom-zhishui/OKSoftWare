package cc.mrbird.febs.OKExcel.service.Impl;


import cc.mrbird.febs.OKExcel.entity.Gmtx_Express;
import cc.mrbird.febs.OKExcel.mapper.Gmtx_ExpressMapper;
import cc.mrbird.febs.OKExcel.service.GmtxExpressService;
import cc.mrbird.febs.OKExcel.service.GmtxOrderService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GmtxExpressServiceImpl extends ServiceImpl<Gmtx_ExpressMapper, Gmtx_Express> implements GmtxExpressService {

    @Override
    @DS("muhai")
    public Gmtx_Express findgmtxExpress(Gmtx_Express gmtx_express) {
        LambdaQueryWrapper<Gmtx_Express> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(gmtx_express.getEName())) {
            queryWrapper.like(Gmtx_Express::getEName, gmtx_express.getEName());
        }
        return baseMapper.selectOne(queryWrapper);
    }
}
