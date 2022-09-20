package cn.itfield.wxcc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEmails {
    private String email;

    public MessageEmails(String email, String copyto, Long userId) {
        this.email = email;
        this.copyto = copyto;
        this.userId = userId;
    }

    /**
     * 抄送
     */
    private String copyto;
    private Long userId;

    private Date sendTime=new Date();
}
