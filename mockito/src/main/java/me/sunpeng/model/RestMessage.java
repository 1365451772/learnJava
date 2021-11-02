package me.sunpeng.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sp
 * @date 2021-11-02 10:32
 */
@ApiModel(description = "返回响应数据")
@Data
public class RestMessage implements Serializable {

    @ApiModelProperty(value = "是否成功",required = true)
    private boolean success = true;

    @ApiModelProperty(value = "错误吗")
    private Integer errCode;

    @ApiModelProperty(value = "提示信息")
    private String message;

    @ApiModelProperty(value = "数据")
    private Object data;
}
