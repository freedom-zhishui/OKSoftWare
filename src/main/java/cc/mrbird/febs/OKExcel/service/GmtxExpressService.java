package cc.mrbird.febs.OKExcel.service;

import cc.mrbird.febs.OKExcel.entity.Gmtx_Express;
import cc.mrbird.febs.OKExcel.entity.Gmtx_Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author MrBird
 */
public interface GmtxExpressService extends IService<Gmtx_Express> {
    Gmtx_Express findgmtxExpress(Gmtx_Express gmtx_express);
}
