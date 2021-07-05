package cc.mrbird.febs.OKExcel.service;

import cc.mrbird.febs.OKExcel.entity.Gmtx_Order;
import cc.mrbird.febs.OKExcel.entity.OKContent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author MrBird
 */
public interface GmtxOrderService extends IService<Gmtx_Order> {

    List<Gmtx_Order> findGmtxOrderList(Gmtx_Order gmtxOrder);
    Gmtx_Order findGmtxOrder(Gmtx_Order gmtxOrder);

    int updateGmtxOrder(OKContent okContent,Gmtx_Order gmtxOrder);
}
