package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ISystemdictionaryService;
import cn.itfield.wxcc.domain.Systemdictionary;
import cn.itfield.wxcc.query.SystemdictionaryQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/systemdictionary")
public class SystemdictionaryController {

    @Autowired
    public ISystemdictionaryService systemdictionaryService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Systemdictionary systemdictionary){
        if(systemdictionary.getId()!=null){
            systemdictionaryService.updateById(systemdictionary);
        }else{
            systemdictionaryService.save(systemdictionary);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        systemdictionaryService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(systemdictionaryService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(systemdictionaryService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody SystemdictionaryQuery query){
        Page<Systemdictionary> page = new Page<Systemdictionary>(query.getPageNo(), query.getPageSize());
        page = systemdictionaryService.page(page);
        return JsonResult.me().setData(new PageList<Systemdictionary>(page.getTotal(), page.getRecords()));
    }
}
