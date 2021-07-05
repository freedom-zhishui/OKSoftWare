package cc.mrbird.febs.OKExcel.controller;

import cc.mrbird.febs.OKExcel.entity.OKExcel;
import cc.mrbird.febs.OKExcel.service.OKExcelService;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IUserDataPermissionService;
import cc.mrbird.febs.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Controller("OKExcelView")
@RequestMapping(FebsConstant.VIEW_PREFIX + "OKExcel")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IUserService userService;
    private final IUserDataPermissionService userDataPermissionService;

    private final OKExcelService  okExcelService;

    @GetMapping("excel")
    public String excelList() {
        return FebsUtil.view("OKExcel/excelList");
    }

    @GetMapping("excel/detail/{id}")
    public String excelDetail(@NotBlank(message = "{required}") @PathVariable Long id, Model model) {
        OKExcel okExcel  = okExcelService.getById(id);
        model.addAttribute("okExcel",okExcel);
        return FebsUtil.view("OKExcel/excelDetail");
    }

    @GetMapping("excel/update/{id}")
    public String excelUpdate(@NotBlank(message = "{required}") @PathVariable Long id, Model model) {
        OKExcel okExcel  = okExcelService.getById(id);
        model.addAttribute("okExcel",okExcel);
        return FebsUtil.view("OKExcel/excelUpdate");
    }

   @GetMapping("excel/add")
    public String excelAdd() {
        return FebsUtil.view("OKExcel/excelAdd");
    }

}
