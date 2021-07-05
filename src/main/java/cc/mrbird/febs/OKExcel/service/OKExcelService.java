package cc.mrbird.febs.OKExcel.service;

import cc.mrbird.febs.OKExcel.entity.OKExcel;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.job.entity.Job;
import cc.mrbird.febs.job.entity.JobLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author MrBird
 */
public interface OKExcelService extends IService<OKExcel> {

    IPage<OKExcel> findOKExcelList(QueryRequest request,OKExcel okExcel);
}
