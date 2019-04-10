package com.ruxuanwo.template.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.domain.SysRole;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.dto.SysRoleDTO;
import com.ruxuanwo.template.enums.StatusEnums;
import com.ruxuanwo.template.service.SysRoleService;
import com.ruxuanwo.template.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 角色controller
 *
 * @author ruxuanwo
 */
@Controller
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService roleService;


    /**
     * 增加角色页面
     *
     * @return
     */
    @GetMapping("/addRoleList")
    public String addRoleList(HttpServletRequest request) {
        List<Map<String, Object>> maps = getStatusMap();
        request.setAttribute("roleStatus", maps);
        return "role/roleAddUI";
    }

    /**
     * 增加角色
     *
     * @param name   必填 String 角色名
     * @param remark 必填 String 备注
     * @return
     */
    @ResponseBody
    @PostMapping("/addRole")
    public Result addRole(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "remark") String remark,
            @RequestParam(name = "status") Integer status
    ) {
        SysRole tRole = new SysRole();
        tRole.setStatus(status);
        tRole.setName(name);
        tRole.setRemark(remark);
        tRole.setCreateTime(new Date());
        boolean isAddSuccess = roleService.save(tRole);
        if (!isAddSuccess) {
            return ResultUtil.error("增加角色失败");
        }
        return ResultUtil.success();
    }

    /**
     * 删除角色(批量)
     *
     * @param ids 必填 ArrayList 角色id
     * @return
     */
    @PostMapping("/removeRole")
    @ResponseBody
    public Result removeRole(
            @RequestParam(name = "ids") ArrayList<String> ids
    ) {
        for (String id : ids) {
            boolean b = roleService.removeById(id);
            if (!b) {
                return ResultUtil.error("删除数据失败");
            }
        }
        return ResultUtil.success();
    }

    /**
     * 修改角色页面
     *
     * @param id 必填 String 角色id
     * @return
     */
    @GetMapping("/editRoleUI")
    public String editRoleUI(@RequestParam("id") String id, HttpServletRequest request) {
        SysRole role = roleService.getById(id);
        request.setAttribute("role", role);
        List<Map<String, Object>> maps = getStatusMap();
        request.setAttribute("roleStatus", maps);
        return "/role/roleEditUI";
    }

    /**
     * 修改角色信息
     * @param id 必填 String 角色id
     * @param status 选填 角色状态 1：启用；2：禁用
     * @param name 选填 String 角色名
     * @param remark 选填 String 备注
     * @return
     */
    @ResponseBody
    @PostMapping("/updateRole")
    public Result updateRole(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "remark", required = false) String remark
    ) {
        SysRole tRole = new SysRole();
        tRole.setId(id);
        tRole.setStatus(status);
        tRole.setName(name);
        tRole.setRemark(remark);
        boolean isSuccess = roleService.updateById(tRole);
        if (!isSuccess) {
            return ResultUtil.error("修改角色信息失败");
        }
        return ResultUtil.success();
    }

    /**
     * 角色列表页面
     *
     * @return
     */
    @GetMapping("/roleListUI")
    public String roleListUI() {
        return "/role/roleListUI";
    }

    /**
     * 查询角色 分页
     *
     * @param status      状态 1：可用；0：禁用
     * @param name        选填 String 角色名
     * @param remark      选填 String 备注
     * @param currentPage 选填 int 当前页数
     * @param pageSize    选填 int 每页数量
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Result list(
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "remark", required = false) String remark,
            @RequestParam(name = "currentPage", required = false, defaultValue = Constant.PAGE) Integer currentPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = Constant.SIZE) Integer pageSize
    ) {

        IPage<SysRoleDTO> rolePage = roleService.list(new Page<>(currentPage, pageSize), status, name, remark);
        return ResultUtil.success(rolePage);
    }

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     * @return
     */
    @ResponseBody
    @GetMapping("/updateStatus")
    public Result updateStatus(@RequestParam String id, @RequestParam Integer status) {
        SysRole role = roleService.getById(id);
        role.setStatus(status);
        roleService.updateById(role);
        return ResultUtil.success();
    }

    /**
     * 前端页面角色状态下拉框
     *
     * @return
     */
    private List<Map<String, Object>> getStatusMap() {
        List<Map<String, Object>> maps = new ArrayList<>();
        for (StatusEnums value : StatusEnums.values()) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("name", value.getName());
            map.put("code", value.getCode());
            maps.add(map);
        }
        return maps;
    }
}
