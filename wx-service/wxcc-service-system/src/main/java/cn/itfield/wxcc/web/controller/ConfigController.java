package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IConfigService;
import cn.itfield.wxcc.domain.Config;
import cn.itfield.wxcc.query.ConfigQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    public IConfigService configService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Config config){
        if(config.getId()!=null){
            configService.updateById(config);
        }else{
            configService.save(config);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        configService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(configService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(configService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody ConfigQuery query){
        Page<Config> page = new Page<Config>(query.getPageNo(), query.getPageSize());
        page = configService.page(page);
        return JsonResult.me().setData(new PageList<Config>(page.getTotal(), page.getRecords()));
    }
}
