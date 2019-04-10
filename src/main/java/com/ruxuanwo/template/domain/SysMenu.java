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
 * 菜单表
 *
 * @author ruxuanwo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * PK 主键
     */
    @TableId("id")
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 父级菜单
     */
    private String pid;

    /**
     * 是否叶子节点
     */
    private Boolean boolIsleaf;

    /**
     * 备注菜单描述
     */
    private String remark;

    /**
     * 菜单图标
     */
    private String icon;

    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public SysMenu(String name,
                   String path,
                   String icon,
                   String pid,
                   Boolean boolIsleaf,
                   String remark) {
        this.name = name;
        this.path = path;
        this.icon = icon;
        this.pid = pid;
        this.boolIsleaf = boolIsleaf;
        this.remark = remark;
        this.createTime = new Date();
    }
    public SysMenu(String id,
                   String name,
                   String path,
                   String icon,
                   String pid,
                   Boolean boolIsleaf,
                   String remark) {
        this.id=id;
        this.name = name;
        this.path = path;
        this.icon = icon;
        this.pid = pid;
        this.boolIsleaf = boolIsleaf;
        this.remark = remark;
        this.createTime = new Date();
    }

    public SysMenu() {

    }

}
