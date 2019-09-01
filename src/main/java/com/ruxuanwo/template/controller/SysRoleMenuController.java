package com.ruxuanwo.template.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.domain.SysRoleMenu;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.dto.TreeNode;
import com.ruxuanwo.template.service.SysRoleMenuService;
import com.ruxuanwo.template.service.token.TokenService;
import com.ruxuanwo.template.utils.ResultUtil;
import com.ruxuanwo.template.utils.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 角色菜单controller
 *
 * @author ruxuanwo
 */
@Controller
@RequestMapping("/roleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Autowired
    private TokenService tokenService;

    /**
     * 新增角色菜单
     *
     * @param roleId  必填  string  角色id
     * @param menuIds 必填  array   菜单id集合
     * @param request
     */
    @ResponseBody
    @PostMapping("/addRoleMenu")
    public Result addRoleMenu(
            @RequestParam(value = "roleId", required = true) String roleId,
            @RequestParam(value = "menuIds", required = true) String[] menuIds,
            HttpServletRequest request
    ) {
        List<SysRoleMenu> tRoleMenuRelations = roleMenuService.find(new SysRoleMenu().setRoleId(roleId));
        for (SysRoleMenu tRoleMenuRelation : tRoleMenuRelations) {
            roleMenuService.removeById(tRoleMenuRelation.getId());
        }
        String currentUserId = tokenService.getUserIdByRequest(request);
        for (String menuId : menuIds) {
            SysRoleMenu tRoleMenuRelation = new SysRoleMenu();
            tRoleMenuRelation.setRoleId(roleId);
            tRoleMenuRelation.setCreateUser(currentUserId);
            tRoleMenuRelation.setCreateTime(new Date());
            tRoleMenuRelation.setMenuId(menuId);
            roleMenuService.save(tRoleMenuRelation);
        }
        return ResultUtil.success();
    }


    /**
     * 删除角色菜单
     *
     * @param ids 必填  array   角色菜单id集合
     */
    @DeleteMapping("/removeRoleMenu")
    public Result removeRoleMenu(
            @RequestParam(value = "ids", required = true) List ids
    ) {
        roleMenuService.removeByIds(ids);
        return ResultUtil.success();
    }

    /**
     * 更新角色菜单
     *
     * @param id      必填  string  角色菜单id
     * @param menuId  必填  string  菜单id
     * @param request
     */
    @ResponseBody
    @PostMapping("/updateRoleMenu")
    public Result updateRoleMenu(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "menuId", required = true) String menuId,
            HttpServletRequest request
    ) {
        Date updateTime = new Date();
        SysRoleMenu tRoleMenuRelation = new SysRoleMenu();
        tRoleMenuRelation.setCreateTime(updateTime);
        tRoleMenuRelation.setMenuId(menuId);
        tRoleMenuRelation.setId(id);
        boolean isUpdateSuccess = roleMenuService.updateById(tRoleMenuRelation);
        if (!isUpdateSuccess) {
            return ResultUtil.error("修改角色菜单失败");
        }
        return ResultUtil.success();
    }

    /**
     * 角色菜单页面
     *
     * @return
     */
    @GetMapping("/roleMenuListUI")
    public String roleMenuListUI() {
        return "/roleMenu/roleMenuListUI";
    }

    /**
     * 分页查询角色菜单列表
     *
     * @param roleId   非必填 string  角色id
     * @param pageNum  必填   integer 页码
     * @param pageSize 必填   integer 每页数据数
     */
    @ResponseBody
    @GetMapping("/listPage")
    public Result listPage(
            @RequestParam(name = "roleId", required = false) String roleId,
            @RequestParam(name = "menuId", required = false) String menuId,
            @RequestParam(name = "pageNum", required = false, defaultValue = Constant.PAGE) Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = Constant.SIZE) Integer pageSize,
            HttpServletRequest request
    ) {
        SysRoleMenu tRoleMenuRelation = new SysRoleMenu();
        if (StringUtils.isNotEmpty(roleId)) {
            tRoleMenuRelation.setRoleId(roleId);
        }
        if (StringUtils.isNotEmpty(menuId)) {
            tRoleMenuRelation.setMenuId(menuId);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SysRoleMenu> page = roleMenuService.find(tRoleMenuRelation);
        PageInfo<SysRoleMenu> pageInfo = new PageInfo<>(page);
        return ResultUtil.success(pageInfo);
    }

    /**
     * 查询角色菜单列表
     *
     * @param roleId 非必填 string  角色id
     */
    @ResponseBody
    @GetMapping("/list")
    public Result list(
            @RequestParam(name = "roleId", required = false) String roleId
    ) {
        List<SysRoleMenu> result;
        SysRoleMenu tRoleMenuRelation;
        try {
            if (null == roleId) {
                result = roleMenuService.list();
            } else {
                tRoleMenuRelation = new SysRoleMenu();
                tRoleMenuRelation.setRoleId(roleId);
                result = roleMenuService.find(tRoleMenuRelation);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("未知错误查询数据失败");
        }
    }

    /**
     * 根据角色id查询角色菜单列表
     *
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/findByRoleId")
    public Result findByRoleId(@RequestParam(value = "roleId") String roleId) {
        List<TreeNode> treeNodes = roleMenuService.findByRoleId(roleId);
        return ResultUtil.success(TreeUtil.convert(treeNodes));
    }
}
