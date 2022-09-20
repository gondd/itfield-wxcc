package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IUserBaseInfoService;
import cn.itfield.wxcc.domain.UserBaseInfo;
import cn.itfield.wxcc.query.UserBaseInfoQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/userBaseInfo")
public class UserBaseInfoController {

    @Autowired
    public IUserBaseInfoService userBaseInfoService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody UserBaseInfo userBaseInfo){
        if(userBaseInfo.getId()!=null){
            userBaseInfoService.updateById(userBaseInfo);
        }else{
            userBaseInfoService.save(userBaseInfo);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        userBaseInfoService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(userBaseInfoService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(userBaseInfoService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody UserBaseInfoQuery query){
        Page<UserBaseInfo> page = new Page<UserBaseInfo>(query.getPageNo(), query.getPageSize());
        page = userBaseInfoService.page(page);
        return JsonResult.me().setData(new PageList<UserBaseInfo>(page.getTotal(), page.getRecords()));
    }
}
