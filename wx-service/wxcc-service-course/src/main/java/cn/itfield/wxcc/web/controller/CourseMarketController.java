package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.ICourseMarketService;
import cn.itfield.wxcc.domain.CourseMarket;
import cn.itfield.wxcc.query.CourseMarketQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courseMarket")
public class CourseMarketController {

    @Autowired
    public ICourseMarketService courseMarketService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseMarket courseMarket){
        if(courseMarket.getId()!=null){
            courseMarketService.updateById(courseMarket);
        }else{
            courseMarketService.save(courseMarket);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseMarketService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseMarketService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseMarketService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseMarketQuery query){
        Page<CourseMarket> page = new Page<CourseMarket>(query.getPageNo(), query.getPageSize());
        page = courseMarketService.page(page);
        return JsonResult.me().setData(new PageList<CourseMarket>(page.getTotal(), page.getRecords()));
    }
}
