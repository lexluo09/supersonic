package com.tencent.supersonic.chat.server.tool.event;

import com.tencent.supersonic.chat.server.tool.ChatTool;
import org.springframework.context.ApplicationEvent;

public class ToolAddEvent extends ApplicationEvent {

    private ChatTool chatTool;

    public ToolAddEvent(Object source, ChatTool chatTool) {
        super(source);
        this.chatTool = chatTool;
    }

    public ChatTool getChatTool() {
        return chatTool;
    }
}
