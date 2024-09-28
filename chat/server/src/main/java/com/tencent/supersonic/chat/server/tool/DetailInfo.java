package com.tencent.supersonic.chat.server.tool;

import lombok.Data;

import java.util.List;

@Data
public class DetailInfo {
    private Long id;
    private String name;
    private List<String> dimensions;
    private List<String> metrics;
    private String config;
    private String comment;
}
