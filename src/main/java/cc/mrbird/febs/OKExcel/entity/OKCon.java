package cc.mrbird.febs.OKExcel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
@TableName("gmtx_type")
@Excel("沐海网站表")
public class OKCon {

    @TableField("type_id")
    private long typeId;
    /*
     * 总数量
     */
    @TableField("type_name")
    private String typeName;
    /*
    * 更新数量
    */
    @TableField("type_sort")
    private int typeSort;
   /*
   *重复数量
   */
    @TableField("class_id")
    private int classId;
    /*
    * 重复数据
    */
    @TableField("class_name")
    private String className;



}







