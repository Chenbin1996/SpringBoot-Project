package com.ruxuanwo.template.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.base.BaseServiceImpl;
import com.ruxuanwo.template.domain.SysRole;
import com.ruxuanwo.template.domain.SysUser;
import com.ruxuanwo.template.dto.LoginInfoDTO;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.dto.SysUserDTO;
import com.ruxuanwo.template.enums.StatusEnums;
import com.ruxuanwo.template.mapper.SysUserMapper;
import com.ruxuanwo.template.service.SysRoleService;
import com.ruxuanwo.template.service.SysUserService;
import com.ruxuanwo.template.utils.JWTUtil;
import com.ruxuanwo.template.utils.PassWordEncryptionUtil;
import com.ruxuanwo.template.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户service实现类
 *
 * @author ruxuanwo
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysRoleService roleService;

    @Override
    public IPage<SysUserDTO> selectUserList(Page<SysUserDTO> page, SysUser entity) {
        return baseMapper.selectUserList(page, entity);
    }

    /**
     * 验证登录
     *
     * @param loginName
     * @param passWord
     * @return
     * @author ruxuanwo
     */
    @Override
    public Result login(String loginName, String passWord) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("login_name", loginName);
        List<SysUser> users = baseMapper.selectByMap(params);
        if (CollectionUtils.isEmpty(users)) {
            return ResultUtil.error("系统不存在当前用户！");
        }
        SysUser user = getUser(users, passWord);
        if (user == null) {
            return ResultUtil.error("用户名或者密码错误！");
        }
        if (!StatusEnums.ENABLE.getCode().equals(user.getStatus())) {
            return ResultUtil.error("该用户已失效");
        }
        List<SysRole> byUserId = roleService.findByUserId(user.getId());
        Map<String, Object> payload = new HashMap<>(16);
        payload.put("userId", user.getId());
        String token = JWTUtil.createToken(payload);
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();
        loginInfoDTO.setUser(user);
        loginInfoDTO.setRole(byUserId);
        loginInfoDTO.setToken(token);
        return ResultUtil.builderResponse(101, "效验成功", token);
    }

    /**
     * 根据密码获取用户
     *
     * @param users    根据用户名查出的用户集合
     * @param passWord 传入的密码
     * @return
     * @author ruxuanwo
     */
    private SysUser getUser(List<SysUser> users, String passWord) {
        for (SysUser user : users) {
            if (equalPassword(passWord, user)) {
                return user;
            }
        }
        return null;
    }

    /**
     * 判断密码
     *
     * @param passWord 传入的密码
     * @param userInfo 根据用户名查出的用户
     * @return
     * @author ruxuanwo
     */
    private boolean equalPassword(String passWord, SysUser userInfo) {
        boolean result;
        if (StringUtils.isNotEmpty(userInfo.getPassWord())) {
            result = userInfo.getPassWord().equals(PassWordEncryptionUtil.passWordEncryption(userInfo.getLoginName(), passWord));
        } else {
            throw new RuntimeException("登录失败，该用户未设置密码！");
        }
        return result;
    }


}
