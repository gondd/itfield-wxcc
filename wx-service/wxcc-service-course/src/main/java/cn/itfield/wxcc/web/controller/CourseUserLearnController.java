package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.CourseUserLearn;
import cn.itfield.wxcc.query.CourseUserLearnQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.ICourseUserLearnService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courseUserLearn")
public class CourseUserLearnController {

    @Autowired
    public ICourseUserLearnService courseUserLearnService;

    @GetMapping("/getkechen/{courseId}/{loginId}")
    public CourseUserLearn getkechen(@PathVariable("courseId") Long courseId,@PathVariable("loginId")Long loginId){
       return courseUserLearnService.getOne(new QueryWrapper<CourseUserLearn>().eq("course_id", courseId).eq("login_id", loginId));
    }
    /**
    * 保存和修改公用的
    */

    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseUserLearn courseUserLearn){
        if(courseUserLearn.getId()!=null){
            courseUserLearnService.updateById(courseUserLearn);
        }else{
            courseUserLearnService.save(courseUserLearn);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseUserLearnService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseUserLearnService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseUserLearnService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseUserLearnQuery query){
        Page<CourseUserLearn> page = new Page<CourseUserLearn>(query.getPageNo(), query.getPageSize());
        page = courseUserLearnService.page(page);
        return JsonResult.me().setData(new PageList<CourseUserLearn>(page.getTotal(), page.getRecords()));
    }
}
