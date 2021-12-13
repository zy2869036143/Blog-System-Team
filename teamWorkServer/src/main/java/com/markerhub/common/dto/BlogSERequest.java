package com.markerhub.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class BlogSERequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String content;
    private Integer status;
    private Integer praise;
    private Integer favorite;
    private JSONArray label;
}
