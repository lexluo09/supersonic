package com.tencent.supersonic.chat.server.tool;

import java.util.List;

public interface ToolCollector {

    List<ToolResource> collect();

    List<CollectorInfo> getInfos(List<Long> resourceIds);
}
