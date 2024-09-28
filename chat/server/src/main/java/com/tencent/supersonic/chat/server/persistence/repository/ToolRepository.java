package com.tencent.supersonic.chat.server.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.supersonic.chat.server.persistence.dataobject.ToolDO;

import java.util.List;

public interface ToolRepository {
    List<ToolDO> getTools();

    List<ToolDO> fetchToolDOs(String queryText, String type);

    void createTool(ToolDO pluginDO);

    void updateTool(ToolDO pluginDO);

    ToolDO getTool(Long id);

    List<ToolDO> query(QueryWrapper<ToolDO> queryWrapper);

    void deleteTool(Long id);
}
