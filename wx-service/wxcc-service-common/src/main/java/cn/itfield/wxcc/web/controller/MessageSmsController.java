package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IMessageSmsService;
import cn.itfield.wxcc.domain.MessageSms;
import cn.itfield.wxcc.query.MessageSmsQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/messageSms")
public class MessageSmsController {

    @Autowired
    public IMessageSmsService messageSmsService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody MessageSms messageSms){
        if(messageSms.getId()!=null){
            messageSmsService.updateById(messageSms);
        }else{
            messageSmsService.save(messageSms);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        messageSmsService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(messageSmsService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(messageSmsService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody MessageSmsQuery query){
        Page<MessageSms> page = new Page<MessageSms>(query.getPageNo(), query.getPageSize());
        page = messageSmsService.page(page);
        return JsonResult.me().setData(new PageList<MessageSms>(page.getTotal(), page.getRecords()));
    }
}
