package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.CourseCart;
import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.query.CourseCartQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.ICourseCartService;
import cn.itfield.wxcc.utils.UserContext;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/courseCart")
public class CourseCartController {

    @Autowired
    public ICourseCartService courseCartService;
    @Autowired
    private UserContext userContext;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseCart courseCart){
        if(courseCart.getId()!=null){
            courseCartService.updateById(courseCart);
        }else{
            courseCartService.save(courseCart);
        }
        return JsonResult.me();
    }
    @GetMapping("/addshoppingcart/{courseId}")
    public JsonResult addshoppingcart(@PathVariable("courseId") Long courseId, HttpServletRequest request){
        String header = request.getHeader("U-TOKEN");
        Login getuser = userContext.getuser(header);
        courseCartService.addshoppingcart(getuser.getId(),courseId);
        return JsonResult.me();
    }
    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseCartService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseCartService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseCartService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseCartQuery query){
        Page<CourseCart> page = new Page<CourseCart>(query.getPageNo(), query.getPageSize());
        page = courseCartService.page(page);
        return JsonResult.me().setData(new PageList<CourseCart>(page.getTotal(), page.getRecords()));
    }
}
