package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ICourseCollectService;
import cn.itfield.wxcc.domain.CourseCollect;
import cn.itfield.wxcc.query.CourseCollectQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courseCollect")
public class CourseCollectController {

    @Autowired
    public ICourseCollectService courseCollectService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseCollect courseCollect){
        if(courseCollect.getId()!=null){
            courseCollectService.updateById(courseCollect);
        }else{
            courseCollectService.save(courseCollect);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseCollectService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseCollectService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseCollectService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseCollectQuery query){
        Page<CourseCollect> page = new Page<CourseCollect>(query.getPageNo(), query.getPageSize());
        page = courseCollectService.page(page);
        return JsonResult.me().setData(new PageList<CourseCollect>(page.getTotal(), page.getRecords()));
    }
}
