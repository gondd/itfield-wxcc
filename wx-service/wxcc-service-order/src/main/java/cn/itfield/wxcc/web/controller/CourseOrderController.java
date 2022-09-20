package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.CourseOrder;
import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.domain.dto.CourseOrderDto;
import cn.itfield.wxcc.query.CourseOrderQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.ICourseOrderService;
import cn.itfield.wxcc.utils.UserContext;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/courseOrder")
public class CourseOrderController {

    @Autowired
    public ICourseOrderService courseOrderService;
    @Autowired
    private UserContext userContext;
    @PostMapping("/placeOrder")
    public JsonResult courseOrder(@RequestBody CourseOrderDto courseOrderDto,HttpServletRequest request){
        Login getuser = userContext.getuser(request.getHeader("U-TOKEN"));
        courseOrderDto.setUserId(getuser.getId());
        String s = courseOrderService.courseOrder(courseOrderDto);
        return JsonResult.me().setData(s);
    }
    @PostMapping("/killorder")
    public JsonResult killorder(@RequestBody CourseOrderDto courseOrderDto,HttpServletRequest request){
        Login getuser = userContext.getuser(request.getHeader("U-TOKEN"));
        courseOrderDto.setUserId(getuser.getId());
        String s = courseOrderService.killorder(courseOrderDto);
        return JsonResult.me().setData(s);
    }
    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody CourseOrder courseOrder){
        if(courseOrder.getId()!=null){
            courseOrderService.updateById(courseOrder);
        }else{
            courseOrderService.save(courseOrder);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        courseOrderService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(courseOrderService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(courseOrderService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody CourseOrderQuery query){
        Page<CourseOrder> page = new Page<CourseOrder>(query.getPageNo(), query.getPageSize());
        page = courseOrderService.page(page);
        return JsonResult.me().setData(new PageList<CourseOrder>(page.getTotal(), page.getRecords()));
    }
}
