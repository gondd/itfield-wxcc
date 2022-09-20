package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.Role;
import cn.itfield.wxcc.query.RoleQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.IRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    public IRoleService roleService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Role role){
        if(role.getId()!=null){
            roleService.updateById(role);
        }else{
            roleService.save(role);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        roleService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(roleService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(roleService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody RoleQuery query){
        Page<Role> page = new Page<Role>(query.getPageNo(), query.getPageSize());
        page = roleService.page(page);
        return JsonResult.me().setData(new PageList<Role>(page.getTotal(), page.getRecords()));
    }
}
