package com.duojiala.mikeboot.controller;

import com.duojiala.mikeboot.domain.dto.ResponseBean;
import com.duojiala.mikeboot.domain.enums.GenderEnum;
import com.duojiala.mikeboot.domain.req.IdReq;
import com.duojiala.mikeboot.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j //日志注解，由lombok提供
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //lombok的注解，管理重复注解，在所有字段上加上Autowired注解，字段需final修饰或者加上@notnull注解
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})//spring提供的 用来处理跨域资源共享（CORS）的注解
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/query-user-info")
    @ApiOperation(value = "根据id查询用户详情")
    public ResponseBean queryUserInfo(@RequestBody @Validated IdReq req) { //springboot@Validated校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理。
        return ResponseBean.success(userService.queryUserInfo(req));
    }

    @PostMapping(value = "/query-user-infos")
    @ApiOperation(value = "条件查询用户信息")
    public ResponseBean queryUserInfos(
            // name 用户名称 不必填
            @ApiParam(value = "用户名称", required = false) @RequestParam(required = false) String name,//@RequestParam(required = false) 没有接收到参数不抛异常,不写抛
            // gender 用户性别 必填
            @ApiParam(value = "用户性别", required = true) @RequestParam(required = true) GenderEnum gender
    ) {
        return ResponseBean.success(userService.queryUserInfos(name,gender));
    }

    @GetMapping(value = "/get-token")
    @ApiOperation(value = "获取请求头中的token信息")
    public void getToken(
            @RequestHeader(value = "token",required = false) String token
    ) {
        // 直接获取 token 信息
        System.out.println("token = " + token);

        // 通过代码获取
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String header = request.getHeader("token");
            System.err.println("header = " + header);
        }
    }
}
