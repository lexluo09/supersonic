package com.tencent.supersonic.chat.server.tool;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatCollector implements ToolCollector, InitializingBean {

    public static final String TYPE = "CHAT";

    @Override
    public List<ToolResource> collect() {
        return null;
    }

    @Override
    public List<CollectorInfo> getInfos(List<Long> resourceIds) {
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        ToolCollectorFactory.add(TYPE, this);
    }
}
