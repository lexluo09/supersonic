package com.tencent.supersonic.chat.server.tool;

import com.tencent.supersonic.chat.api.pojo.enums.ResourceType;
import com.tencent.supersonic.common.pojo.enums.TypeEnums;
import com.tencent.supersonic.headless.api.pojo.response.ItemResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter {

    public static List<ToolResource> convert(List<ItemResp> itemRespList) {
        // Create a map to hold all ToolResource objects by their id
        Map<Long, ToolResource> toolResourceMap = new HashMap<>();

        // Create a list to hold root ToolResource objects
        List<ToolResource> rootToolResources = new ArrayList<>();

        // First pass: create all ToolResource objects and put them in the map
        for (ItemResp itemResp : itemRespList) {
            ToolResource toolResource = new ToolResource();
            toolResource.setResourceId(itemResp.getId());
            toolResource.setParentId(itemResp.getParentId());
            toolResource.setResourceName(itemResp.getName());
            toolResource.setResourceType(convertToResourceType(itemResp.getType()));
            toolResourceMap.put(itemResp.getId(), toolResource);
            rootToolResources.add(toolResource);
        }

        // Second pass: build the tree structure
        for (ItemResp itemResp : itemRespList) {
            ToolResource toolResource = toolResourceMap.get(itemResp.getId());
            toolResource.setChildren(convert(itemResp.getChildren()));
        }

        return rootToolResources;
    }

    private static ResourceType convertToResourceType(TypeEnums type) {
        switch (type) {
            case DOMAIN:
                return ResourceType.DOMAIN;
            case DATASET:
                return ResourceType.DATASET;
            default:
                return null;
        }
    }
}
