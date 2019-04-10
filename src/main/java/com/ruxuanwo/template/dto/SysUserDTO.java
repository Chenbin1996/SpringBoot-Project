package com.ruxuanwo.template.dto;

import com.ruxuanwo.template.domain.SysUser;
import lombok.Data;

/**
 * 用户表DTO
 *
 * @author ruxuanwo
 */
@Data
public class SysUserDTO extends SysUser {
    private String departmentName;
    private String roleName;
}
