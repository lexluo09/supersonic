package com.tencent.supersonic.chat.server.tool.event;

import com.tencent.supersonic.chat.server.tool.ChatTool;
import org.springframework.context.ApplicationEvent;

public class ToolDelEvent extends ApplicationEvent {

    private ChatTool plugin;

    public ToolDelEvent(Object source, ChatTool plugin) {
        super(source);
        this.plugin = plugin;
    }

    public ChatTool getTool() {
        return plugin;
    }
}
