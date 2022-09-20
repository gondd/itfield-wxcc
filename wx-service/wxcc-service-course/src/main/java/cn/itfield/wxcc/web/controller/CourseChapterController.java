package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ICourseChapterService;
import cn.itfield.wxcc.domain.CourseChapter;
import cn.itfield.wxcc.query.CourseChapterQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courseChapter")
public class CourseChapterController {

    @Autowired
    public ICourseChapterService courseChapterService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseChapter courseChapter){
        if(courseChapter.getId()!=null){
            courseChapterService.updateById(courseChapter);
        }else{
            courseChapterService.save(courseChapter);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseChapterService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseChapterService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseChapterService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseChapterQuery query){
        Page<CourseChapter> page = new Page<CourseChapter>(query.getPageNo(), query.getPageSize());
        page = courseChapterService.page(page);
        return JsonResult.me().setData(new PageList<CourseChapter>(page.getTotal(), page.getRecords()));
    }
}
