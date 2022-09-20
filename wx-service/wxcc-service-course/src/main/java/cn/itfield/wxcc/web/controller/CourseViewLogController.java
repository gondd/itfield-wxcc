package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ICourseViewLogService;
import cn.itfield.wxcc.domain.CourseViewLog;
import cn.itfield.wxcc.query.CourseViewLogQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courseViewLog")
public class CourseViewLogController {

    @Autowired
    public ICourseViewLogService courseViewLogService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseViewLog courseViewLog){
        if(courseViewLog.getId()!=null){
            courseViewLogService.updateById(courseViewLog);
        }else{
            courseViewLogService.save(courseViewLog);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseViewLogService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseViewLogService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseViewLogService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseViewLogQuery query){
        Page<CourseViewLog> page = new Page<CourseViewLog>(query.getPageNo(), query.getPageSize());
        page = courseViewLogService.page(page);
        return JsonResult.me().setData(new PageList<CourseViewLog>(page.getTotal(), page.getRecords()));
    }
}
