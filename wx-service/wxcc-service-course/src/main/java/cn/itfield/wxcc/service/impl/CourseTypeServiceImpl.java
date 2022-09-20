package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.CourseType;
import cn.itfield.wxcc.domain.vo.CourseTypeCrumbsVo;
import cn.itfield.wxcc.mapper.CourseTypeMapper;
import cn.itfield.wxcc.service.ICourseTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    public static final String TREEDATA_KEY ="treeDataKey11";
    @Cacheable(cacheNames= TREEDATA_KEY,key= "'All'")
    @Override
    public List<CourseType> treeData() {
        /*if(redisTemplate.hasKey(TREEDATA_KEY)){
            List<CourseType> o = (List<CourseType>)redisTemplate.opsForValue().get(TREEDATA_KEY);
            System.out.println("进的缓存");
            return o;
        }
        List<CourseType> courseTypes = CourseTypes2();
        System.out.println("查的数据库");
        redisTemplate.opsForValue().set(TREEDATA_KEY,courseTypes);*/
        return CourseTypes2();
    }

    @Override
    public List<CourseTypeCrumbsVo> getcourseType(Long courseTypeId) {
        List<CourseTypeCrumbsVo> courseTypeCrumbsVo = new ArrayList<>();
        CourseType courseType = baseMapper.selectById(courseTypeId);
        String path = courseType.getPath();
        String[] pathids = path.split("\\.");
        for (String pathid : pathids) {
            List<CourseType> courseTypes=null;
            CourseType courseType1 =null;
            if(Long.valueOf(pathid)==courseTypeId){
                courseType1=courseType;
            }else {
                courseType1 = baseMapper.selectById(pathid);
            }
            courseTypes = baseMapper.selectList(new QueryWrapper<CourseType>().eq("pid", courseType1.getPid()));
            courseTypeCrumbsVo.add(new CourseTypeCrumbsVo(courseType1,courseTypes));
        }
        return courseTypeCrumbsVo;
    }

    private List<CourseType> CourseTypes2() {
        List<CourseType> par = new ArrayList<>();
        List<CourseType> courseTypes = baseMapper.selectList(null);
        Map<Long, CourseType> map = new HashMap<>();
        for (CourseType courseType : courseTypes) {
            map.put(courseType.getId(),courseType);

        }
        for (CourseType courseType : courseTypes) {
            if(courseType.getPid()==0){
                par.add(courseType);
            }else {
                CourseType courseType1 = map.get(courseType.getPid());
                courseType1.getChildren().add(courseType);
            }
        }
        return par;
    }
    @CacheEvict(cacheNames= TREEDATA_KEY,key= "'All'")
    @Override
    @Transactional
    public boolean save(CourseType entity) {
        super.save(entity);
        saveandupdate(entity);
        return true;
    }

    @Override
    @CacheEvict(cacheNames= TREEDATA_KEY,key= "'All'")
    public boolean updateById(CourseType entity) {
        super.updateById(entity);
        saveandupdate(entity);
        return true;
    }

    private void saveandupdate(CourseType entity) {
        if(entity.getPid()==0||entity.getPid()==null){
            entity.setPath(entity.getId().toString());
            baseMapper.updateById(entity);
        }else {
            CourseType courseType = baseMapper.selectById(entity.getPid());
            entity.setPath(courseType.getPath()+"."+entity.getId());
            baseMapper.updateById(entity);
        }
    }
    @CacheEvict(cacheNames= TREEDATA_KEY,key= "'All'")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    private List<CourseType> CourseTypes1() {
        List<CourseType> par = new ArrayList<>();
        List<CourseType> courseTypes = baseMapper.selectList(null);
        for (CourseType courseType : courseTypes) {
            if(courseType.getPid()==0){
                par.add(courseType);
            }else {
                CourseType part=null;
                for (CourseType type : courseTypes) {
                    if(courseType.getPid().longValue()==type.getId().longValue()){
                        part=type;
                        break;
                    }
                }
                if(part!=null) {
                    part.getChildren().add(courseType);
                }
            }
        }
        return par;
    }

    private List<CourseType> CourseTypes(long pid) {
        List<CourseType> courseTypes = baseMapper.selectList(new QueryWrapper<CourseType>().eq("pid", pid));
        for (CourseType courseType : courseTypes) {
            courseType.setChildren(CourseTypes(courseType.getId()));
        }
        return courseTypes;
    }
}
