package cc.mrbird.febs.OKExcel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

@Data
@TableName("gmtx_order_log")
@Excel("订单处理历史表")
public class Gmtx_Order_Log {

    /*
     * 订单id
     */
    @TableField("order_id")
    private int orderId;

    /*
    * 角色
    */
    @TableField("log_role")
    private String logRole;
    /*
    * 操作人
    */
    @TableField("log_user")
    private String logUser;

    /*
     * 文字描述
     */
    @TableField("log_msg")
    private String logMsg;
    /*
    * 订单状态：0(已取消)10:未付款;20:已付款;30:已发货;40:已收货;
    */
    @TableField("log_orderstate")
    private String  logOrderstate;

    /*
     *处理时间
     */
    @TableField("log_time")
    private int logTime;





}







