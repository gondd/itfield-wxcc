package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.UserAccount;
import cn.itfield.wxcc.query.UserAccountQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.IUserAccountService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userAccount")
public class UserAccountController {

    @Autowired
    public IUserAccountService userAccountService;
    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody UserAccount userAccount){
        if(userAccount.getId()!=null){
            userAccountService.updateById(userAccount);
        }else{
            userAccountService.save(userAccount);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        userAccountService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(userAccountService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(userAccountService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody UserAccountQuery query){
        Page<UserAccount> page = new Page<UserAccount>(query.getPageNo(), query.getPageSize());
        page = userAccountService.page(page);
        return JsonResult.me().setData(new PageList<UserAccount>(page.getTotal(), page.getRecords()));
    }
}
