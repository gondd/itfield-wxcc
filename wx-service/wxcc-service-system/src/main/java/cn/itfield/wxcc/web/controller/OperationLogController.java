package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IOperationLogService;
import cn.itfield.wxcc.domain.OperationLog;
import cn.itfield.wxcc.query.OperationLogQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/operationLog")
public class OperationLogController {

    @Autowired
    public IOperationLogService operationLogService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody OperationLog operationLog){
        if(operationLog.getId()!=null){
            operationLogService.updateById(operationLog);
        }else{
            operationLogService.save(operationLog);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        operationLogService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(operationLogService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(operationLogService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody OperationLogQuery query){
        Page<OperationLog> page = new Page<OperationLog>(query.getPageNo(), query.getPageSize());
        page = operationLogService.page(page);
        return JsonResult.me().setData(new PageList<OperationLog>(page.getTotal(), page.getRecords()));
    }
}
