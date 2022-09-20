package cn.itfield.wxcc.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-19
 */
@TableName("t_course_order_item")
public class CourseOrderItem extends Model<CourseOrderItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 对应订单ID
     */
    private Long orderId;

    /**
     * 小计金额
     */
    private BigDecimal amount;

    /**
     * 课程价格
     */
    private BigDecimal price;

    /**
     * 课程的数量
     */
    private Integer count;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名字
     */
    private String courseName;

    /**
     * 封面
     */
    private String coursePic;

    private String orderNo;

    /**
     * 秒杀课程ID
     */
    private Long killCourseId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getKillCourseId() {
        return killCourseId;
    }

    public void setKillCourseId(Long killCourseId) {
        this.killCourseId = killCourseId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CourseOrderItem{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", amount=" + amount +
        ", price=" + price +
        ", count=" + count +
        ", courseId=" + courseId +
        ", courseName=" + courseName +
        ", coursePic=" + coursePic +
        ", orderNo=" + orderNo +
        ", killCourseId=" + killCourseId +
        "}";
    }
}
