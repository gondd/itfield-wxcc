package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ICourseOrderItemService;
import cn.itfield.wxcc.domain.CourseOrderItem;
import cn.itfield.wxcc.query.CourseOrderItemQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courseOrderItem")
public class CourseOrderItemController {

    @Autowired
    public ICourseOrderItemService courseOrderItemService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseOrderItem courseOrderItem){
        if(courseOrderItem.getId()!=null){
            courseOrderItemService.updateById(courseOrderItem);
        }else{
            courseOrderItemService.save(courseOrderItem);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseOrderItemService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseOrderItemService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseOrderItemService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseOrderItemQuery query){
        Page<CourseOrderItem> page = new Page<CourseOrderItem>(query.getPageNo(), query.getPageSize());
        page = courseOrderItemService.page(page);
        return JsonResult.me().setData(new PageList<CourseOrderItem>(page.getTotal(), page.getRecords()));
    }
}
