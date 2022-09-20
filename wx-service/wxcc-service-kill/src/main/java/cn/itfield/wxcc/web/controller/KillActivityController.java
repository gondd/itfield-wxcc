package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IKillActivityService;
import cn.itfield.wxcc.domain.KillActivity;
import cn.itfield.wxcc.query.KillActivityQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/killActivity")
public class KillActivityController {

    @Autowired
    public IKillActivityService killActivityService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody KillActivity killActivity){
        if(killActivity.getId()!=null){
            killActivityService.updateById(killActivity);
        }else{
            killActivityService.save(killActivity);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        killActivityService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(killActivityService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(killActivityService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody KillActivityQuery query){
        Page<KillActivity> page = new Page<KillActivity>(query.getPageNo(), query.getPageSize());
        page = killActivityService.page(page);
        return JsonResult.me().setData(new PageList<KillActivity>(page.getTotal(), page.getRecords()));
    }
}
