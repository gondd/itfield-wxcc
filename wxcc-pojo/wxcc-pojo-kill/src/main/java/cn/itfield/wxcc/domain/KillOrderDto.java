package cn.itfield.wxcc.domain;

import lombok.Data;

@Data
public class KillOrderDto {
    private Long killcourseid;
    private Long userid;
    private String killstates;
}
