package cn.itfield.wxcc.service;

import cn.itfield.wxcc.domain.CourseType;
import cn.itfield.wxcc.domain.vo.CourseTypeCrumbsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
public interface ICourseTypeService extends IService<CourseType> {

    List<CourseType> treeData();

    List<CourseTypeCrumbsVo> getcourseType(Long courseTypeId);
}
