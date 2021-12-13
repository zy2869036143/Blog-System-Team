package com.markerhub.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Label implements Serializable {
    private static final long serialVersionUID = -1464964389043519003L;
    private String label;
}
