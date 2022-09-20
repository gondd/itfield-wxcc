package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IPayFlowService;
import cn.itfield.wxcc.domain.PayFlow;
import cn.itfield.wxcc.query.PayFlowQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payFlow")
public class PayFlowController {

    @Autowired
    public IPayFlowService payFlowService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody PayFlow payFlow){
        if(payFlow.getId()!=null){
            payFlowService.updateById(payFlow);
        }else{
            payFlowService.save(payFlow);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        payFlowService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(payFlowService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(payFlowService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody PayFlowQuery query){
        Page<PayFlow> page = new Page<PayFlow>(query.getPageNo(), query.getPageSize());
        page = payFlowService.page(page);
        return JsonResult.me().setData(new PageList<PayFlow>(page.getTotal(), page.getRecords()));
    }
}
