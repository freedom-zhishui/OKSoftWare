package cc.mrbird.febs.OKExcel.service;

import cc.mrbird.febs.OKExcel.entity.OKCon;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author MrBird
 */
@DS("muhai")
public interface OKConService extends IService<OKCon> {

    IPage<OKCon> findOKConList(QueryRequest request, OKCon okExcel);


}
