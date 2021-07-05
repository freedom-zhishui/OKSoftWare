package cc.mrbird.febs.OKExcel.service;

import cc.mrbird.febs.OKExcel.entity.Gmtx_Order;
import cc.mrbird.febs.OKExcel.entity.Gmtx_Order_Common;
import cc.mrbird.febs.OKExcel.entity.OKContent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author MrBird
 */
public interface GmtxOrderCommonService extends IService<Gmtx_Order_Common> {
    int updateOrderCommon(Gmtx_Order_Common orderCommon);
}
