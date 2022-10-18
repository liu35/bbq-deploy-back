package com.group.bbq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.UserLoginLog;

public interface UserLoginLogService extends IService<UserLoginLog> {

    Result getUserLoginLog(int pageNum, int pageSize);
}
