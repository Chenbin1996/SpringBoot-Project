package com.ruxuanwo.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.domain.SysUser;
import com.ruxuanwo.template.dto.SysUserDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户mapper
 *
 * @author ruxuanwo
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUserDTO> selectUserList(Page<SysUserDTO> page, @Param("entity") SysUser entity);
}
