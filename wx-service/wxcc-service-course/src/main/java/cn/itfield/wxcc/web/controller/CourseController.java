package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.Course;
import cn.itfield.wxcc.domain.CourseCollect;
import cn.itfield.wxcc.domain.CourseDto;
import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.query.CourseQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.ICourseService;
import cn.itfield.wxcc.service.impl.CourseCollectServiceImpl;
import cn.itfield.wxcc.utils.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private UserContext userContext;

    @Autowired
    public ICourseService courseService;
    @Autowired
    private CourseCollectServiceImpl courseCollectService;


    @GetMapping("/shouc/{courseId}")
    public JsonResult shouc(@PathVariable("courseId" )Long courseId,HttpServletRequest request){
        Login getuser = userContext.getuser(request.getHeader("U-TOKEN"));
        Assert.isTrue(getuser!=null,"请登录！");
        Assert.isTrue(courseId!=null,"没有收藏课程");
        CourseCollect c = courseCollectService.getOne(new QueryWrapper<CourseCollect>().eq("user_id", getuser.getId()).eq("course_id", courseId));
        if(c==null){
            CourseCollect courseCollect = new CourseCollect();
            courseCollect.setCourseId(courseId);
            courseCollect.setUserId(getuser.getId());
            courseCollectService.save(courseCollect);
        }
        return c==null?JsonResult.me():JsonResult.error("已经存在添加成功了哟");

    }
    @PostMapping("/courseorder")
    public JsonResult courseorder(@RequestBody List<Long> courseIds){
        List<Course> courses=courseService.courseorder(courseIds);
        return JsonResult.me().setData(courses);
    }
    @GetMapping("/detaildata/{courseId}")
    public JsonResult detaildata(@PathVariable("courseId") Long courseId){
        Map<String,Object> maps=courseService.detaildata(courseId);
        return JsonResult.me().setData(maps);
    }
    /**
     * 加载视频
     * @param
     * @return
     */

    @PostMapping("/offLineCourse")
    public JsonResult offLineCourse(@RequestBody List<Long> courseids){
        courseService.offLineCourse(courseids);
        return JsonResult.me();
    }

    @GetMapping("/gettimetable/{courseId}")
    public JsonResult gettimetable(@PathVariable("courseId") Long courseId){
        Map<String,Object> map =courseService.gettimetable(courseId);
        return JsonResult.me().setData(map);
    }
    @PostMapping("/onLineCourse")
    public JsonResult onLineCourse(@RequestBody List<Long> courseIds){
        courseService.onLineCourse(courseIds);
        return JsonResult.me();
    }
    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseDto courseDto, HttpServletRequest res){
        System.out.println(courseDto.getCourse().getId());
        if(courseDto.getCourse().getId()!=null){
            courseService.updateById(courseDto);
        }else{
            String token = res.getHeader("U-TOKEN");
            Login getuser = userContext.getuser(token);
            courseDto.getCourse().setLoginId(getuser.getId());
            courseDto.getCourse().setLoginUserName(getuser.getUsername());
            courseService.save(courseDto);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/getcourse/{courseId}")
    public Course get(@PathVariable("courseId")Long courseId){

        return courseService.getById(courseId);
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseQuery query){
        Page<Course> page = new Page<Course>(query.getPageNo(), query.getPageSize());
        QueryWrapper<Course> name = new QueryWrapper<Course>().like(StringUtils.hasText(query.getKeyword()), "name", query.getKeyword());
        page = courseService.page(page,name);
        return JsonResult.me().setData(new PageList<Course>(page.getTotal(), page.getRecords()));
    }
}
