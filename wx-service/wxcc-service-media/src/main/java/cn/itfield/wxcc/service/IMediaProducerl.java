package cn.itfield.wxcc.service;

import cn.itfield.wxcc.domain.MediaFile;

public interface IMediaProducerl {
    Boolean syncSend(MediaFile mediaFile);
}
