package me.sunpeng.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sp
 * @date 2021-11-01 20:20
 */
@Data
@ApiModel(description = "用户信息")
public class User implements Serializable {
    private static final long serialVersionUID = 2143689011597291054L;
    @ApiModelProperty("id")
    private int id;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("mobile")
    private String mobile;
    @ApiModelProperty("password")
    private String password;
    @ApiModelProperty("age")
    private int age;



}
