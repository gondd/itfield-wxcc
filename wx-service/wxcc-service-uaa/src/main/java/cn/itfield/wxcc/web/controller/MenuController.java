package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.Menu;
import cn.itfield.wxcc.query.MenuQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.IMenuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    public IMenuService menuService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Menu menu){
        if(menu.getId()!=null){
            menuService.updateById(menu);
        }else{
            menuService.save(menu);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        menuService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(menuService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(menuService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody MenuQuery query){
        Page<Menu> page = new Page<Menu>(query.getPageNo(), query.getPageSize());
        page = menuService.page(page);
        return JsonResult.me().setData(new PageList<Menu>(page.getTotal(), page.getRecords()));
    }
}
