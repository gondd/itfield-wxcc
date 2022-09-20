package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.config.AlipayProp;
import cn.itfield.wxcc.domain.PayOrder;
import cn.itfield.wxcc.dto.AlipayDto;
import cn.itfield.wxcc.query.PayOrderQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.IPayOrderService;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/payOrder")
public class PayOrderController {

    @Autowired
    public IPayOrderService payOrderService;
    @Autowired
    private AlipayProp alipayProp;

    @PostMapping("/hui")
    public String hui(HttpServletRequest request){
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            HashMap<String,String> params = new HashMap<>();
            //准备二次验签参数
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                params.put(entry.getKey(), entry.getValue() != null || entry.getValue().length > 0 ? entry.getValue()[0] : null);
            }
            //二次验签提交
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayProp.getAlipayPublicKey(),
                    "utf-8", alipayProp.getSignType());
            log.info("正在执行二次验签.......");
            //如果为true执行业务
            if(signVerified){
                //交易状态
                String trade_status = request.getParameter("trade_status");
                //商家订单编号
                String out_trade_no = request.getParameter("out_trade_no");
                //支付宝交易号
                String trade_no = request.getParameter("trade_no");
                if("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                    payOrderService.paySuccess(trade_status,out_trade_no,trade_no);
                    return "success";
                }
            }else{
                // TODO 验签失败则记录异常日志，并在response中返回failure.
                log.info("二次验签失败！");

            }
        } catch (Exception e) {
            // TODO 验签失败则记录异常日志，并在response中返回failure.

        }
        return "failure";
    }
    /**
     * 查询订单有没有创建
     * @param orderNo
     * @return
     */
    @GetMapping("/checkPayOrder/{orderNo}")
    public JsonResult checkPayOrder(@PathVariable("orderNo") Long orderNo){
        PayOrder payOrder = payOrderService.getOne(new QueryWrapper<PayOrder>().eq("order_no", orderNo));
        return payOrder!=null?JsonResult.me().setData(payOrder):JsonResult.error("订单还未创建");

    }
    @PostMapping("/alipaly")
    public JsonResult alipaly(@RequestBody AlipayDto alipayDto){
        System.out.println(alipayDto);
        String htmls =payOrderService.alipaly(alipayDto);
        return JsonResult.me().setData(htmls);
    }
    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody PayOrder payOrder){
        if(payOrder.getId()!=null){
            payOrderService.updateById(payOrder);
        }else{
            payOrderService.save(payOrder);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        payOrderService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(payOrderService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(payOrderService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody PayOrderQuery query){
        Page<PayOrder> page = new Page<PayOrder>(query.getPageNo(), query.getPageSize());
        page = payOrderService.page(page);
        return JsonResult.me().setData(new PageList<PayOrder>(page.getTotal(), page.getRecords()));
    }
}
