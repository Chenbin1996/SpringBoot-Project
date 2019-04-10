package com.ruxuanwo.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruxuanwo.template.domain.SysRoleMenu;
import com.ruxuanwo.template.dto.TreeNode;

import java.util.List;

/**
 * 角色菜单mapper
 *
 * @author ruxuanwo
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<TreeNode> findByRoleId(String roleId);
}
