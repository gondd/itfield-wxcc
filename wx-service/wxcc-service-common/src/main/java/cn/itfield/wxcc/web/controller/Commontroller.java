package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verifycode")
public class Commontroller {
    @Autowired
    private ICommonService iCommonService;
    @GetMapping("/imageCode/{imageCodeKey}")
    public JsonResult verifycode(@PathVariable("imageCodeKey") String imageCodeKey){
        return iCommonService.verifycode(imageCodeKey);
    }
    @PostMapping("/sendSmsCode")
    public JsonResult sendSmsCode(@RequestBody LoginDto loginDto){
        System.out.println(loginDto);
        try {
            iCommonService.sendSmsCode(loginDto);
            return JsonResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }
}
