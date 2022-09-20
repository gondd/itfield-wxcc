package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.query.LoginQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.ILoginService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public ILoginService loginService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Login login){
        if(login.getId()!=null){
            loginService.updateById(login);
        }else{
            loginService.save(login);
        }
        return JsonResult.me().setData(login.getId());
    }
    @PostMapping("/common")
    public JsonResult saveOrUpdate(@RequestBody LoginDto loginDto){
        if(loginDto.getPassword().equals("1")){
            return new JsonResult().setMessage("成功");
        }
        System.out.println(111);
        try {
            return loginService.common(loginDto);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("登录失败");

        }
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        loginService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(loginService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(loginService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody LoginQuery query){
        Page<Login> page = new Page<Login>(query.getPageNo(), query.getPageSize());
        page = loginService.page(page);
        return JsonResult.me().setData(new PageList<Login>(page.getTotal(), page.getRecords()));
    }
}
