package com.tencent.supersonic.chat.server.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tencent.supersonic.auth.api.authentication.utils.UserHolder;
import com.tencent.supersonic.chat.api.pojo.request.ToolQueryReq;
import com.tencent.supersonic.chat.server.service.ToolService;
import com.tencent.supersonic.chat.server.tool.ChatTool;
import com.tencent.supersonic.chat.server.tool.ToolDetailInfo;
import com.tencent.supersonic.chat.server.tool.ToolResource;
import com.tencent.supersonic.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat/tool")
public class ToolController {

    @Autowired
    protected ToolService toolService;

    @PostMapping
    public boolean createTool(@RequestBody ChatTool chatTool, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        User user = UserHolder.findUser(httpServletRequest, httpServletResponse);
        toolService.createTool(chatTool, user);
        return true;
    }

    @PutMapping
    public boolean updateTool(@RequestBody ChatTool chatTool, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        User user = UserHolder.findUser(httpServletRequest, httpServletResponse);
        toolService.updateTool(chatTool, user);
        return true;
    }

    @DeleteMapping("/{id}")
    public boolean deleteTool(@PathVariable("id") Long id) {
        toolService.deleteTool(id);
        return true;
    }

    @RequestMapping("/getToolList")
    public List<ChatTool> getToolList() {
        return toolService.getToolList();
    }

    @PostMapping("/query")
    List<ChatTool> query(@RequestBody ToolQueryReq toolQueryReq,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User user = UserHolder.findUser(httpServletRequest, httpServletResponse);
        return toolService.queryWithAuthCheck(toolQueryReq, user);
    }

    @PostMapping("/queryResource")
    List<ToolResource> queryResource(@RequestBody ToolQueryReq toolQueryReq,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User user = UserHolder.findUser(httpServletRequest, httpServletResponse);
        return toolService.queryResource(toolQueryReq, user);
    }

    @GetMapping("/queryToolDetailInfos")
    List<ToolDetailInfo> queryToolDetailInfos(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        User user = UserHolder.findUser(httpServletRequest, httpServletResponse);
        return toolService.queryToolDetailInfos(user);
    }

}
