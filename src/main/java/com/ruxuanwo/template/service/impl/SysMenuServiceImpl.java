package com.ruxuanwo.template.service.impl;


import com.ruxuanwo.template.base.BaseServiceImpl;
import com.ruxuanwo.template.domain.SysMenu;
import com.ruxuanwo.template.dto.SysMenuDTO;
import com.ruxuanwo.template.dto.TreeNode;
import com.ruxuanwo.template.mapper.SysMenuMapper;
import com.ruxuanwo.template.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单service实现类
 *
 * @author ruxuanwo
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper tMenuMapper;

    @Override
    public List<SysMenuDTO> getMenuList(List<String> roleIds) {
        if (roleIds != null && roleIds.isEmpty()){
            roleIds.add("0");
        }
        Map<String, Object> params = new HashMap<>(16);
        params.put("roleIds", roleIds);
        return baseMapper.getMenu(params);
    }

    @Override
    public List<SysMenu> selectMenuList() {
        return baseMapper.selectMenuList();
    }

    @Override
    public List<SysMenu> selectParentMenuList() {
        return baseMapper.selectParentMenuList();
    }

    @Override
    public SysMenuDTO getMenuByToUpdate(String id) {
        return baseMapper.getMenuByToUpdate(id);
    }

    @Override
    public List<TreeNode> selectByTree(String roleId) {
        return tMenuMapper.selectByTree(roleId);

    }
}
