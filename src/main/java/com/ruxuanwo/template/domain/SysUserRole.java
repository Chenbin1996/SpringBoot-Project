package com.ruxuanwo.template.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色表
 *
 * @author ruxuanwo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * PK 主键
     */
    @TableId("id")
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 创建/关联时间
     */
    private Date createTime;

    private String createUser;

    public SysUserRole(String userId, String roleId, Date createTime, String createUser) {
        this.userId = userId;
        this.roleId = roleId;
        this.createTime = createTime;
        this.createUser = createUser;
    }

    public SysUserRole() {

    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
