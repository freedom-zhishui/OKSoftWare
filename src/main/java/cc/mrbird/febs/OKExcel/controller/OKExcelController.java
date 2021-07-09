package cc.mrbird.febs.OKExcel.controller;

import cc.mrbird.febs.OKExcel.entity.*;
import cc.mrbird.febs.OKExcel.service.*;
import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.entity.Strings;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequestMapping("OKExcel")
@RequiredArgsConstructor
public class OKExcelController extends BaseController {

    private final OKExcelService okExcelService;
    private final OKConService OKConService;
    private final GmtxOrderService gmtxOrderService;
    private final GmtxExpressService gmtxExpressService;
    //更新gmtx_order_common
    private final GmtxOrderCommonService gmtxOrderCommonService;

    // 更新gmtx_order_log
    private  final GmtxOrderLogService gmtxOrderLogService;

    @GetMapping
    @RequiresPermissions("OKExcel:view")
    public FebsResponse getOKExcelList(QueryRequest request, OKExcel okExcel){
        IPage<OKExcel> okExcelIPage  =  okExcelService.findOKExcelList(request,okExcel);
        return new FebsResponse().success().data(getDataTable((okExcelIPage)));

    }

    @PostMapping
    @RequiresPermissions("OKExcel:add")
    @ControllerEndpoint(operation = "新增OK导入excel信息", exceptionMessage = "新增OK导入excel信息失败")
    public FebsResponse addJob(@Valid OKExcel okExcel) {
        JSONObject json_test = JSONObject.parseObject(okExcel.getExcelData());
        String  jso =json_test.get("result").toString();
        JSONObject json = JSONObject.parseObject(jso);
        String count = json.get("count").toString();
        String colnum= json.get("colnum").toString();
        String screen = json.get("screen").toString();
        String Duplicate = json.get("Duplicate").toString();
        // 需要更新订单号的集合
        List<OKContent> okContentList = JSONArray.parseArray(json.get("data").toString(), OKContent.class);
        // 更新订单号
        Map map  = updateMuHaiOrder(okContentList);
        String updateMsg = "gmtx_order更新数量=" + map.get("gmtxOrderCount").toString() + ", gmtx_order_common更新数量"
                + map.get("gmtxOrderCommonCount").toString() + ",gmtx_order_log插入数量= " + map.get("mgtxOrderLogCount").toString()
                + ",失效的更新操作（数据库中已有物流单号，故不作更新）数量=  " +  map.get("gmtxCount").toString();
        List<OKContent> okContents  = (List<OKContent>) map.get("okContentList");
        if(okContents.size()>0){
            String result = JSONArray.toJSONString(okContents);
            okExcel.setUpdateResult(result);
        }
        if(okExcel.getRepeatData().length()<=2) okExcel.setRepeatData("");
        okExcel.setUpdateNumber(updateMsg);
        okExcel.setUserName(getCurrentUser().getUsername());
        okExcel.setUploadTime(new Date());
        okExcelService.save(okExcel);
        return new FebsResponse().success();
    }



    @GetMapping("delete/{excelId}")
    @RequiresPermissions("OKExcel:delete")
    @ControllerEndpoint(operation = "删除定时导入记录", exceptionMessage = "删除导入记录失败")
    public FebsResponse deleteOkExcel(@NotBlank(message = "{required}") @PathVariable String excelId) {
        okExcelService.removeByIds(Arrays.asList(StringUtils.split(excelId, Strings.COMMA)));
        return new FebsResponse().success();
    }

/*    @PostMapping("update")
    @ControllerEndpoint(operation = "修改定时任务", exceptionMessage = "修改定时任务失败")
    public FebsResponse updateOkExcel(@Valid OKExcel okExcel) {
        okExcelService.update(okExcel);
        return new FebsResponse().success();
    }*/

