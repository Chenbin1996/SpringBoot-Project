package com.ruxuanwo.template.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ruxuanwo.template.enums.StatusEnums;
import com.ruxuanwo.template.utils.PassWordEncryptionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author ruxuanwo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends Model<SysUser> {
    private static final long serialVersionUID = 1L;

    /**
     * PK主键 PK主键
     */
    @TableId("id")
    private String id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 登陆账号
     */
    private String loginName;

    /**
     * 登陆密码
     */
    private String passWord;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 登陆时间
     */
    private Date loginTime;

    /**
     * 登陆次数
     */
    private Long loginCount;

    /**
     * 用户状态 1:正常 0:失效
     */
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public SysUser(String id,
                   String name,
                   String loginName,
                   String phoneNumber,
                   String eMail) {
        this.id = id;
        this.name = name;
        this.loginName = loginName;
        this.passWord = PassWordEncryptionUtil.passWordEncryption(loginName, passWord);
        this.phoneNumber = phoneNumber;
        this.email = eMail;
    }

    public SysUser(String name,
                   String loginName,
                   String passWord,
                   String phoneNumber,
                   String eMail, Date nowDate) {
        this.name = name;
        this.loginName = loginName;
        this.passWord = PassWordEncryptionUtil.passWordEncryption(loginName, passWord);
        this.phoneNumber = phoneNumber;
        this.email = eMail;
        this.createTime = nowDate;
        this.loginTime = nowDate;
        this.loginCount = 0L;
        this.status = StatusEnums.ENABLE.getCode();
    }

    public SysUser(
            String name,
            String phoneNumber,
            String eMail,
            Integer status
    ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = eMail;
        this.status = status;
    }
}
