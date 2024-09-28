package com.tencent.supersonic.chat.server.tool;

import com.google.common.collect.Lists;
import com.tencent.supersonic.chat.api.pojo.enums.ResourceType;
import lombok.Data;

import java.util.List;

@Data
public class ToolResource {
    private Long resourceId;
    private Long parentId;
    private String resourceName;
    private ResourceType resourceType;
    private List<ToolResource> children = Lists.newArrayList();
}
