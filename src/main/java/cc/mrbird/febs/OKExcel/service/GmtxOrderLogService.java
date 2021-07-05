package cc.mrbird.febs.OKExcel.service;

import cc.mrbird.febs.OKExcel.entity.Gmtx_Order_Common;
import cc.mrbird.febs.OKExcel.entity.Gmtx_Order_Log;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Puddy
 */
public interface GmtxOrderLogService extends IService<Gmtx_Order_Log> {
    void saveLog(Gmtx_Order_Log orderCLog);
}
