package cn.itfield.wxcc.service;

import cn.itfield.wxcc.result.JsonResult;

public interface ITokenService {
    JsonResult createToken(Long courseId, Long loginId);
}
