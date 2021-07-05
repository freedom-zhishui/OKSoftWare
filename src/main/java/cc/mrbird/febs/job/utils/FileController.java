package cc.mrbird.febs.job.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cc.mrbird.febs.OKExcel.entity.OKCon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static cc.mrbird.febs.OKExcel.excel.ReadExcel.getExcelContent;

@Slf4j
@RestController  //注解，组件扫描加入到spring容器中
@RequestMapping("/fileUpload") //类controller的映射路径
public class FileController {
    /**
     *  文件上传
     * @return
     */
    @RequestMapping("/uploadExcel")
        @ResponseBody
        public Object upload(MultipartFile file) {
        Map <String,Object> map  = new HashMap<String,Object>();
        try {
            String oldName = file.getOriginalFilename();
            String path="D:\\2021-06-17\\";
            if(!new File(path).exists()) {
                new File(path).mkdirs();
            }
            //获取最后一个.的位置
            int lastIndexOf = oldName.lastIndexOf(".");
            //获取文件的后缀名 .jpg
            String suffix = oldName.substring(lastIndexOf);
            String fileName=oldName.replace(".xls","").replace(".xlsx","")+System.currentTimeMillis()+suffix;
            String newPath = path+fileName;
            file.transferTo(new File(newPath));
            String  result = null;
            if(oldName.endsWith(".xls") || oldName.endsWith(".xlsx")){
                result  = getExcelContent(newPath);
            }

            map.put("result",result);
            map.put("oldName",oldName);
            map.put("newName",fileName);
            map.put("oldFilePath",path+oldName);
            map.put("newFilePath",newPath);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

