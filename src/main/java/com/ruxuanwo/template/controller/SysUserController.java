package com.ruxuanwo.template.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.domain.*;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.enums.StatusEnums;
import com.ruxuanwo.template.service.*;
import com.ruxuanwo.template.service.token.TokenService;
import com.ruxuanwo.template.utils.PassWordEncryptionUtil;
import com.ruxuanwo.template.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用户controller
 *
 * @author ruxuanwo
 */
@Controller
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysDepartmentService departmentService;
    @Autowired
    private SysUserDepartmentService userDepartmentService;

    /**
     * 跳转表单页面
     *
     * @return
     */
    @GetMapping("/userListUI")
    public String userListUI() {
        return "/user/userListUI";
    }

    /**
     * 跳转新增页面
     *
     * @param request
     * @return
     */
    @GetMapping("/userAddUI")
    public String userAddUI(HttpServletRequest request) {
        List<SysRole> roles = roleService.list();
        List<SysDepartment> departments = departmentService.list();
        request.setAttribute("roles", roles);
        request.setAttribute("departments", departments);
        return "/user/userAddUI";
    }

    /**
     * 新增
     *
     * @param name
     * @param loginName
     * @param passWord
     * @param phoneNumber
     * @param email
     * @param roleId
     * @param departmentId
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/userAddHandel")
    public Result userAddHandel(@RequestParam(value = "name") String name,
                                @RequestParam(value = "loginName") String loginName,
                                @RequestParam(value = "passWord") String passWord,
                                @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                @RequestParam(value = "email", required = false) String email,
                                @RequestParam(value = "roleId", required = false) String[] roleId,
                                @RequestParam(value = "departmentId", required = false) String[] departmentId,
                                HttpServletRequest request) {
        String currentUserId = tokenService.getUserIdByRequest(request);
        SysUser tUser = new SysUser(name, loginName, passWord, phoneNumber, email, new Date());
        userService.save(tUser);
        /* 如果角色选择了，那就添加角色 */
        saveUserRoleAndDepartment(roleId, departmentId, tUser.getId(), currentUserId);

        return ResultUtil.success();
    }

    /**
     * 删除用户  批量
     *
     * @param ids 必填 ArrayList 用户id
     * @return
     */
    @ResponseBody
    @PostMapping("/removeUser")
    public Result removeUser(@RequestParam(name = "ids") String[] ids) {
        boolean isRemoveSuccess = userService.removeByIds(Arrays.asList(ids));
        if (!isRemoveSuccess) {
            return ResultUtil.error("删除用户失败！");
        }
        return ResultUtil.success();

    }

    /**
     * 修改用户页面
     *
     * @param id 必填 String 用户id
     * @return
     */
    @GetMapping("/editUserUI")
    public String editUserUI(@RequestParam("id") String id, HttpServletRequest request) {
        SysUser user = userService.getById(id);
        request.setAttribute("user", user);
        List<SysRole> roles = roleService.list();
        request.setAttribute("roles", roles);
        List<SysRole> userRoles = roleService.findByUserId(id);
        request.setAttribute("userRoles", userRoles);
        List<SysDepartment> departments = departmentService.list();
        request.setAttribute("departments", departments);
        List<SysDepartment> userDepartments = departmentService.findByUserId(id);
        request.setAttribute("userDepartments", userDepartments);
        return "/user/userEditUI";
    }

    /**
     * 切换用户状态
     *
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @PostMapping("/userSwitchStatusHandel")
    public Result userSwitchStatusHandel(@RequestParam("id") String id,
                                         @RequestParam("status") Integer status) {
        SysUser tUser = new SysUser();
        tUser.setId(id);
        tUser.setStatus(status);
        boolean flag = userService.updateById(tUser);
        if (!flag) {
            return ResultUtil.error("切换失败");
        }
        return ResultUtil.success();
    }

    /**
     * 修改用户信息
     *
     * @param id          必填 String 用户id
     * @param name        选填 String 用户名
     * @param loginName   选填 String 登录名
     * @param phoneNumber 选填 String 手机号码
     * @param eMail       选填 String 邮箱
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Result update(@RequestParam(name = "id") String id,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam(name = "loginName", required = false) String loginName,
                         @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                         @RequestParam(name = "email", required = false) String eMail,
                         @RequestParam(name = "roleId", required = false) String[] roleId,
                         @RequestParam(value = "departmentId", required = false) String[] departmentId,
                         HttpServletRequest request) {
        String currentUserId = tokenService.getUserIdByRequest(request);
        SysUser tUser = new SysUser(id, name, loginName, phoneNumber, eMail);
        userService.updateById(tUser);

        List<SysUserRole> tUserRoleRelations = userRoleService.find(new SysUserRole().setUserId(id));
        for (SysUserRole tUserRoleRelation : tUserRoleRelations) {
            userRoleService.removeById(tUserRoleRelation.getId());
        }
        List<SysUserDepartment> userDepartments = userDepartmentService.find(new SysUserDepartment().setUserId(id));
        for (SysUserDepartment userDepartment : userDepartments) {
            userDepartmentService.removeById(userDepartment.getId());
        }

        saveUserRoleAndDepartment(roleId, departmentId, tUser.getId(), currentUserId);
        request.setAttribute("tUser", tUser);
        return ResultUtil.success();
    }

    /**
     * 查询用户列表 分页
     *
     * @param name        选填 String 用户名
     * @param phoneNumber 选填 String 手机号
     * @param eMail       选填 String 邮箱
     * @param status      选填 String 状态
     * @param currentPage 选填 int 当前页
     * @param pageSize    选填 int 每页数量
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Result list(@RequestParam(name = "name", required = false) String name,
                       @RequestParam(name = "loginName", required = false) String loginName,
                       @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                       @RequestParam(name = "eMail", required = false) String eMail,
                       @RequestParam(name = "status", required = false) Integer status,
                       @RequestParam(name = "currentPage", required = false, defaultValue = Constant.PAGE) Integer currentPage,
                       @RequestParam(name = "pageSize", required = false, defaultValue = Constant.SIZE) Integer pageSize) {
        SysUser user = new SysUser();
        user.setName(name);
        user.setLoginName(loginName);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(eMail);
        user.setStatus(status);
        PageHelper.startPage(currentPage, pageSize);
        List<SysUser> users = userService.find(user);
        PageInfo<SysUser> pageInfo = new PageInfo<>(users);
        return ResultUtil.success(pageInfo);
    }

    /**
     * 更改用户状态 禁用/启用
     */
    @ResponseBody
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestParam(name = "id", required = true) String id) {
        SysUser tUser = userService.getById(id);
        if (tUser != null) {
            Integer status = tUser.getStatus();
            if (StatusEnums.ENABLE.getCode().equals(status)) {
                tUser.setStatus(StatusEnums.DISABLE.getCode());
            } else if (StatusEnums.DISABLE.getCode().equals(status)) {
                tUser.setStatus(StatusEnums.ENABLE.getCode());
            }
            if (!userService.updateById(tUser)) {
                return ResultUtil.error("更改状态失败");
            }
            return ResultUtil.success();
        }
        return ResultUtil.error("用户不存在");
    }

    /**
     * 重置密码
     *
     * @param id       必填 String 用户id
     * @param passWord 必填 String 新密码
     * @return
     * @date 2019/3/6 15:06
     */
    @ResponseBody
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestParam(name = "id", required = true) String id,
                                @RequestParam(name = "passWord", required = true) String passWord) {
        SysUser tUser = userService.getById(id);
        if (tUser != null) {
            if ("F".equals(tUser.getStatus())) {
                return ResultUtil.error("用户已被禁用");
            }
            tUser.setPassWord(PassWordEncryptionUtil.passWordEncryption(tUser.getLoginName(), passWord));
            boolean isSuccess = userService.updateById(tUser);
            if (!isSuccess) {
                return ResultUtil.error("重置密码失败");
            }
            return ResultUtil.success();
        }
        return ResultUtil.error("用户不存在");
    }

    /**
     * 新增用户角色以及用户部门
     *
     * @param roleId
     * @param departmentId
     * @param userId
     * @param currentUserId
     */
    private void saveUserRoleAndDepartment(String[] roleId, String[] departmentId, String userId, String currentUserId) {
        if (roleId != null && roleId.length > 0) {
            for (String role : roleId) {
                SysUserRole userRole = new SysUserRole(userId, role, new Date(), currentUserId);
                userRoleService.save(userRole);
            }
        }
        if (departmentId != null && departmentId.length > 0) {
            for (String department : departmentId) {
                SysUserDepartment userDepartment = new SysUserDepartment(userId, department, new Date(), currentUserId);
                userDepartmentService.save(userDepartment);
            }
        }
    }


}
