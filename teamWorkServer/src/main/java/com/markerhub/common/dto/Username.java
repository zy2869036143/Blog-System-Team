package com.markerhub.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class Username implements Serializable {
    @NotBlank (message = "搜索用户不能为空")
    private String username;
}
