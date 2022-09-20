package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.Teacher;
import cn.itfield.wxcc.query.TeacherQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.ITeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    public ITeacherService teacherService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody Teacher teacher){
        System.out.println(teacher);
        if(teacher.getId()!=null){
            teacherService.updateById(teacher);
        }else{
            teacherService.save(teacher);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        teacherService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(teacherService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(teacherService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody TeacherQuery query){
        Page<Teacher> page = new Page<Teacher>(query.getPageNo(), query.getPageSize());
        page = teacherService.page(page);
        return JsonResult.me().setData(new PageList<Teacher>(page.getTotal(), page.getRecords()));
    }
}
