package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ICourseResourceService;
import cn.itfield.wxcc.domain.CourseResource;
import cn.itfield.wxcc.query.CourseResourceQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courseResource")
public class CourseResourceController {

    @Autowired
    public ICourseResourceService courseResourceService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseResource courseResource){
        if(courseResource.getId()!=null){
            courseResourceService.updateById(courseResource);
        }else{
            courseResourceService.save(courseResource);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseResourceService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseResourceService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseResourceService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseResourceQuery query){
        Page<CourseResource> page = new Page<CourseResource>(query.getPageNo(), query.getPageSize());
        page = courseResourceService.page(page);
        return JsonResult.me().setData(new PageList<CourseResource>(page.getTotal(), page.getRecords()));
    }
}
