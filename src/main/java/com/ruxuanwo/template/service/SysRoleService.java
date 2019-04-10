package com.ruxuanwo.template.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.base.BaseService;
import com.ruxuanwo.template.domain.SysRole;
import com.ruxuanwo.template.dto.SysRoleDTO;

import java.util.List;

/**
 * 角色service
 *
 * @author ruxuanwo
 */
public interface SysRoleService extends BaseService<SysRole> {
    /**
     * 根据用户ID获取角色
     *
     * @param userId
     * @return
     */
    List<SysRole> findByUserId(String userId);

    /**
     * 表单数据接口，所有的List数据都将是这个接口传输出去
     *
     * @param page
     * @param status 角色状态
     * @param name   角色名
     * @param remark 备注
     * @return
     */
    IPage<SysRoleDTO> list(Page<SysRoleDTO> page, Integer status, String name, String remark);

}
