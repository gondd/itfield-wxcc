package cn.itfield.wxcc.Dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
    private Integer type;
    private String phone;
    private String imageCode;
    private String smsCode;
    private Integer regChannel;
    private String imageCodeKey;
    private String invitecode;
}
