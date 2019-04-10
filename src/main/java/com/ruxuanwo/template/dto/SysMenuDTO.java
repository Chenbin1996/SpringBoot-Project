package com.ruxuanwo.template.dto;

import com.ruxuanwo.template.domain.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * 菜单表DTO
 *
 * @author ruxuanwo
 */
@Data
public class SysMenuDTO extends SysMenu {
    public SysMenuDTO(String title, String path, String icon, String parentId, Boolean boolIsleaf, String remark) {
        super(title, path, icon, parentId, boolIsleaf, remark);
    }
    public SysMenuDTO(){

    }
    private String parentName;
    private List<SysMenu> nodes;
}
