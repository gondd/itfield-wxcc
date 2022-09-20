package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.domain.User;
import cn.itfield.wxcc.query.UserQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.IUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public IUserService userService;

    @PostMapping("/getuser")
    public List<User> getuser(@RequestBody List<Long> userids){
       List<User> users= userService.getuser(userids);
       return users;
    }
    @PostMapping("/register")
    public JsonResult register(@RequestBody LoginDto loginDto){
        userService.register(loginDto);
        return JsonResult.me();
    }
    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody User user){
        if(user.getId()!=null){
            userService.updateById(user);
        }else{
            userService.save(user);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        userService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(userService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(userService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody UserQuery query){
        Page<User> page = new Page<User>(query.getPageNo(), query.getPageSize());
        page = userService.page(page);
        return JsonResult.me().setData(new PageList<User>(page.getTotal(), page.getRecords()));
    }
}
