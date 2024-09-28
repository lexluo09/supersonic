package com.tencent.supersonic.chat.server.tool;

import com.google.common.collect.Lists;
import com.tencent.supersonic.common.pojo.RecordInfo;
import lombok.Data;

import java.util.List;

@Data
public class ChatTool extends RecordInfo {
    private Long id;
    private String name;
    private String type;
    private List<Long> resourceIds = Lists.newArrayList();
    private String config;
    private String comment;
}
