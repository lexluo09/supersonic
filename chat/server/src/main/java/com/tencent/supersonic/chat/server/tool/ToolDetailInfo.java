package com.tencent.supersonic.chat.server.tool;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class ToolDetailInfo<T> {
    private Long id;
    private String name;
    private String type;
    private List<T> resources = Lists.newArrayList();
    private String config;
    private String comment;
}
