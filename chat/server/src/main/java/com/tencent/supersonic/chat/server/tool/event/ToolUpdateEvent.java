package com.tencent.supersonic.chat.server.tool.event;

import com.tencent.supersonic.chat.server.tool.ChatTool;
import org.springframework.context.ApplicationEvent;

public class ToolUpdateEvent extends ApplicationEvent {

    private ChatTool oldToll;

    private ChatTool newTool;

    public ToolUpdateEvent(Object source, ChatTool chatTool, ChatTool newTool) {
        super(source);
        this.oldToll = chatTool;
        this.newTool = newTool;
    }

    public ChatTool getOldToll() {
        return oldToll;
    }

    public ChatTool getNewTool() {
        return newTool;
    }
}
