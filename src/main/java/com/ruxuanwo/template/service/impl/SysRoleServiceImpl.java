package com.ruxuanwo.template.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.base.BaseServiceImpl;
import com.ruxuanwo.template.domain.SysRole;
import com.ruxuanwo.template.dto.SysRoleDTO;
import com.ruxuanwo.template.mapper.SysRoleMapper;
import com.ruxuanwo.template.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色service实现类
 *
 * @author ruxuanwo
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper mapper;

    @Override
    public List<SysRole> findByUserId(String userId) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("userId", userId);
        return mapper.findByUserId(params);
    }

    @Override
    public IPage<SysRoleDTO> list(Page<SysRoleDTO> page, Integer status, String name, String remark) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("status", status);
        params.put("name", name);
        params.put("remark", remark);
        return page.setRecords(mapper.list(page, params));
    }
}
