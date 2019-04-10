package com.ruxuanwo.template.dto;

import com.ruxuanwo.template.domain.SysRole;
import lombok.Data;

/**
 * 角色表DTO
 *
 * @author ruxuanwo
 */
@Data
public class SysRoleDTO extends SysRole {
    private String statusName;
}
