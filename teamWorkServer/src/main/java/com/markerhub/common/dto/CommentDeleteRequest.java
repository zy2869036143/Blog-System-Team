package com.markerhub.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
public class CommentDeleteRequest implements Serializable {
    @NotNull(message = "请求删除的用户id不能删除")
    private Long userId;
    @NotNull(message = "希望删除的评论不能为空")
    private Long commentId;
}
