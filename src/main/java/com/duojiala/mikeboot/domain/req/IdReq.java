package com.duojiala.mikeboot.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Id请求体")
public class IdReq {
    @ApiModelProperty("主键id")
    @NotNull//validate校验
    @Size(max = 10,message = "id长度过长") //validate校验
    private String id;
}
