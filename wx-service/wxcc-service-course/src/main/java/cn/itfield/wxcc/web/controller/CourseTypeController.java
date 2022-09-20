package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.CourseType;
import cn.itfield.wxcc.domain.vo.CourseTypeCrumbsVo;
import cn.itfield.wxcc.query.CourseTypeQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.ICourseTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseType")
public class CourseTypeController {

    @Autowired
    public ICourseTypeService courseTypeService;
    @GetMapping("/getcourseid/{courseId}")
    public JsonResult getcourseid(@PathVariable("courseId") Long courseId){
        List<CourseType> coursetype = courseTypeService.list(new QueryWrapper<CourseType>().eq("pid", courseId));
        return JsonResult.me().setData(coursetype);
    }    @GetMapping("/loadCrumbs/{courseTypeId}")
    public JsonResult courseTypeId(@PathVariable("courseTypeId") Long courseTypeId){
        List<CourseTypeCrumbsVo> courseTypeCrumbsVos=courseTypeService.getcourseType(courseTypeId);
        return JsonResult.me().setData(courseTypeCrumbsVos);

    }

    @GetMapping("/treeData")
    public JsonResult treeData(){
        List<CourseType> courseTypes= courseTypeService.treeData();
        return JsonResult.me().setData(courseTypes);
    }
    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseType courseType){
        System.out.println(courseType);
        if(courseType.getId()!=null){
            courseTypeService.updateById(courseType);
        }else{
            courseTypeService.save(courseType);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseTypeService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseTypeService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseTypeService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseTypeQuery query){
        Page<CourseType> page = new Page<CourseType>(query.getPageNo(), query.getPageSize());
        page = courseTypeService.page(page);
        return JsonResult.me().setData(new PageList<CourseType>(page.getTotal(), page.getRecords()));
    }
}
