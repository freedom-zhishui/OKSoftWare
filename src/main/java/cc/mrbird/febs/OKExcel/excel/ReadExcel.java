package cc.mrbird.febs.OKExcel.excel;

import cc.mrbird.febs.OKExcel.entity.OKContent;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
@Slf4j
public class ReadExcel {

        // 读取excel
        public static Workbook readExcel(String filePath) {
            Workbook wb = null;
            if (filePath == null) {
                return null;
            }
            String extString = filePath.substring(filePath.lastIndexOf("."));
            InputStream is = null;
            try {
                is = new FileInputStream(filePath);
                if (".xls".equals(extString)) {
                    return wb = new HSSFWorkbook(is);
                } else if (".xlsx".equals(extString)) {
                    return wb = new XSSFWorkbook(is);
                } else {
                    return wb = null;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return wb;
        }

        public static Object getCellFormatValue(Cell cell) {
            Object cellValue = null;
            if (cell != null) {
                // 判断cell类型
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC: {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                        break;
                    }
                    case Cell.CELL_TYPE_FORMULA: {
                        // 判断cell是否为日期格式
                        if (DateUtil.isCellDateFormatted(cell)) {
                            // 转换为日期格式YYYY-mm-dd
                            cellValue = cell.getDateCellValue();
                        } else {
                            // 数字
                            cellValue = String.valueOf(cell.getNumericCellValue());
                        }
                        break;
                    }
                    case Cell.CELL_TYPE_STRING: {
                        cellValue = cell.getRichStringCellValue().getString();
                        break;
                    }
                    default:
                        cellValue = "";
                }
            } else {
                cellValue = "";
            }
            return cellValue;
        }
        // 读取excel内容，并存放在json中
        public static String  getExcelContent(String filePath){
            Workbook wb = null;
            Sheet sheet = null;
            List<Map<String, String>> list = null;
            List  arrayList  = new ArrayList();
            List  duplicateList  = new ArrayList();
            Map<String, Object>  mapResult  = new HashMap<String, Object>();
            String cellData = null;
            String  result;
            wb = readExcel(filePath);
            Map<String,Object>  hashMap = new HashMap<>();
            result  = parsesExcel(wb,arrayList,duplicateList,mapResult,list,sheet,hashMap);

            return result;
        }

    private static String parsesExcel(Workbook wb, List arrayList, List duplicateList, Map<String, Object> mapResult, List<Map<String, String>> list, Sheet sheet, Map<String, Object> hashMap) {
        Row row = null;
        if (wb != null) {
            // 用来存放表中数据
            list = new ArrayList<Map<String, String>>();
            // 获取第一个sheet
            // wb.getSheet(0)
            int sheetNum = wb.getNumberOfSheets();
            System.out.println("sheet个数：" + sheetNum);
            // 收集订单号
            String orderNumbers ="";
            for (int k = 0; k < sheetNum; k++) {

                sheet = wb.getSheetAt(k);

                String sheetName = sheet.getSheetName();// sheet名称，用于校验模板是否正确

                // 获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();
                mapResult.put("count","sheet1共"+rownum+"行");
                // 获取第一行（根据标题获取我想要的数据colnum值）  备注：21   物流单号：16
                row = sheet.getRow(0);

                if (row != null) {
                    // 获取最大列数
                    int colnum = row.getPhysicalNumberOfCells();
                    mapResult.put("colnum","sheet1共"+colnum+"列");
                    // 物流单号在excel中列的下标
                    int courierIndex = 0;
                    // 备注在excel中的列下标
                    int orderIndex = 0;
                    // 物流公司在excel中的列下标
                    int companyIndex = 0;
                    // 客户在excel中的列下标
                    int customerIndex = 0;
                    // 产品名称在excel中的列下标
                    int productNameIndex = 0;
                    // 发货单号在excel中的列下标
                    int trackNumberIndex = 0;

                    // 获取指定列的在rows中的下标
                    for(int g = 0;g<colnum;g++){
                        String name = (String)getCellFormatValue(row.getCell(g));
                        switch (name) {
                            case "物流单号":
                                courierIndex = g;
                                break;
                            case "备注":
                                orderIndex = g;
                                break;
                            case "物流公司":
                                companyIndex = g;
                                break;
                            case "顾客":
                                customerIndex = g;
                                break;
                            case "产品名称":
                                productNameIndex = g;
                                break;
                            case "发货单号":
                                trackNumberIndex = g;
                                break;
                        }
                    }
                    for (int i = 1; i < rownum; i++) {
                        Map<String, String> map = new LinkedHashMap<String, String>();
                        row = sheet.getRow(i);
                        if (row != null) {
                                /*excel备注一栏的信息中有含有沐海官网的订单号，但是备注处目前有两种情况：
                                  1:\2021041103220701、0801、0901   这种代表的是三个订单共用一个 物流单号  例：\2021041103220701、0801  俞丹=磁悬浮360-540（3框3画）
                                  2：同一个订单号  3个快递单号，3个收件人，3个产品
                                 */

                            // 1、取备注内容、获取物流单号
                            String note = (String)getCellFormatValue(row.getCell(orderIndex));
                            String courierNumbers = (String) getCellFormatValue(row.getCell(courierIndex));
                            String express = (String) getCellFormatValue(row.getCell(companyIndex));
                            String customer = (String) getCellFormatValue(row.getCell(customerIndex));
                            String productName = (String) getCellFormatValue(row.getCell(productNameIndex));
                            String trackNumber = (String) getCellFormatValue(row.getCell(trackNumberIndex));
                            if(StringUtils.isBlank(note)) {
                                continue;
                            }else{
                                //1.1 将备注内容进行截取，截取格式：\2021041103220701、0801  俞丹=磁悬浮360-540（3框3画）
                                String year  = getSysYear();
                                //当前年加一或者减一都可以匹配过
                                String oldYear = String.valueOf(NumberUtils.toInt(year)-1);
                                String newYear = String.valueOf(NumberUtils.toInt(year)+1);


                                // 沐海官网订单号是以 年+月+日+时分秒
                                if(note.contains(year) || note.contains(oldYear)  || note.contains(newYear)){
                                    // 截取符合需求的沐海订单号
                                    int index  = getWordIndex(note);
                                    note  = note.substring(0,index).trim().replace("\\","");
                                    String contact  = note.substring(0,12);  // 截取订单的前12位作为拼接头
                                    //1.2 如果截取的数值包含“、”，split(",")
                                    if (note.contains("、")){
                                        String[] noteArray  = note.split("、");
                                        for (int j=0;j<noteArray.length;j++) {
                                            OKContent okContent  = new OKContent();
                                            if(j>0){
                                                okContent.setOrderNumber(contact+noteArray[j]);
                                                // 将信息放入map中，用于后面的校验工作
                                                hashMap.put(""+contact+noteArray[j],contact+noteArray[j]);
                                                // 收集订单号
                                                orderNumbers  +=contact+noteArray[j]+',';
                                            }else{
                                                okContent.setOrderNumber(noteArray[j]);
                                                hashMap.put(""+noteArray[j],noteArray[j]);
                                                orderNumbers  +=noteArray[j]+',';
                                            }
                                            okContent.setCourierNumbers(courierNumbers);
                                            okContent.setExpress(express);
                                            okContent.setCustomer(customer);
                                            okContent.setProductName(productName);
                                            okContent.setTrackNumber(trackNumber);
                                            arrayList.add(okContent);

                                        }
                                        // 1.3 如果不含有，则要判断另外一种情况，就是存在相同订单不同物流订单情况，并且对待相同订单号只录入一条物流信息
                                        // 相同订单号使用不同的快递单号时，只更新一条物流信息
                                    }else{
                                        // 判断map中是否已含有相同的订单,如果存在则不添加
                                        Boolean flag  = hashMap.containsKey(""+note);

                                        if(!flag){
                                            OKContent okContent2  = new OKContent();
                                            okContent2.setOrderNumber(note);
                                            okContent2.setCourierNumbers(courierNumbers);
                                            okContent2.setExpress(express);
                                            okContent2.setCustomer(customer);
                                            okContent2.setProductName(productName);
                                            okContent2.setTrackNumber(trackNumber);
                                            arrayList.add(okContent2);
                                            hashMap.put(""+note,note);
                                            orderNumbers  +=note+',';
                                        }else {
                                            // 当flag为true时，记录重复订单号的数据，并把对应的物流单号记录下来
                                            OKContent okContent1  = new OKContent();
                                            okContent1.setOrderNumber(note);
                                            okContent1.setCourierNumbers(courierNumbers);
                                            okContent1.setExpress(express);
                                            okContent1.setCustomer(customer);
                                            okContent1.setProductName(productName);
                                            okContent1.setTrackNumber(trackNumber);
                                            duplicateList.add(okContent1);

                                        }

                                    }
                                }
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            orderNumbers = orderNumbers.substring(0,orderNumbers.length()-1).substring(0,orderNumbers.length()-1);
            mapResult.put("screen","成功筛选出"+arrayList.size()+"个");
            mapResult.put("data",arrayList);
            mapResult.put("Duplicate","共"+duplicateList.size()+"数据重复");
            mapResult.put("DuplicateData",duplicateList);
            mapResult.put("orderNumbers",orderNumbers);

//            log.info("result is {}", JSONObject.toJSONString(mapResult));


        }
        return  JSONObject.toJSONString(mapResult);
    }


    // 获取需要截取字符中第一个出现的文字
    static int getWordIndex(String note) {
        int firstIndex =0;
        for (int index = 0;index<=note.length()-1;index++) {
            //将字符串拆开成单个的字符
            String w = note.substring(index, index + 1);
            if (w.compareTo("\u4e00") > 0 && w.compareTo("\u9fa5") < 0) {// \u4e00-\u9fa5 中文汉字的范围
//                System.out.println("第一个中文的索引位置:" + index + ",值是：" + w);
                firstIndex  = index;
                break;
            }
        }
        return  firstIndex;
    }

    // 判断是不是当前年的订单，只匹配当前年的订单
    public static String getSysYear() {

        Calendar date = Calendar.getInstance();

        String year = String.valueOf(date.get(Calendar.YEAR));

        return year;

    }

    public static void main(String[] args) {

//            String[] ss  = "2021041103220701,2021041103220801,2021041303228001,2021041303228201,2021040303186001,2021041403229801,2021041403229901,2021041403230001,2021041403230101,2021041403230301,2021041403230701,2021041403230801,2021041403231201,2021041403231301,2021041403231701,2021041103216901,2021041303227901,2021041403230401,2021041503234301,2021041503234401,2021041503234501,2021041503232701,2021041503232801,2021041503233101,2021041503233201,2021041503233301,2021041503233401,2021041503233901,2021041503234801,2021041503234901,2021041503235101,2021041503235201,2021041503235401,2021041603235601,2021041603236001,2021041603235701,2021041603236501,2021041603236901,2021041603237101,2021041603237201,2021041603237701,2021041603237801,2021041603238101,2021041603237501,2021041703239601,2021041703239701,2021041703238501,2021041703238601,2021041703239801,2021041703240001,2021041703240301,2021041703240501,2021041703240801,2021041703240901,2021041703241101,2021041803241501,2021041803243601,2021041803242001,2021041803242101,2021041803242601,2021041803244401,2021041803244501".split(",");
        String filePath = "D:\\模板——导出发货表格0422.xlsx";
//        String filePath = "D:\\1111.xlsx";
        String  result  = getExcelContent(filePath);
        System.out.println("测试结束="+result);
    }

}


