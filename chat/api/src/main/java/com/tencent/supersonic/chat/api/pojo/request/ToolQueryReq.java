package com.tencent.supersonic.chat.api.pojo.request;

import lombok.Data;

@Data
public class ToolQueryReq {

    private String name;

    private String parseMode;

    private String toolType;

    private String createdBy;
}
