package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-21
 */
@Data
@TableName("t_pay_flow")
public class PayFlow extends Model<PayFlow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 交易时间
     */
    private Date notifyTime=new Date();

    /**
     * 标题
     */
    private String subject;

    /**
     * 交易号，对应订单号
     */
    private String outTradeNo;

    /**
     * 金额
     */
    private BigDecimal totalAmount;

    /**
     * 交易状态
     */
    private String tradeStatus="1";

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 回传参数
     */
    private String passbackParams;

    private Boolean paySuccess;

    private String resultDesc;


}
