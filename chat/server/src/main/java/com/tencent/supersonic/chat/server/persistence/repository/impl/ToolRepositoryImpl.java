package com.tencent.supersonic.chat.server.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.supersonic.chat.server.persistence.dataobject.ToolDO;
import com.tencent.supersonic.chat.server.persistence.mapper.ToolDOMapper;
import com.tencent.supersonic.chat.server.persistence.repository.ToolRepository;
import com.tencent.supersonic.common.util.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class ToolRepositoryImpl implements ToolRepository {

    private ToolDOMapper toolDOMapper;

    public ToolRepositoryImpl(ToolDOMapper toolDOMapper) {
        this.toolDOMapper = toolDOMapper;
    }

    @Override
    public List<ToolDO> getTools() {
        return toolDOMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<ToolDO> fetchToolDOs(String queryText, String type) {
        ToolRepository pluginRepository = ContextUtils.getBean(ToolRepository.class);
        return pluginRepository.getTools();
    }

    @Override
    public void createTool(ToolDO toolDO) {
        toolDOMapper.insert(toolDO);
    }

    @Override
    public void updateTool(ToolDO toolDO) {
        toolDOMapper.updateById(toolDO);
    }

    @Override
    public ToolDO getTool(Long id) {
        return toolDOMapper.selectById(id);
    }

    @Override
    public List<ToolDO> query(QueryWrapper<ToolDO> queryWrapper) {
        return toolDOMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteTool(Long id) {
        toolDOMapper.deleteById(id);
    }
}
