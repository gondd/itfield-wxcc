package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IMessageEmailService;
import cn.itfield.wxcc.domain.MessageEmail;
import cn.itfield.wxcc.query.MessageEmailQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/messageEmail")
public class MessageEmailController {

    @Autowired
    public IMessageEmailService messageEmailService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody MessageEmail messageEmail){
        if(messageEmail.getId()!=null){
            messageEmailService.updateById(messageEmail);
        }else{
            messageEmailService.save(messageEmail);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        messageEmailService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(messageEmailService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(messageEmailService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody MessageEmailQuery query){
        Page<MessageEmail> page = new Page<MessageEmail>(query.getPageNo(), query.getPageSize());
        page = messageEmailService.page(page);
        return JsonResult.me().setData(new PageList<MessageEmail>(page.getTotal(), page.getRecords()));
    }
}
