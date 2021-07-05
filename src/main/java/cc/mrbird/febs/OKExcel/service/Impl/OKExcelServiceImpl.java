package cc.mrbird.febs.OKExcel.service.Impl;

import cc.mrbird.febs.OKExcel.entity.OKCon;
import cc.mrbird.febs.OKExcel.entity.OKExcel;
import cc.mrbird.febs.OKExcel.mapper.OKExcelMapper;
import cc.mrbird.febs.OKExcel.service.OKExcelService;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.job.entity.JobLog;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author MrBird
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OKExcelServiceImpl extends ServiceImpl<OKExcelMapper, OKExcel> implements OKExcelService {

    @Override
    public IPage<OKExcel> findOKExcelList(QueryRequest request, OKExcel okExcel) {
        LambdaQueryWrapper<OKExcel> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(okExcel.getExcelName())) {
            queryWrapper.eq(OKExcel::getExcelName, okExcel.getExcelName());
        }
        Page<OKExcel> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "id", FebsConstant.ORDER_DESC, true);
        return page(page, queryWrapper);
    }
}
