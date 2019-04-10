package com.ruxuanwo.template.service;

import com.ruxuanwo.template.base.BaseService;
import com.ruxuanwo.template.domain.SysMenu;
import com.ruxuanwo.template.dto.SysMenuDTO;
import com.ruxuanwo.template.dto.TreeNode;

import java.util.List;

/**
 * 菜单service
 *
 * @author ruxuanwo
 */
public interface SysMenuService extends BaseService<SysMenu> {
    List<SysMenuDTO> getMenuList(List<String> roleIds);
    List<SysMenu> selectMenuList();
    List<SysMenu> selectParentMenuList();
    SysMenuDTO getMenuByToUpdate(String id);

    List<TreeNode> selectByTree(String roleId);
}