    // 更新沐海物流信息
    private Map updateMuHaiOrder(List<OKContent> okContentList) {
        // gmtx_order 更新数量
        AtomicInteger gmtxOrderCount = new AtomicInteger();
        // gmtx_order_common 更新数量
        AtomicInteger gmtxOrderCommonCount = new AtomicInteger();
        // gmtx_order_log插入数量
        AtomicInteger mgtxOrderLogCount = new AtomicInteger();
        // 记录重复更新数
        AtomicInteger gmtxCount  = new AtomicInteger();

        List<OKContent>  okContentList1  = new ArrayList<>();
        // 错误的订单信息
        List<OKContent>  okContentList2  = new ArrayList<>();
        okContentList.forEach(okContent -> {
            //1.根据订单号查询出order_id, store_id
            Gmtx_Order gmtx_order = new Gmtx_Order();
            gmtx_order.setOrderSn(okContent.getOrderNumber());
            // 获取到订单的order_id、shore_id、order_sn
            //List<Gmtx_Order> orderList  = gmtxOrderService.findGmtxOrderList(gmtx_order);
            gmtx_order = gmtxOrderService.findGmtxOrder(gmtx_order);
            /*如果订单的shipping_code已存在，则不作更新操作（excel中存在一个订单号对应多个物流单号的情况
             和工作室仲文婷沟通后，在一个订单号对应多个不同的物流单号时，存一个物流单号即可） */
            if(gmtx_order != null){
                if(StringUtils.isNotBlank(gmtx_order.getShippingCode())){
                    // 如果物流单号不为空则记录数量
                    gmtxCount.addAndGet(1);
                }else{
                    /*2.根据物流公司查询出该物流公司编号 select id from gmtx_express where e_name = "顺丰快递";*/
                    Gmtx_Express gmtx_express = new Gmtx_Express();
                    String express = "";
                    if(StringUtils.isNotBlank(okContent.getExpress())){
                        if (okContent.getExpress().contains("-")) {
                            String[] data = okContent.getExpress().split("-");
                            express = data[0];
                        } else {
                            express = okContent.getExpress();
                        }
                        gmtx_express.setEName(express);
                        // 获取到完整的物流订单信息
                        gmtx_express = gmtxExpressService.findgmtxExpress(gmtx_express);

                        //3.更新gmtx_order表
                        //update gmtx_order set shipping_code = "SF1300414538167", order_state = 30, delay_time = unix_timestamp(now()) where order_id = 36098 and store_id = 16;
                        gmtx_order.setShippingCode(okContent.getCourierNumbers());
                        // 时间戳
                        gmtx_order.setDelayTime( (int) (System.currentTimeMillis() / 1000));
                        gmtxOrderCount.addAndGet(gmtxOrderService.updateGmtxOrder(okContent, gmtx_order));

                        //4.更新gmtx_order_common表
                        //update gmtx_order_common set deliver_explain = "发货备注", shipping_express_id = 29, shipping_time = unix_timestamp(now()) where order_id = 36098 and store_id = 16;
                        Gmtx_Order_Common gmtxOrderCommon = new Gmtx_Order_Common();
                        gmtxOrderCommon.setDeliverExplain("发货备注");
                        gmtxOrderCommon.setShippingExpressId(gmtx_express.getId());
                        gmtxOrderCommon.setOrderId(gmtx_order.getOrderId());
                        gmtxOrderCommon.setStoreId(gmtx_order.getStoreId());
                        gmtxOrderCommon.setShipping_time((int) (System.currentTimeMillis() / 1000));
                        gmtxOrderCommonCount.addAndGet(gmtxOrderCommonService.updateOrderCommon(gmtxOrderCommon));

                        //5.插入gmtx_order_log表
                        //insert into gmtx_order_log (order_id, log_role, log_user, log_msg, log_orderstate, log_time) values (36098, "商家", "系统", "发出了货物 ( 编辑了发货信息 )", "30", unix_timestamp(now()));
                        Gmtx_Order_Log orderLog = new Gmtx_Order_Log();
                        orderLog.setOrderId((int) gmtx_order.getOrderId());
                        orderLog.setLogMsg("发出了货物 ( 编辑了发货信息 )");
                        orderLog.setLogRole("商家");
                        orderLog.setLogUser("muhai");
                        orderLog.setLogOrderstate("30");
                        orderLog.setLogTime((int) (System.currentTimeMillis() / 1000));
                        gmtxOrderLogService.saveLog(orderLog);
                        mgtxOrderLogCount.addAndGet(1);

                        // 将执行成功的订单号、物流公司、顾客、产品名称放入到
                        okContentList1.add(okContent);
                    }else{
                        // 收集错误的订单信息  2021-07-09
                        okContentList2.add(okContent);
                    }
                }
            }
        });
        // 6 返回更新的数据
        Map map = new HashMap();
        map.put("gmtxOrderCount", gmtxOrderCount);
        map.put("gmtxCount", gmtxCount);
        map.put("gmtxOrderCommonCount", gmtxOrderCommonCount);
        map.put("mgtxOrderLogCount", mgtxOrderLogCount);
        map.put("okContentList", okContentList1);
        map.put("okContentList2", okContentList2);
        return map;
    }
}
