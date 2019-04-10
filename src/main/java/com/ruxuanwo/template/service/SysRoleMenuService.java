package com.ruxuanwo.template.service;

import com.ruxuanwo.template.base.BaseService;
import com.ruxuanwo.template.domain.SysRoleMenu;
import com.ruxuanwo.template.dto.TreeNode;

import java.util.List;

/**
 * 角色菜单service
 *
 * @author ruxuanwo
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu> {

    List<TreeNode> findByRoleId(String roleId);
}
