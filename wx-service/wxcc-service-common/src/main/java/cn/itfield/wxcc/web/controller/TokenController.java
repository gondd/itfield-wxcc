package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.service.ITokenService;
import cn.itfield.wxcc.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private UserContext userContext;
    @GetMapping("/createToken/{courseId}")
    public JsonResult createToken(@PathVariable("courseId") Long courseId, HttpServletRequest request){
        Login getuser = userContext.getuser(request.getHeader("U-TOKEN"));
        return tokenService.createToken(courseId,getuser.getId());
    }
}
