package cc.mrbird.febs.OKExcel.entity;

import lombok.Data;

@Data
public class OKContent {
    // 物流订单号
    private String courierNumbers;
    // muhai官网订单号
    private String orderNumber;
    // 快递公司
    private String express;
    // 顾客
    private String customer;
    // 产品名称
    private String productName;
    // 发货单号
    private String  trackNumber;
}
