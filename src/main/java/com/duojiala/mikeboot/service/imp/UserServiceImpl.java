package com.duojiala.mikeboot.service.imp;

import com.duojiala.mikeboot.dao.UserMapper;
import com.duojiala.mikeboot.domain.entity.User;
import com.duojiala.mikeboot.domain.enums.ExceptionEnum;
import com.duojiala.mikeboot.domain.enums.GenderEnum;
import com.duojiala.mikeboot.domain.req.IdReq;
import com.duojiala.mikeboot.domain.resp.UserInfoResp;
import com.duojiala.mikeboot.exceptions.CommonException;
import com.duojiala.mikeboot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserInfoResp queryUserInfo(IdReq req) {
        User user = userMapper.selectByPrimaryKey(req.getId());
        if (user == null) {
            throw new CommonException(ExceptionEnum.NOT_FIND);
        }
        return UserInfoResp.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .birthday(user.getBirthday())
                .gender(GenderEnum.getDescByType(user.getGender()))
                .build();
    }

    @Override
    public List<UserInfoResp> queryUserInfos(String name, GenderEnum genderEnum) {
        Example example = Example.builder(User.class).build();
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            // 模糊查询姓名
            criteria.andLike("name","%" + name + "%");
        }
        criteria.andEqualTo("gender",genderEnum.getType());
        List<User> users = userMapper.selectByExample(example);
        List<UserInfoResp> respList = new ArrayList<>();
        for (User user : users) {
            respList.add(UserInfoResp.builder()
                    .id(user.getId().toString())
                    .name(user.getName())
                    .birthday(user.getBirthday())
                    .gender(GenderEnum.getDescByType(user.getGender()))
                    .build());
        }
        return respList;
    }
}
