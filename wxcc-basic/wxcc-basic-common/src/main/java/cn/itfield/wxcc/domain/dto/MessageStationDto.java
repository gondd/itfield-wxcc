package cn.itfield.wxcc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageStationDto {
    private String title;
    private String content;

    /**
     * 类型
     */
    private String type;
    private Date sendTime=new Date();
    private Integer isread;
    List<Long> userids;
    private Long userid;

    public MessageStationDto(String title, String content, String type, Integer isread, List<Long> userids) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.isread = isread;
        this.userids = userids;
    }

    public MessageStationDto(String title, String content, String type, Integer isread, Long userid) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.isread = isread;
        this.userid = userid;
    }
    /**
     * 类型
     */

}
