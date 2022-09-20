package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IUserAddressService;
import cn.itfield.wxcc.domain.UserAddress;
import cn.itfield.wxcc.query.UserAddressQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/userAddress")
public class UserAddressController {

    @Autowired
    public IUserAddressService userAddressService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody UserAddress userAddress){
        if(userAddress.getId()!=null){
            userAddressService.updateById(userAddress);
        }else{
            userAddressService.save(userAddress);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        userAddressService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(userAddressService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(userAddressService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody UserAddressQuery query){
        Page<UserAddress> page = new Page<UserAddress>(query.getPageNo(), query.getPageSize());
        page = userAddressService.page(page);
        return JsonResult.me().setData(new PageList<UserAddress>(page.getTotal(), page.getRecords()));
    }
}
