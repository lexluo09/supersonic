package com.tencent.supersonic.chat.server.tool;

import com.tencent.supersonic.headless.api.pojo.SchemaElement;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CollectorInfo {
    private String databaseType;
    private SchemaElement dataSet;
    private Set<SchemaElement> metrics = new HashSet<>();
    private Set<SchemaElement> dimensions = new HashSet<>();
    private Set<SchemaElement> terms = new HashSet<>();
    private String queryUrl;
}
