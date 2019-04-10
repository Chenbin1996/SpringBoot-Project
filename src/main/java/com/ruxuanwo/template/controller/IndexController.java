package com.ruxuanwo.template.controller;


import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.domain.SysRole;
import com.ruxuanwo.template.domain.SysUser;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.dto.SysMenuDTO;
import com.ruxuanwo.template.service.SysMenuService;
import com.ruxuanwo.template.service.SysRoleService;
import com.ruxuanwo.template.service.SysUserService;
import com.ruxuanwo.template.service.token.TokenService;
import com.ruxuanwo.template.utils.PassWordEncryptionUtil;
import com.ruxuanwo.template.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页controller
 *
 * @author ruxuanwo
 */
@Controller
public class IndexController {
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserService userService;

    /**
     * 跳转首页，根据当前登录用户判断可展示菜单，超级管理员可显示所有
     *
     * @param request
     * @return
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        String currentUserId = tokenService.getUserIdByRequest(request);
        List<SysRole> byUserId = roleService.findByUserId(currentUserId);
        List<String> roleIds = isAdmin(byUserId) ? null : byUserId.stream().map(SysRole::getId).collect(Collectors.toList());
        List<SysMenuDTO> list = menuService.getMenuList(roleIds);
        // 放入菜单
        request.setAttribute("menus", list);
        SysUser user = tokenService.getUserByRequest(request);
        request.setAttribute("userName", user.getName());
        return "/index";
    }

    @GetMapping("/error_404")
    public String error_404(HttpServletRequest request) {
        return "/error/404";
    }

    @GetMapping("/error_500")
    public String error_500() {
        return "/error/error.html";
    }

    @GetMapping("/index_v1")
    public String index_v1(HttpServletRequest request) {
        return "/menu/menuListUI";
    }

    @GetMapping("/console")
    public String console(HttpServletRequest request) {
        return "/menu/menuListUI";
    }

    /**
     * 判断当前用户的角色中是否有超级管理员
     *
     * @param roles 用户所属角色集合
     * @return 返回布尔值
     */
    private Boolean isAdmin(List<SysRole> roles) {
        roles = roles.stream().filter(role -> Constant.ADMIN_NAME.equals(role.getName())).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(roles);
    }

    /**
     * 跳转编辑密码页面
     *
     * @return
     */
    @GetMapping("/editPasswordUI")
    public String editPasswordUI() {
        return "/user/password";
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧的密码
     * @param password    新的密码
     * @param repassword  确认新密码
     * @param request
     * @return
     */
    @PostMapping("/editPassword")
    @ResponseBody
    public Result editPassword(@RequestParam(name = "oldPassword") String oldPassword,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "repassword") String repassword,
                               HttpServletRequest request) {
        if (!repassword.equals(password)) {
            return ResultUtil.error("两次密码输入不一样");
        }
        SysUser tUser = tokenService.getUserByRequest(request);
        if (!tUser.getPassWord().equals(PassWordEncryptionUtil.passWordEncryption(tUser.getLoginName(), oldPassword))) {
            return ResultUtil.error("当前密码输入错误");
        }
        tUser.setPassWord(PassWordEncryptionUtil.passWordEncryption(tUser.getLoginName(), password));
        userService.updateById(tUser);
        return ResultUtil.success();
    }

}
