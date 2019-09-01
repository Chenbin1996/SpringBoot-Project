package com.ruxuanwo.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruxuanwo.template.domain.SysDepartment;
import com.ruxuanwo.template.dto.SysDepartmentDTO;
import com.ruxuanwo.template.dto.TreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门mapper
 *
 * @author ruxuanwo
 */
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {


    List<TreeNode> listTree();

    List<SysDepartmentDTO> selectByPid(@Param("map") Map map);

    List<SysDepartment> findByUserId(String userId);

    SysDepartmentDTO listDtoById(@Param("map") Map map);

}