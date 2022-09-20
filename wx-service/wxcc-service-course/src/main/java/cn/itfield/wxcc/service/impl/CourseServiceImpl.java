package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.client.CourseClient;
import cn.itfield.wxcc.client.MediaFileClient;
import cn.itfield.wxcc.client.UserClient;
import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.*;
import cn.itfield.wxcc.domain.doc.CourceDoc;
import cn.itfield.wxcc.domain.dto.MessageEmails;
import cn.itfield.wxcc.domain.dto.MessageSmss;
import cn.itfield.wxcc.mapper.*;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.service.ICourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseDetailMapper courseDetailMapper;

    @Autowired
    private CourseMarketMapper courseMarketMapper;
    @Autowired
    private CourseClient courseClient;
    @Autowired
    private CourseResourceMapper courseResourceMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private CourseTypeMapper courseTypeMapper;
    @Autowired
    private CourseCollectMapper courseCollectMapper;
    @Autowired
    private CourseSummaryMapper courseSummaryMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private CourseChapterMapper courseChapterMapper;
    @Autowired
    private MediaFileClient mediaFileClient;
    @Transactional
    @Override
    public void save(CourseDto courseDto) {
        System.out.println(courseDto);
        Course course = courseDto.getCourse();
        course.setStatus(0);
        List<Long> teacharIds = courseDto.getTeacharIds();
        if(teacharIds!=null){
            List<Teacher> teachers = teacherMapper.selectBatchIds(teacharIds);

            System.out.println(teachers);
            List<String> teacherNames = teachers.stream()
                    .map(teacher -> teacher.getName())
                    .collect(Collectors.toCollection(ArrayList::new));
            System.out.println(teacherNames);
            course.setTeacherNames(String.join(",",teacherNames));
            System.out.println(111);
        }
        baseMapper.insert(course);
        CourseDetail courseDetail = courseDto.getCourseDetail();
        courseDetail.setCourseId(course.getId());
        courseDetailMapper.insert(courseDetail);

        CourseMarket courseMarket = courseDto.getCourseMarket();
        courseMarket.setCourseId(course.getId());
        courseMarketMapper.insert(courseMarket);

        CourseResource courseResource = courseDto.getCourseResource();
        courseResource.setCourseId(course.getId());
        courseResourceMapper.insert(courseResource);

        baseMapper.insetteacher(courseDto.getTeacharIds(), course.getId());

    }
    @Transactional
    @Override
    public void updateById(CourseDto courseDto) {
        Course course = courseDto.getCourse();
       /* Course course1 = baseMapper.selectById(course.getId());
        course1.setTeacherNames(null);
        baseMapper.updateById(course1);*/
        if(courseDto.getTeacharIds().isEmpty()){
            /*List<Long> teacharIds = courseDto.getTeacharIds();
            List<Course> teachers = baseMapper.selectBatchIds(teacharIds);
            List<String> teacherNames = teachers.stream()
                    .map(teacher -> teacher.getName())
                    .collect(Collectors.toCollection(ArrayList::new));
            course.setTeacherNames(String.join(",",teacherNames));*/
        }
        baseMapper.updateById(course);
        CourseDetail detail = courseDetailMapper.selectOne(new QueryWrapper<CourseDetail>().eq("course_id", course.getId()));
        CourseDetail courseDetail = courseDto.getCourseDetail();
        courseDetail.setId(detail.getId());
        courseDetailMapper.updateById(courseDetail);

        CourseMarket course_id = courseMarketMapper.selectOne(new QueryWrapper<CourseMarket>().eq("course_id", course.getId()));
        CourseMarket courseMarket = courseDto.getCourseMarket();
        courseMarket.setId(course_id.getId());
        courseMarketMapper.updateById(courseMarket);

        CourseResource resource = courseResourceMapper.selectOne(new QueryWrapper<CourseResource>().eq("course_id", course.getId()));
        CourseResource courseResource = courseDto.getCourseResource();
        courseResource.setId(resource.getId());
        courseResourceMapper.updateById(courseResource);

        if(courseDto.getTeacharIds().isEmpty()) {
            baseMapper.deletebycourseID(course.getId());
            baseMapper.insetteacher(courseDto.getTeacharIds(), course.getId());
        }

    }

    @Override
    public Map<String, Object> gettimetable(Long courseId) {
        CourseDetail detail = courseDetailMapper.selectOne(new QueryWrapper<CourseDetail>().eq("course_id", courseId));

        CourseMarket market = courseMarketMapper.selectOne(new QueryWrapper<CourseMarket>().eq("course_id", courseId));

        CourseResource resource = courseResourceMapper.selectOne(new QueryWrapper<CourseResource>().eq("course_id", courseId));

        List<Long> teacharIds=baseMapper.selectbyTeacherid(courseId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("detail",detail);
        map.put("market",market);
        map.put("resource",resource);
        map.put("teacharIds",teacharIds);
        return map;
    }

    @Override
    public void onLineCourse(List<Long> courseIds) {
        Assert.isTrue(!courseIds.isEmpty(),"参数不能为空");
        List<Course> courses = baseMapper.selectBatchIds(courseIds);
        List<Course> courses1 = new ArrayList<>();
        for (Course cours : courses) {
            if(cours.getStatus()==0){
                courses1.add(cours);
            }
        }
        Assert.isTrue(!courses1.isEmpty(),"里面有已经上架的课程");
        baseMapper.updatacourses(1L,courseIds);
        CourceDoc courceDoc = new CourceDoc();

        List<CourceDoc> collect = courses.stream().map(course -> coursetocoursedoc(course)).collect(Collectors.toList());
        System.out.println(collect);
        JsonResult coursesave = courseClient.coursesave(collect);
        Assert.isTrue(coursesave.getSuccess(),coursesave.getMessage());
        String msg = String.format(ConsTant.MESSAGE_TEMPLATE, courses.get(0).getId(), courses.get(0).getName());
        //发送短信消息
        List <Long> userids=courseCollectMapper.selectbyuser(courseIds);
        System.out.println(userids);
        List<MessageSmss> smsDtos=null;
        List<MessageEmails> emails=null;
        if(userids!=null){
            List<User> getuser = userClient.getuser(userids);
            System.out.println(getuser);
            smsDtos = getuser.stream().map(user -> usertosms(user)).collect(Collectors.toList());
            emails = getuser.stream().map(user -> usertoemail(user)).collect(Collectors.toList());

        }
        /*List<MessageSmss> smsDtos = Arrays.asList(
                new MessageSmss(null, 1l, "18996157300"),
                new MessageSmss(null, 2l, "18996157301")
        );*/
      /*  if(smsDtos!=null){
            MessageSmsDto messageSmsDto = new MessageSmsDto("课程发布",msg,smsDtos);
            rocketMQTemplate.sendOneWay(ConsTant.NEWS_NAME_PUSH+":"+ConsTant.NEWS_NAME_SMS, MessageBuilder.withPayload(JSON.toJSONString(messageSmsDto)).build());
        }

        //发送邮件
        *//*List<MessageEmails> emails = Arrays.asList(
                new MessageEmails("1@qq.com", null, 1l),
                new MessageEmails("1@qq.com", null, 2l)
        );*//*
        if(emails!=null) {
            MessageEmailDto messageEmailDto = new MessageEmailDto("课程发布", msg, emails);
            rocketMQTemplate.sendOneWay(ConsTant.NEWS_NAME_PUSH + ":" + ConsTant.NEWS_NAME_email, MessageBuilder.withPayload(JSON.toJSONString(messageEmailDto)).build());
        }
        *//*List<Long> ids = Arrays.asList(
                1l,2l
        );*//*
        if(userids!=null) {
            MessageStationDto messageStationDto = new MessageStationDto("课程发布", msg, "系统消息", 1, userids);
            rocketMQTemplate.sendOneWay(ConsTant.NEWS_NAME_PUSH + ":" + ConsTant.NEWS_NAME_state, MessageBuilder.withPayload(JSON.toJSONString(messageStationDto)).build());
        }*/
    }



    private MessageEmails usertoemail(User user) {
        MessageEmails messageEmails = new MessageEmails();
        messageEmails.setEmail(user.getEmail());
        messageEmails.setUserId(user.getId());
        messageEmails.setCopyto(null);
        return messageEmails;
    }

    private MessageSmss usertosms(User user) {
        MessageSmss messageSmss = new MessageSmss();
        messageSmss.setPhone(user.getPhone());
        messageSmss.setUserId(user.getId());
        return messageSmss;
    }


    @Override
    public void offLineCourse(List<Long> courseids) {
        Assert.isTrue(!StringUtils.isEmpty(courseids),"参数为空");
        List<Course> courses = baseMapper.selectBatchIds(courseids);
        List<Course> courses1 = new ArrayList<>();
        for (Course cours : courses) {
            if(cours.getStatus()==1){
                courses1.add(cours);
            }
        }
        Assert.isTrue(courses1!=null,"里面包含已经下架的数据");
        baseMapper.updatacourses(0l,courseids);
        List<CourceDoc> collect = courses.stream().map(course -> coursetocoursedoc(course)).collect(Collectors.toList());
        JsonResult deletebatch = courseClient.deletebatch(collect);
        Assert.isTrue(deletebatch.getSuccess(),deletebatch.getMessage());


    }

    @Override
    public Map<String, Object> detaildata(Long courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseDetail detail = courseDetailMapper.selectOne(new QueryWrapper<CourseDetail>().eq("course_id", courseId));
        CourseMarket market = courseMarketMapper.selectOne(new QueryWrapper<CourseMarket>().eq("course_id", courseId));
        CourseResource resource = courseResourceMapper.selectOne(new QueryWrapper<CourseResource>().eq("course_id", courseId));
        CourseSummary courseSummary = courseSummaryMapper.selectOne(new QueryWrapper<CourseSummary>().eq("course_id", courseId));
        List<Teacher> teachers=baseMapper.selectbycourseId(courseId);
        List<CourseChapter> courseChapters = courseChapterMapper.selectList(new QueryWrapper<CourseChapter>().eq("course_id", courseId));
        HashMap<Long, CourseChapter> map1 = new HashMap<>();
        courseChapters.stream().forEach(courseChapter -> map1.put(courseChapter.getId(),courseChapter));

        List<MediaFile> mediaFiles=mediaFileClient.getvoide(courseId);
        mediaFiles.forEach(System.out::println);
        Assert.isTrue(mediaFiles!=null,"当前课程没有视频");
        CourseChapter courseChapter = null;
        /*for (MediaFile mediaFile : mediaFiles) {
                courseChapter = map1.get(mediaFile.getChapterId());
                courseChapter.getMediafiles().add(mediaFile);
        }*/
        for (MediaFile mediaFile : mediaFiles) {
            courseChapter = map1.get(mediaFile.getChapterId());
            courseChapter.getMediaFiles().add(mediaFile);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("course",course);
        map.put("courseDetail",detail);
        map.put("courseMarket",market);
        map.put("resource",resource);
        map.put("teachers",teachers);
        map.put("courseSummary",courseSummary);
        map.put("courseChapters",courseChapters);
        System.out.println(map);
        return map;
    }

    @Override
    public List<Course> courseorder(List<Long> courseIds) {
        Assert.isTrue(!courseIds.isEmpty(),"数据错误");
        List<Course> courses = baseMapper.selectBatchIds(courseIds);
        for (Course cours : courses) {
            CourseMarket courseMarket = courseMarketMapper.selectOne(new QueryWrapper<CourseMarket>().eq("course_id", cours.getId()));
            cours.setPrice(courseMarket.getPrice());
            cours.setPriceOld(courseMarket.getPriceOld());

        }
        return courses;



    }

    private CourceDoc coursetocoursedoc(Course course) {
        CourceDoc courceDoc = new CourceDoc();
        BeanUtils.copyProperties(course,courceDoc);
        CourseType courseType = courseTypeMapper.selectById(course.getCourseTypeId());
        courceDoc.setCourseTypeName(courseType.getName());
        courceDoc.setEndTime(course.getEndTime().getTime());
        courceDoc.setOnlineTime(course.getOnlineTime().getTime());
        courceDoc.setStartTime(course.getStartTime().getTime());
        CourseMarket courseMarket = courseMarketMapper.selectOne(new QueryWrapper<CourseMarket>().eq("course_id", course.getId()));
        courceDoc.setCharge(courseMarket.getCharge());
        courceDoc.setPrice(courseMarket.getPrice());
        courceDoc.setPriceOld(courseMarket.getPriceOld());
        CourseSummary courseSummary = courseSummaryMapper.selectOne(new QueryWrapper<CourseSummary>().eq("course_id", course.getId()));
        courceDoc.setSaleCount(courseSummary.getSaleCount());
        courceDoc.setViewCount(courseSummary.getViewCount());
        courceDoc.setCommentCount(courseSummary.getCommentCount());
        return courceDoc;
    }
}
