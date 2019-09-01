package com.ruxuanwo.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.domain.SysRole;
import com.ruxuanwo.template.dto.SysRoleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色mapper
 *
 * @author ruxuanwo
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户ID查询角色
     * @param params
     * @return
     */
    List<SysRole> findByUserId(Map<String, Object> params);

    /**
     * 分页数据
     * @param params
     * @return
     */
    List<SysRoleDTO> list(@Param("params") Map<String, Object> params);
}
