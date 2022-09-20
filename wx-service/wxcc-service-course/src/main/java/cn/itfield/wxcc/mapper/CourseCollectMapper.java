package cn.itfield.wxcc.mapper;

import cn.itfield.wxcc.domain.CourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 商品收藏 Mapper 接口
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Component
public interface CourseCollectMapper extends BaseMapper<CourseCollect> {

    List<Long> selectbyuser(@Param("courseIds") List<Long> courseIds);
}
