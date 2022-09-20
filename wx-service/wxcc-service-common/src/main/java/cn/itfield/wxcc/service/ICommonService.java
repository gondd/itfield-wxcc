package cn.itfield.wxcc.service;

import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.result.JsonResult;

public interface ICommonService {
    JsonResult verifycode(String imageCodeKey);

    void sendSmsCode(LoginDto loginDto);
}
