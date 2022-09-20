package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-09-03
 */
@TableName("t_media_file")
public class MediaFile extends Model<MediaFile> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件的ID
     */
    private String fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 源文件名
     */
    private String fileOriginalName;

    /**
     * 文件的云存储地址，处理过的m3u8格式
     */
    private String fileUrl;

    /**
     * 文件在本地磁盘的临时存储目录
     */
    private String filePath;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 1上传成功未处理 2处理成功 3处理失败 4无需处理
     */
    private Integer fileStatus;

    /**
     * 文件总大小
     */
    private Long fileSize;

    /**
     * 文件mime类型
     */
    private String mimeType;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 属于哪个章节
     */
    private Long chapterId;

    /**
     * 属于哪个课程
     */
    private Long courseId;

    /**
     * 视频的序号
     */
    private Integer number;

    /**
     * 视频名
     */
    private String name;

    private String courseName;

    private String chapterName;

    private Integer timeMinute;

    private Boolean free;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(Integer timeMinute) {
        this.timeMinute = timeMinute;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MediaFile{" +
        "id=" + id +
        ", fileId=" + fileId +
        ", fileName=" + fileName +
        ", fileOriginalName=" + fileOriginalName +
        ", fileUrl=" + fileUrl +
        ", filePath=" + filePath +
        ", fileType=" + fileType +
        ", fileStatus=" + fileStatus +
        ", fileSize=" + fileSize +
        ", mimeType=" + mimeType +
        ", uploadTime=" + uploadTime +
        ", chapterId=" + chapterId +
        ", courseId=" + courseId +
        ", number=" + number +
        ", name=" + name +
        ", courseName=" + courseName +
        ", chapterName=" + chapterName +
        ", timeMinute=" + timeMinute +
        ", free=" + free +
        "}";
    }
}
