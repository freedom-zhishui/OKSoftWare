package cc.mrbird.febs.OKCon.service.Impl;

import cc.mrbird.febs.OKExcel.entity.OKCon;
import cc.mrbird.febs.OKExcel.mapper.OKConMapper;
import cc.mrbird.febs.OKExcel.service.OKConService;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class OKServiceImpl extends ServiceImpl<OKConMapper, OKCon> implements OKConService {

    @Override
    public IPage<OKCon> findOKConList(QueryRequest request, OKCon okExcel) {
        LambdaQueryWrapper<OKCon> queryWrapper = new LambdaQueryWrapper<>();
        Page<OKCon> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "", FebsConstant.ORDER_DESC, true);
        return page(page, queryWrapper);
    }
}
