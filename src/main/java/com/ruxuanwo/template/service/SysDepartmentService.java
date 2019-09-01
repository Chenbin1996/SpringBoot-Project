package com.ruxuanwo.template.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruxuanwo.template.base.BaseService;
import com.ruxuanwo.template.domain.SysDepartment;
import com.ruxuanwo.template.dto.SysDepartmentDTO;
import com.ruxuanwo.template.dto.TreeNode;

import java.util.List;

/**
 * 部门service
 *
 * @author ruxuanwo
 */
public interface SysDepartmentService extends BaseService<SysDepartment> {

    List<TreeNode> listTree();

    List<SysDepartmentDTO> selectByPid(String pid, String name, String pname);

    List<SysDepartment> findByUserId(String userId);

    SysDepartmentDTO listDtoById(String id);

}