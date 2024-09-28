package com.tencent.supersonic.chat.server.tool;

import java.util.HashMap;
import java.util.Map;

public class ToolCollectorFactory {

    private static final Map<String, ToolCollector> factories = new HashMap<>();

    public static void add(String toolType, ToolCollector toolCollector) {
        factories.put(toolType, toolCollector);
    }

    public static ToolCollector get(String toolType) {
        return factories.get(toolType);
    }

    public static Map<String, ToolCollector> getFactories() {
        return factories;
    }
}
