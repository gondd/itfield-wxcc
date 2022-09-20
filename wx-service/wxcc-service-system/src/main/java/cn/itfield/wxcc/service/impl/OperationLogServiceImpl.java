package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.OperationLog;
import cn.itfield.wxcc.mapper.OperationLogMapper;
import cn.itfield.wxcc.service.IOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-06
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
