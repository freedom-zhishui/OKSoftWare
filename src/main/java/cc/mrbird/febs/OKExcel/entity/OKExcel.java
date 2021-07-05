package cc.mrbird.febs.OKExcel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_ok_excel")
@Excel("OK软件更新沐海官网订单号表")
public class OKExcel {

    @TableField("id")
    private long id;
    /*
     * 总数量
     */
    @TableField("total")
    private String total;
    /*
    * 更新数量
    */
    @TableField("update_number")
    private String updateNumber;
   /*
   *重复数量
   */
    @TableField("repeat_number")
    private String repeatNumber;
    /*
    * 重复数据
    */
    @TableField("repeat_data")
    private String repeatData;
    /*
    *上传时间
    */
    @TableField("upload_time")
    private Date uploadTime;
    /*
    *操作人
    */
    @TableField("user_name")
    private String UserName;


    @TableField("text1")
    private String text1;
    /*
    *上传的excel名称
    */
    @TableField("excel_name")
    private String excelName;
    /*
    * excel地址
    */
    @TableField("excel_path")
    private String excelPath;
     /*
    * excel地址
    */
    @TableField("line_number")
    private String lineNumber;

    // 物流订单号
    @TableField("courier_numbers")
    private String courierNumbers;
    // muhai官网订单号
    @TableField("order_number")
    private String orderNumber;
    // 快递公司
    @TableField("express")
    private String express;
    // 顾客
    @TableField("customer")
    private String customer;
    // 产品名称
    @TableField("product_name")
    private String productName;
    // 发货单号
    @TableField("track_number")
    private String  trackNumber;

    /*
     * excel数据
     */
    @TableField(exist = false)
    private String excelData;
   // 已更新完成的 orderId，以及重复的数据
   @TableField("update_result")
   private String  updateResult;




}







