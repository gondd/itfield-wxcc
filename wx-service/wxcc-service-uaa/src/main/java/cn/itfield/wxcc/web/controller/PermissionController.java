package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.Permission;
import cn.itfield.wxcc.query.PermissionQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.IPermissionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    public IPermissionService permissionService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Permission permission){
        if(permission.getId()!=null){
            permissionService.updateById(permission);
        }else{
            permissionService.save(permission);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        permissionService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(permissionService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(permissionService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody PermissionQuery query){
        Page<Permission> page = new Page<Permission>(query.getPageNo(), query.getPageSize());
        page = permissionService.page(page);
        return JsonResult.me().setData(new PageList<Permission>(page.getTotal(), page.getRecords()));
    }
}
