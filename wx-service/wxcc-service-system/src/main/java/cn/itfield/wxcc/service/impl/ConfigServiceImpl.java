package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.Config;
import cn.itfield.wxcc.mapper.ConfigMapper;
import cn.itfield.wxcc.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-06
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
