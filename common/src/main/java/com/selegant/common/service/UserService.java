package com.selegant.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.selegant.common.base.Result;
import com.selegant.common.model.EtlUser;

import java.util.Map;

public interface UserService extends IService<EtlUser> {

    Result login(Map<String,String> params);

    Result getUserInfo(String accessToken);

    Result changePassword(String accessToken, String oldPassword, String newPassword, String confirmPassword);
}
