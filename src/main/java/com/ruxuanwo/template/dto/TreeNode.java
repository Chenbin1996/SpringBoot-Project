package com.ruxuanwo.template.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形数据DTO，用于前端插件zTree对应属性
 *
 * @author ruxuanwo
 */
public class TreeNode implements Serializable {

    private String name;
    private String id;
    private String pId;
    private String parantPaths;
    private Boolean checked = false;
    private List<TreeNode> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getParantPaths() {
        return parantPaths;
    }

    public void setParantPaths(String parantPaths) {
        this.parantPaths = parantPaths;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void addChild(TreeNode child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", parantPaths='" + parantPaths + '\'' +
                ", checked=" + checked +
                ", children=" + children +
                '}';
    }
}
