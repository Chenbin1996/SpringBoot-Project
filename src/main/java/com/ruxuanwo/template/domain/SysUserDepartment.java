package com.ruxuanwo.template.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户部门表
 *
 * @author ruxuanwo
 */
@Data
@TableName("sys_user_department")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUserDepartment extends Model<SysUserDepartment>{
	private static final long serialVersionUID = 1L;
	/* 主键 */
	@TableId("id")
	private	String	id;
	/* 用户ID */
	private String userId;
	/* 部门ID */
	private String departmentId;
	/* 创建时间 */
	private Date createTime;
	/* 创建用户 */
	private String createUser;

	public SysUserDepartment(String userId, String departmentId, Date createTime, String createUser) {
		this.userId = userId;
		this.departmentId = departmentId;
		this.createTime = createTime;
		this.createUser = createUser;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}