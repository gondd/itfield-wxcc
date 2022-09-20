package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.service.IAccountFlowService;
import cn.itfield.wxcc.domain.AccountFlow;
import cn.itfield.wxcc.query.AccountFlowQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/accountFlow")
public class AccountFlowController {

    @Autowired
    public IAccountFlowService accountFlowService;

    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody AccountFlow accountFlow){
        if(accountFlow.getId()!=null){
            accountFlowService.updateById(accountFlow);
        }else{
            accountFlowService.save(accountFlow);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        accountFlowService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(accountFlowService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(accountFlowService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody AccountFlowQuery query){
        Page<AccountFlow> page = new Page<AccountFlow>(query.getPageNo(), query.getPageSize());
        page = accountFlowService.page(page);
        return JsonResult.me().setData(new PageList<AccountFlow>(page.getTotal(), page.getRecords()));
    }
}
