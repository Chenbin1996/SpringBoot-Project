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
 * 部门表
 *
 * @author ruxuanwo
 */
@Data
@TableName("sys_department")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysDepartment extends Model<SysDepartment>{
	private static final long serialVersionUID = 1L;
	/* 部门主键 */
	@TableId("id")
	private	String	id;
	/* 部门名称 */
	private String name;
	/* 部门简称 */
	private String shortName;
	/* 部门等级 */
	private Integer grade;
	/* 父级部门 */
	private String pid;
	/* 创建时间 */
	private Date createTime;
	/* 创建用户 */
	private String createUser;
	/* 部门状态，0：禁用；1：启动 */
	private Integer status;
	/* 排序 */
	private Byte sortOrder;
	/* 主要负责人 */
	private String primaryPerson;
	/* 副负责人 */
	private String deputyPerson;
	/* 备注 */
	private String remark;
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}