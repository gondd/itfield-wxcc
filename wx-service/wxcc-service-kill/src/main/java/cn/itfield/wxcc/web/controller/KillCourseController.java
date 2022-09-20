package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.KillCourse;
import cn.itfield.wxcc.domain.KillOrderDto;
import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.query.KillCourseQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.IKillCourseService;
import cn.itfield.wxcc.utils.UserContext;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/killCourse")
public class KillCourseController {

    @Autowired
    public IKillCourseService killCourseService;
    @Autowired
    private UserContext userContext;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody KillCourse killCourse){
        if(killCourse.getId()!=null){
            killCourseService.updateById(killCourse);
        }else{
            killCourseService.save(killCourse);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        killCourseService.removeById(id);
        return JsonResult.me();
    }
    @PostMapping("/kill")
    public JsonResult kill(@RequestBody KillOrderDto killOrderDto, HttpServletRequest request){
        Login getuser = userContext.getuser(request.getHeader("U-TOKEN"));
        killOrderDto.setUserid(getuser.getId());
        return JsonResult.me().setData(killCourseService.kill(killOrderDto));

    }    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){

        return JsonResult.me().setData(killCourseService.killcoursebyid(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){

        return JsonResult.me().setData(killCourseService.listtoRedis());
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody KillCourseQuery query){
        Page<KillCourse> page = new Page<KillCourse>(query.getPageNo(), query.getPageSize());
        page = killCourseService.page(page);
        return JsonResult.me().setData(new PageList<KillCourse>(page.getTotal(), page.getRecords()));
    }
}
