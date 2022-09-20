package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ICourseSummaryService;
import cn.itfield.wxcc.domain.CourseSummary;
import cn.itfield.wxcc.query.CourseSummaryQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courseSummary")
public class CourseSummaryController {

    @Autowired
    public ICourseSummaryService courseSummaryService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseSummary courseSummary){
        if(courseSummary.getId()!=null){
            courseSummaryService.updateById(courseSummary);
        }else{
            courseSummaryService.save(courseSummary);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseSummaryService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseSummaryService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseSummaryService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseSummaryQuery query){
        Page<CourseSummary> page = new Page<CourseSummary>(query.getPageNo(), query.getPageSize());
        page = courseSummaryService.page(page);
        return JsonResult.me().setData(new PageList<CourseSummary>(page.getTotal(), page.getRecords()));
    }
}
