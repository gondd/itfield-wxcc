package cn.itfield.wxcc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSmss {
    private String ip;
    private Date sendTime=new Date();
    private Long userId;
    private String phone;

    public MessageSmss(String ip, Long userId, String phone) {
        this.ip = ip;
        this.userId = userId;
        this.phone = phone;
    }
}
