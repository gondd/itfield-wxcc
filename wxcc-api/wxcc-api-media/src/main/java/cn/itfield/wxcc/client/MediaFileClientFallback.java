package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.MediaFile;
import feign.hystrix.FallbackFactory;

import java.util.ArrayList;
import java.util.List;

public class MediaFileClientFallback implements FallbackFactory<MediaFileClient> {
    @Override
    public MediaFileClient create(Throwable throwable) {
        return new MediaFileClient() {
            @Override
            public List<MediaFile> getvoide(Long courseId) {
                return new ArrayList<>();
            }
        };
    }
}
