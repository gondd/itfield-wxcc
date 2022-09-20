package cn.itfield.wxcc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEmailDto {

    private String title;
    private String content;
    List<MessageEmails> messageEmails;

    /**
     * 抄送
     */
}
