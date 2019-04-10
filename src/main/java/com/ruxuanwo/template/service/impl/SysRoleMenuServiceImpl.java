package com.ruxuanwo.template.service.impl;


import com.ruxuanwo.template.base.BaseServiceImpl;
import com.ruxuanwo.template.domain.SysRoleMenu;
import com.ruxuanwo.template.dto.TreeNode;
import com.ruxuanwo.template.mapper.SysRoleMenuMapper;
import com.ruxuanwo.template.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色菜单service实现类
 *
 * @author ruxuanwo
 */
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper mapper;
    @Override
    public List<TreeNode> findByRoleId(String roleId) {
        return mapper.findByRoleId(roleId);
    }
}
