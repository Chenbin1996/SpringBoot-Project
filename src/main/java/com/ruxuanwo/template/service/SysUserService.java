package com.ruxuanwo.template.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.base.BaseService;
import com.ruxuanwo.template.domain.SysUser;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.dto.SysUserDTO;

/**
 * 用户service
 *
 * @author ruxuanwo
 */
public interface SysUserService extends BaseService<SysUser> {
    IPage<SysUserDTO> selectUserList(Page<SysUserDTO> page, SysUser entity);

    /**
     * 验证登录
     *
     * @author ruxuanwo
     * @param loginName
     * @param passWord
     * @return
     */
    Result login(String loginName, String passWord);
}
