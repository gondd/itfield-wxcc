package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.domain.MediaFile;
import cn.itfield.wxcc.query.MediaFileQuery;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.result.PageList;
import cn.itfield.wxcc.service.IMediaFileService;
import cn.itfield.wxcc.utils.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mediaFile")
public class MediaFileController {

    @Autowired
    public IMediaFileService mediaFileService;
    @Autowired
    private UserContext userContext;
    @GetMapping("/getForUser/{mediaId}")
    public JsonResult getForUser(@PathVariable("mediaId")Long mediaId, HttpServletRequest request) {
        String header = request.getHeader("U-TOKEN");
        Login getuser = userContext.getuser(header);
        MediaFile mediaFile=mediaFileService.getForUser(mediaId,getuser.getId());
        return JsonResult.me().setData(mediaFile);
    }
    @GetMapping("/getvoide/{courseId}")
    public List<MediaFile> getvoide(@PathVariable("courseId") Long courseId){
        List<MediaFile> MediaFiles = mediaFileService.list(new QueryWrapper<MediaFile>().eq("course_id", courseId));
        System.out.println(MediaFiles);
        return MediaFiles;
    }
    //===============================================================
    //文件注册，检查文件是否已经上传
    @PostMapping("/register")
    public JsonResult register(@RequestParam("fileMd5") String fileMd5,     // 文件唯一标识
                               @RequestParam("fileName") String fileName,   // 文件名
                               @RequestParam("fileSize") Long fileSize,     // 文件大小
                               @RequestParam("mimetype") String mimetype,   // mime类型
                               @RequestParam("fileExt") String fileExt) {   //文件扩展名

        log.info("文件上传-文件注册,fileName={},fileMd5={}",fileName,fileMd5);

        return mediaFileService.register(fileMd5,fileName,fileSize,mimetype,fileExt);
    }

    //校验文件块是否已经存在了
    @PostMapping("/checkchunk")
    public JsonResult checkchunk(
            // 文件唯一标识
            @RequestParam("fileMd5") String fileMd5,
            // 当前分块下标
            @RequestParam("chunk") Integer chunk,
            // 当前分块大小
            @RequestParam("chunkSize") Integer chunkSize){
        log.info("文件上传-检查文件块是否存在；{}",fileMd5);
        return mediaFileService.checkchunk(fileMd5,chunk,chunkSize);
    }

    //上传分块后的文件
    @PostMapping("/uploadchunk")
    public JsonResult uploadchunk(
            //分块后的文件
            @RequestParam("file") MultipartFile file,
            // 文件唯一标识
            @RequestParam("fileMd5") String fileMd5,
            // 第几块，分块的索引
            @RequestParam("chunk") Integer chunk){

        log.info("文件上传 fileName={},fileMd5={}",file.getOriginalFilename(),fileMd5);
        return mediaFileService.uploadchunk(file,fileMd5,chunk);
    }

    //分块都上传成功之后，合并分块
    @PostMapping("/mergechunks")
    public JsonResult mergechunks(
            // 课程章节ID
            @RequestParam("chapterId") Long chapterId,
            // 课程ID
            @RequestParam("courseId") Long courseId,
            // 课程序列号
            @RequestParam("videoNumber") Integer videoNumber,
            // 课程章节ID
            @RequestParam("name") String name,
            //章节名
            @RequestParam("chapterName") String chapterName,
            //课程名
            @RequestParam("courseName") String courseName,
            // 文件唯一标识
            @RequestParam("fileMd5") String fileMd5,
            // 源文件名
            @RequestParam("fileName") String fileName,
            // 文件总大小
            @RequestParam("fileSize") Long fileSize,
            // 文件的mimi类型
            @RequestParam("mimetype") String mimetype,
            // 文件扩展名
            @RequestParam("fileExt") String fileExt){

        log.info("合并文件 fileName={} ，fileMd5={} ",fileName,fileMd5);
        return mediaFileService.mergechunks(fileMd5,fileName,fileSize,mimetype,fileExt,chapterId,courseId,videoNumber,name,courseName,chapterName);
    }

    //===============================================================
    /**
    * 保存和修改公用的
    */
    @PostMapping("/save")
    public JsonResult saveOrUpdate(@RequestBody MediaFile mediaFile){
        if(mediaFile.getId()!=null){
            mediaFileService.updateById(mediaFile);
        }else{
            mediaFileService.save(mediaFile);
        }
        return JsonResult.me();
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        mediaFileService.removeById(id);
        return JsonResult.me();
    }

    /**
   * 获取对象
   */
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id")Long id){
        return JsonResult.me().setData(mediaFileService.getById(id));
    }


    /**
    * 查询所有对象
    */
    @GetMapping("/list")
    public JsonResult list(){
        return JsonResult.me().setData(mediaFileService.list(null));
    }


    /**
    * 带条件分页查询数据
    */
    @PostMapping("/pagelist")
    public JsonResult page(@RequestBody MediaFileQuery query){
        Page<MediaFile> page = new Page<MediaFile>(query.getPageNo(), query.getPageSize());
        page = mediaFileService.page(page);
        return JsonResult.me().setData(new PageList<MediaFile>(page.getTotal(), page.getRecords()));
    }
}
