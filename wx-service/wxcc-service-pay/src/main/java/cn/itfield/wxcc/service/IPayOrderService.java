package cn.itfield.wxcc.service;

import cn.itfield.wxcc.domain.PayOrder;
import cn.itfield.wxcc.domain.dto.PayOrderDto;
import cn.itfield.wxcc.dto.AlipayDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-21
 */
public interface IPayOrderService extends IService<PayOrder> {

    String alipaly(AlipayDto alipayDto);

    void paySuccess(String trade_status, String out_trade_no, String trade_no);

    void savepayflow(PayOrderDto payOrderDto);
}
