package cc.mrbird.febs.OKExcel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

@Data
@TableName("gmtx_express")
@Excel("物流公司表")
public class Gmtx_Express {
    @TableField("id")
    private long id;
    /*
     * 公司名称
     */
    @TableField("e_name")
    private String eName;
    /*
    * 状态
    */
    @TableField("e_state")
    private int eState;
   /*
   *编号
   */
    @TableField("e_code")
    private String eCode;
    /*
    * 首字母
    */
    @TableField("e_letter")
    private String eLetter;
    /*
    * 1常用2不常用
    */
    @TableField("e_order")
    private String eOrder;
    /*
    * 公司网址
    */
    @TableField("e_url")
    private String eUrl;

    /*
    * 是否支持服务站配送0否1是
    */
    @TableField("e_zt_state")
    private String eZtState;



}







