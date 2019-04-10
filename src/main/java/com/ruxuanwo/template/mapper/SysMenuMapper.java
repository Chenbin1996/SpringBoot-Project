package com.ruxuanwo.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruxuanwo.template.domain.SysMenu;
import com.ruxuanwo.template.dto.SysMenuDTO;
import com.ruxuanwo.template.dto.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * 菜单mapper
 *
 * @author ruxuanwo
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据角色ID查询菜单，超级管理员除外
     *
     * @param params
     * @return
     * @author Yangxiaohui
     */
    List<SysMenuDTO> getMenu(Map<String, Object> params);

    List<SysMenu> selectMenuList();

    List<SysMenu> selectParentMenuList();

    SysMenuDTO getMenuByToUpdate(String id);

    List<TreeNode> selectByTree(String roleId);

}
