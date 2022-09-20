package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IMessageStationService;
import cn.itfield.wxcc.domain.MessageStation;
import cn.itfield.wxcc.query.MessageStationQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/messageStation")
public class MessageStationController {

    @Autowired
    public IMessageStationService messageStationService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody MessageStation messageStation){
        if(messageStation.getId()!=null){
            messageStationService.updateById(messageStation);
        }else{
            messageStationService.save(messageStation);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        messageStationService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(messageStationService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(messageStationService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody MessageStationQuery query){
        Page<MessageStation> page = new Page<MessageStation>(query.getPageNo(), query.getPageSize());
        page = messageStationService.page(page);
        return JsonResult.me().setData(new PageList<MessageStation>(page.getTotal(), page.getRecords()));
    }
}
