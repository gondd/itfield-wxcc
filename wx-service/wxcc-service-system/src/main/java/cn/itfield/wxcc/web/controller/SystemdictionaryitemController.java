package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ISystemdictionaryitemService;
import cn.itfield.wxcc.domain.Systemdictionaryitem;
import cn.itfield.wxcc.query.SystemdictionaryitemQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/systemdictionaryitem")
public class SystemdictionaryitemController {

    @Autowired
    public ISystemdictionaryitemService systemdictionaryitemService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Systemdictionaryitem systemdictionaryitem){
        if(systemdictionaryitem.getId()!=null){
            systemdictionaryitemService.updateById(systemdictionaryitem);
        }else{
            systemdictionaryitemService.save(systemdictionaryitem);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        systemdictionaryitemService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(systemdictionaryitemService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(systemdictionaryitemService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody SystemdictionaryitemQuery query){
        Page<Systemdictionaryitem> page = new Page<Systemdictionaryitem>(query.getPageNo(), query.getPageSize());
        page = systemdictionaryitemService.page(page);
        return JsonResult.me().setData(new PageList<Systemdictionaryitem>(page.getTotal(), page.getRecords()));
    }
}
