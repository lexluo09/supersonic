package com.tencent.supersonic.chat.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.tencent.supersonic.chat.api.pojo.request.ToolQueryReq;
import com.tencent.supersonic.chat.server.persistence.dataobject.ToolDO;
import com.tencent.supersonic.chat.server.persistence.repository.ToolRepository;
import com.tencent.supersonic.chat.server.service.ToolService;
import com.tencent.supersonic.chat.server.tool.ChatTool;
import com.tencent.supersonic.chat.server.tool.CollectorInfo;
import com.tencent.supersonic.chat.server.tool.ToolCollector;
import com.tencent.supersonic.chat.server.tool.ToolCollectorFactory;
import com.tencent.supersonic.chat.server.tool.ToolDetailInfo;
import com.tencent.supersonic.chat.server.tool.ToolResource;
import com.tencent.supersonic.chat.server.tool.event.ToolAddEvent;
import com.tencent.supersonic.chat.server.tool.event.ToolDelEvent;
import com.tencent.supersonic.common.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ToolServiceImpl implements ToolService {

    private ToolRepository toolRepository;

    private ApplicationEventPublisher publisher;

    public ToolServiceImpl(ToolRepository toolRepository, ApplicationEventPublisher publisher) {
        this.toolRepository = toolRepository;
        this.publisher = publisher;
    }

    @Override
    public synchronized void createTool(ChatTool chatTool, User user) {
        ToolDO toolDO = convert(chatTool, user);
        toolRepository.createTool(toolDO);
        // compatible with H2 db
        List<ChatTool> toolList = getToolList();
        publisher.publishEvent(new ToolAddEvent(this, toolList.get(toolList.size() - 1)));
    }

    @Override
    public void updateTool(ChatTool chatTool, User user) {
        Long id = chatTool.getId();
        ToolDO tool = toolRepository.getTool(id);
        convert(chatTool, tool, user);
        toolRepository.updateTool(tool);
    }

    @Override
    public void deleteTool(Long id) {
        ToolDO toolDOS = toolRepository.getTool(id);
        if (toolDOS != null) {
            toolRepository.deleteTool(id);
            publisher.publishEvent(new ToolDelEvent(this, convert(toolDOS)));
        }
    }

    @Override
    public List<ChatTool> getToolList() {
        List<ChatTool> chatTools = Lists.newArrayList();
        List<ToolDO> tools = toolRepository.getTools();
        if (CollectionUtils.isEmpty(tools)) {
            return chatTools;
        }
        return tools.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public List<ChatTool> query(ToolQueryReq toolQueryReq) {
        QueryWrapper<ToolDO> queryWrapper = new QueryWrapper<>();

        if (Objects.nonNull(toolQueryReq.getToolType())) {
            queryWrapper.lambda().eq(ToolDO::getType, toolQueryReq.getToolType());
        }
        if (StringUtils.isNotBlank(toolQueryReq.getName())) {
            queryWrapper.lambda().like(ToolDO::getName, toolQueryReq.getName());
        }
        if (StringUtils.isNotBlank(toolQueryReq.getCreatedBy())) {
            queryWrapper.lambda().eq(ToolDO::getCreatedBy, toolQueryReq.getCreatedBy());
        }
        List<ToolDO> toolDOS = toolRepository.query(queryWrapper);
        return convertList(toolDOS);
    }

    @Override
    public List<ChatTool> queryWithAuthCheck(ToolQueryReq toolQueryReq, User user) {
        return authCheck(query(toolQueryReq), user);
    }

    // todo
    private List<ChatTool> authCheck(List<ChatTool> chatTools, User user) {
        return chatTools;
    }

    public ChatTool convert(ToolDO toolDO) {
        ChatTool chatTool = new ChatTool();
        BeanUtils.copyProperties(toolDO, chatTool);
        if (StringUtils.isNotBlank(toolDO.getResourceIds())) {
            chatTool.setResourceIds(Arrays.stream(toolDO.getResourceIds().split(","))
                    .map(Long::parseLong).collect(Collectors.toList()));
        }
        chatTool.setType(toolDO.getType());
        return chatTool;
    }

    public ToolDO convert(ChatTool chatTool, User user) {
        ToolDO toolDO = new ToolDO();
        BeanUtils.copyProperties(chatTool, toolDO);
        toolDO.setCreatedAt(new Date());
        toolDO.setCreatedBy(user.getName());
        toolDO.setUpdatedAt(new Date());
        toolDO.setUpdatedBy(user.getName());
        toolDO.setType(chatTool.getType());
        toolDO.setResourceIds(StringUtils.join(chatTool.getResourceIds(), ","));
        return toolDO;
    }

    public ToolDO convert(ChatTool chatTool, ToolDO toolDO, User user) {
        BeanUtils.copyProperties(chatTool, toolDO);
        toolDO.setUpdatedAt(new Date());
        toolDO.setUpdatedBy(user.getName());
        toolDO.setResourceIds(StringUtils.join(chatTool.getResourceIds(), ","));
        toolDO.setType(chatTool.getType());
        return toolDO;
    }

    public List<ChatTool> convertList(List<ToolDO> toolDOS) {
        if (!CollectionUtils.isEmpty(toolDOS)) {
            return toolDOS.stream().map(this::convert).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public List<ToolResource> queryResource(ToolQueryReq toolQueryReq, User user) {
        ToolCollector toolCollector = ToolCollectorFactory.get(toolQueryReq.getToolType());
        return toolCollector.collect();
    }

    @Override
    public List<ToolDetailInfo> queryToolDetailInfos(User user) {
        List<ChatTool> toolList = getToolList();
        List<ToolDetailInfo> results = new ArrayList<>();

        for (ChatTool chatTool : toolList) {
            ToolDetailInfo toolDetailInfo = new ToolDetailInfo<>();
            BeanUtils.copyProperties(chatTool, toolDetailInfo);

            ToolCollector toolCollector = ToolCollectorFactory.get(chatTool.getType());
            List<Long> resourceIds = chatTool.getResourceIds();
            List<CollectorInfo> infos = toolCollector.getInfos(resourceIds);
            if (!CollectionUtils.isEmpty(infos)) {
                toolDetailInfo.setResources(infos);
            }
            results.add(toolDetailInfo);
        }
        return results;
    }
}
