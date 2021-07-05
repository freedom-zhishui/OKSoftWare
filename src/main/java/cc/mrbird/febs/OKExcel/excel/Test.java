package cc.mrbird.febs.OKExcel.excel;

import static cc.mrbird.febs.OKExcel.excel.ReadExcel.getSysYear;
import static cc.mrbird.febs.OKExcel.excel.ReadExcel.getWordIndex;
public class Test {
    public static void main(String[] args) {
        getExcelContent();



    }

    private static void getExcelContent() {
        //  订单为1041703240801、0901、0621、1987、3685 丁爱玲=磁悬浮560-840   已解决
        String note  = "\\2021041703240801、0901、0621、1987、3685 丁爱玲=磁悬浮560-840";
//        String note2 = "\\2021041703241101 广州海马体天汇广场IGC大师店=金属手撕毛边360-540";
        //1.1 将备注内容进行截取，截取格式：\2021041103220701、0801  俞丹=磁悬浮360-540（3框3画）
        String year  = getSysYear();
        // 沐海官网订单号是以 年+月+日+时分秒
        if(note.contains(year)){
            System.out.println("日期="+note);
        }
        // 截取符合需求的沐海订单号
        int index  = getWordIndex(note);
        note  = note.substring(0,index).trim().replace("\\","");
        //1.2 如果截取的数值包含“、”，split(",")
        String contact  = note.substring(0,11);  // 截取订单的前12位作为拼接头
        if (note.contains("、")){
            String[] noteArray  = note.split("、");
            for (int i=0;i<noteArray.length;i++) {
                if(i>0){
                    System.out.println("拼接的订单号="+contact+noteArray[i]);
                }else{
                    System.out.println("首个订单号="+noteArray[i]);
                }
            }
        }
    }
}
