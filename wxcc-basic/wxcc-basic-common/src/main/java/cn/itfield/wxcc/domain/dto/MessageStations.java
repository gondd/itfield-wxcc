package cn.itfield.wxcc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageStations {


    /**
     * 类型
     */
    private String type;

    private Date sendTime;

    private Integer isread;

    private Long userId;
}
