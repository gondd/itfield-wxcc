package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IDepartmentService;
import cn.itfield.wxcc.domain.Department;
import cn.itfield.wxcc.query.DepartmentQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    public IDepartmentService departmentService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Department department){
        if(department.getId()!=null){
            departmentService.updateById(department);
        }else{
            departmentService.save(department);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        departmentService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(departmentService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(departmentService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody DepartmentQuery query){
        Page<Department> page = new Page<Department>(query.getPageNo(), query.getPageSize());
        page = departmentService.page(page);
        return JsonResult.me().setData(new PageList<Department>(page.getTotal(), page.getRecords()));
    }
}
