package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IUserGrowLogService;
import cn.itfield.wxcc.domain.UserGrowLog;
import cn.itfield.wxcc.query.UserGrowLogQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/userGrowLog")
public class UserGrowLogController {

    @Autowired
    public IUserGrowLogService userGrowLogService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody UserGrowLog userGrowLog){
        if(userGrowLog.getId()!=null){
            userGrowLogService.updateById(userGrowLog);
        }else{
            userGrowLogService.save(userGrowLog);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        userGrowLogService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(userGrowLogService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(userGrowLogService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody UserGrowLogQuery query){
        Page<UserGrowLog> page = new Page<UserGrowLog>(query.getPageNo(), query.getPageSize());
        page = userGrowLogService.page(page);
        return JsonResult.me().setData(new PageList<UserGrowLog>(page.getTotal(), page.getRecords()));
    }
}
