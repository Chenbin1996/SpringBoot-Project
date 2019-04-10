package com.ruxuanwo.template.utils;

import com.ruxuanwo.template.dto.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 构建树形数据工具类
 *
 * @author ruxuanwo
 */
public class TreeUtil {

    private TreeUtil(){}

    /**
     * 将传过来的节点集合转换为树结构，
     * @param nodes
     * @return
     */
    public static List<TreeNode> convert(List<TreeNode> nodes){
        if(CollectionUtils.isEmpty(nodes)){
            return null;
        }
        TreeNode treeNode = new TreeNode();
        convert(nodes, treeNode, "0");
        return treeNode.getChildren();
    }
    /**
     * 将treeNode集合根据pid转换成一个树形结构
     * @param nodes
     * @return
     */
    private static void convert(List<TreeNode> nodes, TreeNode treeNode, String pid) {
        if (nodes == null || nodes.isEmpty() || StringUtils.isEmpty(pid)) {
            return;
        }
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode node = nodes.get(i);
            if (isParentAndSonByPid(pid, node)) {
                // 添加到孩子节点列表
                treeNode.addChild(node);
                nodes.remove(node);
                i = -1;
                // 递归，实现无限层级*//*
                convert(nodes, node, node.getId());
            }

        }
    }

    private static  Boolean isParentAndSonByPid(String pid, TreeNode node) {
        if ("0".equals(pid)) {
            if ( node!= null && pid.equals(node.getpId())) {
                return true;
            }
        } else {
            if (node != null && pid != null && (!"0".equals(node.getpId())) && (pid.startsWith(node.getpId()))) {
                return true;
            }
        }
        return false;
    }
}
