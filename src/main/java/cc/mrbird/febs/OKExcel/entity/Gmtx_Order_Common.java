package cc.mrbird.febs.OKExcel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

@Data
@TableName("gmtx_order_common")
@Excel("订单信息扩展表")
public class Gmtx_Order_Common {
/*
            order_sn
    pay_sn
            store_id
    store_name
            buyer_id
    buyer_name
            buyer_email
    add_time
            payment_code
    payment_time
            finnshed_time
    goods_amount
            order_amount
    rcb_amount
            pd_amount
    shipping_fee
            evaluation_state
    order_state
            refund_state
    lock_state
            delete_state
    refund_amount
            delay_time
    order_from
            shipping_code
    out_payment_code
            jiaji
    express
            add_logo
    cpage
            store_message
    erp_num
            is_transport_free
    xianprice
            yucunprice
    is_zhizuo
            zs_info*/



    @TableField("order_id")
    private long orderId;


    /*
     * 卖家店铺id
     */
    @TableField("store_id")
    private String storeId;

    /*
     * 发货备注
     */
    @TableField("deliver_explain")
    private String deliverExplain;

    /*
     * 配送公司ID
     */
    @TableField("shipping_express_id")
    private Long shippingExpressId;

    /*
     * 配送时间
     */
    @TableField("shipping_time")
    private int shipping_time;
//    /*
//    * 状态
//    */
//    @TableField("e_state")
//    private int eState;
//   /*
//   *编号
//   */
//    @TableField("code")
//    private int code;
//    /*
//    * 首字母
//    */
//    @TableField("e_letter")
//    private String eletter;
//    /*
//    * 1常用2不常用
//    */
//    @TableField("e_order")
//    private String eOrder;
//    /*
//    * 公司网址
//    */
//    @TableField("e_url")
//    private String eUrl;
//
//    /*
//    * 是否支持服务站配送0否1是
//    */
//    @TableField("e_zt_state")
//    private String eZtState;



}







