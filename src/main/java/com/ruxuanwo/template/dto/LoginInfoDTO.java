package com.ruxuanwo.template.dto;


import com.ruxuanwo.template.domain.SysRole;
import com.ruxuanwo.template.domain.SysUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 登录后将信息返回
 *
 * @author ruxuanwo
 */
@Data
@ToString
public class LoginInfoDTO {
    private SysUser user;
    private List<SysRole> role;
    private String token;
}
