package com.duojiala.mikeboot.service;

import com.duojiala.mikeboot.domain.enums.GenderEnum;
import com.duojiala.mikeboot.domain.req.IdReq;
import com.duojiala.mikeboot.domain.resp.UserInfoResp;

import java.util.List;

public interface UserService {

    UserInfoResp queryUserInfo(IdReq req);

    List<UserInfoResp> queryUserInfos(String name, GenderEnum genderEnum);
}
