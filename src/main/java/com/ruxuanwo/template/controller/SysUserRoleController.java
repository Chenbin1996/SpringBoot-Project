package com.ruxuanwo.template.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.domain.SysUserRole;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.service.SysUserRoleService;
import com.ruxuanwo.template.service.token.TokenService;
import com.ruxuanwo.template.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户角色controller
 *
 * @author ruxuanwo
 */
@RestController
@RequestMapping("/userRole")
public class SysUserRoleController {

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private TokenService tokenService;

    /**
     * 新增用户角色（可批量）
     *
     * @param userId  必填  string  用户id
     * @param roleIds 必填  array  角色id集合
     */
    @ResponseBody
    @PutMapping("/addUserRole")
    public Result addUserRole(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "roleIds", required = true) String[] roleIds,
            HttpServletRequest request
    ) {
        String currentUserId = tokenService.getUserIdByRequest(request);
        List<SysUserRole> tUserRoleRelationList = new ArrayList<>();
        SysUserRole tUserRoleRelation;
        Date createTime = new Date();
        for (String roleId : roleIds) {
            tUserRoleRelation = new SysUserRole(userId, roleId, createTime, currentUserId);
            tUserRoleRelationList.add(tUserRoleRelation);
        }
        boolean isSaveSuccess = userRoleService.saveBatch(tUserRoleRelationList);
        if (!isSaveSuccess) {
            return ResultUtil.error("增加用户角色失败！");
        }
        request.setAttribute("userRoleList", tUserRoleRelationList);
        return ResultUtil.success();
    }

    /**
     * 删除用户角色（可批量）
     *
     * @param ids 必填  array   用户角色id集合
     * @date 2019/3/5 11:17
     */
    @DeleteMapping("removeUserRole")
    public Result removeUserRole(
            @RequestParam(name = "ids", required = true) List ids
    ) {
        boolean isRemoveSuccess = userRoleService.removeByIds(ids);
        if (!isRemoveSuccess) {
            return ResultUtil.error("删除用户角色失败！");
        }
        return ResultUtil.success("删除用户角色成功！");
    }

    /**
     * 更新用户角色
     *
     * @param id      必填  string  用户id
     * @param roleId  必填  string  角色id
     * @param request
     */
    @ResponseBody
    @PostMapping("/updateUserRole")
    public Result updateUserRole(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "roleId", required = true) String roleId,
            HttpServletRequest request
    ) {
        Date updateTime = new Date();
        SysUserRole tUserRoleRelation = new SysUserRole();
        tUserRoleRelation.setCreateTime(updateTime);
        tUserRoleRelation.setRoleId(roleId);
        tUserRoleRelation.setId(id);
        boolean isUpdateSuccess = userRoleService.updateById(tUserRoleRelation);
        if (!isUpdateSuccess) {
            return ResultUtil.error("修改用户角色失败");
        }
        return ResultUtil.success();
    }

    /**
     * 分页查询用户角色列表
     *
     * @param userId   非必填 string  用户id
     * @param pageNum  必填   integer 页码
     * @param pageSize 必填   integer 每页数据数
     */
    @ResponseBody
    @GetMapping("/listPage")
    public Result listPage(
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "pageNum", required = true) Integer pageNum,
            @RequestParam(name = "pageSize", required = true) Integer pageSize
    ) {
        IPage<SysUserRole> tUserRoleRelationIPage;
        try {
            if (userId == null) {
                tUserRoleRelationIPage = userRoleService.page(new Page<>(pageNum, pageSize), null);
            } else {
                /*构造查询条件*/
                Wrapper<SysUserRole> wrapper = new QueryWrapper<>();
                ((QueryWrapper<SysUserRole>) wrapper).eq("user_id", userId);
                tUserRoleRelationIPage = userRoleService.page(new Page<>(pageNum, pageSize), wrapper);
            }
            return ResultUtil.success(tUserRoleRelationIPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("未知错误查询数据失败");
        }
    }

    /**
     * 查询用户角色列表
     *
     * @param userId 非必填 string  用户id
     * @date 2019/3/5 11:25
     */
    @ResponseBody
    @GetMapping("/list")
    public Result list(
            @RequestParam(name = "userId", required = false) String userId
    ) {
        List<SysUserRole> result;
        SysUserRole tUserRoleRelation;
        try {
            if (null == userId) {
                result = userRoleService.list();
            } else {
                tUserRoleRelation = new SysUserRole();
                tUserRoleRelation.setUserId(userId);
                result = userRoleService.find(tUserRoleRelation);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("未知错误查询数据失败");
        }
    }
}
