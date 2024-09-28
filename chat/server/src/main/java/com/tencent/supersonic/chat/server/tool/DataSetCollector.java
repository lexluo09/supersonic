package com.tencent.supersonic.chat.server.tool;

import com.tencent.supersonic.headless.api.pojo.DataSetSchema;
import com.tencent.supersonic.headless.api.pojo.response.ItemResp;
import com.tencent.supersonic.headless.server.facade.service.SemanticLayerService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class DataSetCollector implements ToolCollector, InitializingBean {

    public static final String TYPE = "DATASET";

    @Autowired
    private SemanticLayerService semanticLayerService;

    @Override
    public List<ToolResource> collect() {
        List<ItemResp> domainDataSetTree = semanticLayerService.getDomainDataSetTree();
        return Converter.convert(domainDataSetTree);
    }

    @Override
    public List<CollectorInfo> getInfos(List<Long> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return new ArrayList<>();
        }
        List<CollectorInfo> results = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            DataSetSchema dataSetSchema = semanticLayerService.getDataSetSchema(resourceId);
            if (Objects.nonNull(dataSetSchema)) {
                results.add(convert(dataSetSchema));
            }
        }
        return results;
    }

    public static CollectorInfo convert(DataSetSchema dataSetSchema) {
        CollectorInfo dataSetInfo = new CollectorInfo();
        dataSetInfo.setDatabaseType(dataSetSchema.getDatabaseType());
        dataSetInfo.setDataSet(dataSetSchema.getDataSet());
        dataSetInfo.setMetrics(new HashSet<>(dataSetSchema.getMetrics()));
        dataSetInfo.setDimensions(new HashSet<>(dataSetSchema.getDimensions()));
        dataSetInfo.setTerms(new HashSet<>(dataSetSchema.getTerms()));
        dataSetInfo.setQueryUrl("/api/semantic/query/validateAndQuery");
        return dataSetInfo;
    }

    @Override
    public void afterPropertiesSet() {
        ToolCollectorFactory.add(TYPE, this);
    }
}
