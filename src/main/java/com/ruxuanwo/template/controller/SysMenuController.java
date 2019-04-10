package com.ruxuanwo.template.controller;


import com.ruxuanwo.template.domain.SysMenu;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.dto.SysMenuDTO;
import com.ruxuanwo.template.dto.TreeNode;
import com.ruxuanwo.template.service.SysMenuService;
import com.ruxuanwo.template.utils.ResultUtil;
import com.ruxuanwo.template.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 菜单controller
 *
 * @author ruxuanwo
 */
@Controller
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService menuService;

    /**
     * 增加菜单页面
     *
     * @return view
     */
    @GetMapping("/addMenuUI")
    public String addMenuUI(HttpServletRequest request) {
        List<SysMenu> parentMenus = menuService.selectParentMenuList();
        request.setAttribute("parentMenus", parentMenus);
        return "/menu/menuAddUI";
    }

    /**
     * 增加菜单页面
     */
    @GetMapping("/editMenuUI")
    public String editMenuUI(@RequestParam("id") String id, HttpServletRequest request) {
        SysMenuDTO menu = menuService.getMenuByToUpdate(id);
        request.setAttribute("menu", menu);
        return "/menu/menuEditUI";
    }

    /**
     * 修改菜单操作
     *
     * @param name       标题
     * @param path       路径
     * @param icon       图标
     * @param pid        父级菜单id
     * @param boolIsleaf 是否叶子
     * @param remark     备注
     * @return Result
     */
    @ResponseBody
    @PostMapping("/editMenuHandel")
    public Result editMenuHandel(@RequestParam("id") String id,
                                 @RequestParam(value = "title") String name,
                                 @RequestParam(value = "path", required = false) String path,
                                 @RequestParam(value = "icon", required = false) String icon,
                                 @RequestParam(value = "pid", required = false) String pid,
                                 @RequestParam(value = "boolIsleaf", required = false) Boolean boolIsleaf,
                                 @RequestParam(value = "remark", required = false) String remark,
                                 HttpServletRequest request) {
        SysMenu tMenu = new SysMenu(id, name, path, icon, pid, boolIsleaf, remark);
        boolean flag = menuService.updateById(tMenu);
        if (!flag) {
            ResultUtil.error("修改数据失败");
        }
        return ResultUtil.success();
    }

    /**
     * 增加菜单操作
     *
     * @param name       标题
     * @param path       路径
     * @param icon       图标
     * @param pid        父级菜单id
     * @param boolIsleaf 是否叶子
     * @param remark     备注
     * @return Result
     */
    @ResponseBody
    @PostMapping("/addMenuHandel")
    public Result addMenuHandel(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "path", required = false) String path,
            @RequestParam(value = "icon", required = false) String icon,
            @RequestParam(value = "pid", required = false) String pid,
            @RequestParam(value = "boolIsleaf", required = false) Boolean boolIsleaf,
            @RequestParam(value = "remark", required = false) String remark,
            HttpServletRequest request) {
        SysMenu tMenu = new SysMenu(name, path, icon, pid, boolIsleaf, remark);
        boolean isSaveSuccess = menuService.save(tMenu);
        if (!isSaveSuccess) {
            return ResultUtil.error("增加菜单失败！");
        }
        return ResultUtil.success();
    }

    /**
     * 删除菜单操作
     *
     * @param id 菜单id
     */
    @ResponseBody
    @PostMapping("/removeMenuHandle")
    public Result removeMenuHandle(@RequestParam(value = "id") String id) {
        boolean flag = menuService.removeById(id);
        if (!flag) {
            return ResultUtil.error("删除功能失败！");
        }
        return ResultUtil.success();
    }

    @ResponseBody
    @GetMapping("/getMenuTreeNodes")
    public Result getMenuTreeNodes() {
        List<SysMenuDTO> list = menuService.getMenuList(null);
        return ResultUtil.success(list);
    }

    /**
     * 菜单列表数据
     */
    @ResponseBody
    @GetMapping("/getMenuList")
    public Result getMenuList() {
        List<SysMenu> list = menuService.selectMenuList();
        return ResultUtil.success(list);
    }

    /**
     * 菜单列表页面
     */
    @GetMapping("/menuListUI")
    public String menuListUI(HttpServletRequest request) {
        return "/menu/menuListUI";
    }

    /**
     * 树节点查询子节点
     *
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/findByTree")
    public Result findByTree(@RequestParam(value = "roleId") String roleId) {
        List<TreeNode> treeNodes = menuService.selectByTree(roleId);
        return ResultUtil.success(TreeUtil.convert(treeNodes));
    }
}
