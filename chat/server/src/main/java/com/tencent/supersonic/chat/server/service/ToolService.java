package com.tencent.supersonic.chat.server.service;

import com.tencent.supersonic.chat.api.pojo.request.ToolQueryReq;
import com.tencent.supersonic.chat.server.tool.ChatTool;
import com.tencent.supersonic.chat.server.tool.ToolDetailInfo;
import com.tencent.supersonic.chat.server.tool.ToolResource;
import com.tencent.supersonic.common.pojo.User;

import java.util.List;

public interface ToolService {

    void createTool(ChatTool tool, User user);

    void updateTool(ChatTool chatTool, User user);

    void deleteTool(Long id);

    List<ChatTool> getToolList();

    List<ChatTool> query(ToolQueryReq toolQueryReq);

    List<ChatTool> queryWithAuthCheck(ToolQueryReq toolQueryReq, User user);

    List<ToolResource> queryResource(ToolQueryReq toolQueryReq, User user);

    List<ToolDetailInfo> queryToolDetailInfos(User user);
}
