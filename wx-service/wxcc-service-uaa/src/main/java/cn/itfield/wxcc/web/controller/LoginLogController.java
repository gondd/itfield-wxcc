package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.LoginLog;
import cn.itfield.wxcc.query.LoginLogQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loginLog")
public class LoginLogController {

    @Autowired
    public ILoginLogService loginLogService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody LoginLog loginLog){
        if(loginLog.getId()!=null){
            loginLogService.updateById(loginLog);
        }else{
            loginLogService.save(loginLog);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        loginLogService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(loginLogService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(loginLogService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody LoginLogQuery query){
        Page<LoginLog> page = new Page<LoginLog>(query.getPageNo(), query.getPageSize());
        page = loginLogService.page(page);
        return JsonResult.me().setData(new PageList<LoginLog>(page.getTotal(), page.getRecords()));
    }
}
