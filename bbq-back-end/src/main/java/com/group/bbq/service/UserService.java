package com.group.bbq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.bbq.common.dto.UpdateUserPasswordDto;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.User;

/**
 * <p>
 * user info table service
 * </p>
 */
public interface UserService extends IService<User> {

    Result register(User user);

    Result edit(User user);

    Result updatePassword(UpdateUserPasswordDto updateUserPasswordDto);

    Result setRole(Long userId,String role);

    Result setUserDisable(Long userId);

    Result setUserEnable(Long userId);

    Result getAllUserInfo(Integer pageNum, Integer pageSize);

}
