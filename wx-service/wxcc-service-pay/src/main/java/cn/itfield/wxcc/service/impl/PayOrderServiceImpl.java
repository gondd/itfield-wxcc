package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.config.AlipayProp;
import cn.itfield.wxcc.domain.PayFlow;
import cn.itfield.wxcc.domain.PayOrder;
import cn.itfield.wxcc.domain.dto.PayOrderDto;
import cn.itfield.wxcc.dto.AlipayDto;
import cn.itfield.wxcc.mapper.PayFlowMapper;
import cn.itfield.wxcc.mapper.PayOrderMapper;
import cn.itfield.wxcc.service.IPayOrderService;
import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-21
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {
    @Autowired
    private AlipayProp alipayProp;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PayFlowMapper payFlowMapper;
    @Override
    public String alipaly(AlipayDto alipayDto) {

        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            // 2. 发起API调用（网站页面扫码支付） pay方法底层封装了发送HTTP请求的代码
            //这里需要订单标题、订单编号、订单支付金额、回跳地址，其他配置参数可以写yml或者t_alipay_info表中
            //两个方案：1.可以直接通过订单编号查询支付单   2.可以从前端传递过来（checkPayOrder接口返回值）

            AlipayTradePagePayResponse response = Factory.Payment.Page().pay(alipayDto.getSubject(), alipayDto.getOrderNo(),
                    alipayDto.getAmount().toString(), alipayDto.getCallUrl());
            // 3. 处理响应或异常

            if (ResponseChecker.success(response)) {
                System.out.println("调用成功：" + response.getBody());
                return response.getBody();
            } else {
                System.err.println("调用失败，原因：" + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
        }
        return null;
    }

    @Override
    public void paySuccess(String trade_status, String out_trade_no, String trade_no) {
        PayOrder payOrder = baseMapper.selectOne(new QueryWrapper<PayOrder>().eq("order_no", out_trade_no));
        PayOrderDto payOrderDto = new PayOrderDto();
        payOrderDto.setOrderNo(out_trade_no);
        payOrderDto.setUserId(payOrder.getUserId());
        payOrderDto.setRelationId(payOrder.getRelationId());
        rocketMQTemplate.sendMessageInTransaction("public-message-zuming","topig_name_shh:pigic_namehh", MessageBuilder.withPayload(JSON.toJSONString(payOrderDto)).build(),payOrderDto);

    }

    @Override
    public void savepayflow(PayOrderDto payOrderDto) {
        PayOrder payOrder = baseMapper.selectOne(new QueryWrapper<PayOrder>().eq("order_no", payOrderDto.getOrderNo()));
        payOrder.setPayStatus(1);
        baseMapper.updateById(payOrder);
        PayFlow payFlow = new PayFlow();
        payFlow.setSubject(payOrder.getSubject());
        payFlow.setOutTradeNo(payOrder.getOrderNo());
        payFlow.setTotalAmount(payOrder.getAmount());
        payFlowMapper.insert(payFlow);
    }

    private Config getOptions() {
        Config config = new Config();
        config.protocol = alipayProp.getProtocol();
        config.gatewayHost = alipayProp.getGatewayHost();
        config.signType = alipayProp.getSignType();
        config.appId = alipayProp.getAppId();
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = alipayProp.getMerchantPrivateKey();
        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = alipayProp.getAlipayPublicKey();
        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = alipayProp.getNotifyUrl();
        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        config.encryptKey = alipayProp.getEncryptKey();
        return config;
    }
}
