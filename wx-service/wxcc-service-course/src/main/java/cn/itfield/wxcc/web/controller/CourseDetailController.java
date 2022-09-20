package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ICourseDetailService;
import cn.itfield.wxcc.domain.CourseDetail;
import cn.itfield.wxcc.query.CourseDetailQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courseDetail")
public class CourseDetailController {

    @Autowired
    public ICourseDetailService courseDetailService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseDetail courseDetail){
        if(courseDetail.getId()!=null){
            courseDetailService.updateById(courseDetail);
        }else{
            courseDetailService.save(courseDetail);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseDetailService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseDetailService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseDetailService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseDetailQuery query){
        Page<CourseDetail> page = new Page<CourseDetail>(query.getPageNo(), query.getPageSize());
        page = courseDetailService.page(page);
        return JsonResult.me().setData(new PageList<CourseDetail>(page.getTotal(), page.getRecords()));
    }
}
