package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IUserRealInfoService;
import cn.itfield.wxcc.domain.UserRealInfo;
import cn.itfield.wxcc.query.UserRealInfoQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/userRealInfo")
public class UserRealInfoController {

    @Autowired
    public IUserRealInfoService userRealInfoService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody UserRealInfo userRealInfo){
        if(userRealInfo.getId()!=null){
            userRealInfoService.updateById(userRealInfo);
        }else{
            userRealInfoService.save(userRealInfo);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        userRealInfoService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(userRealInfoService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(userRealInfoService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody UserRealInfoQuery query){
        Page<UserRealInfo> page = new Page<UserRealInfo>(query.getPageNo(), query.getPageSize());
        page = userRealInfoService.page(page);
        return JsonResult.me().setData(new PageList<UserRealInfo>(page.getTotal(), page.getRecords()));
    }
}
