package com.ruxuanwo.template.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruxuanwo.template.base.BaseServiceImpl;
import com.ruxuanwo.template.domain.SysDepartment;
import com.ruxuanwo.template.dto.SysDepartmentDTO;
import com.ruxuanwo.template.dto.TreeNode;
import com.ruxuanwo.template.mapper.SysDepartmentMapper;
import com.ruxuanwo.template.service.SysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 部门service实现类
 *
 * @author ruxuanwo
 */
@Service
public class SysDepartmentServiceImpl extends BaseServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {
    @Autowired
    private SysDepartmentMapper mapper;
    @Override
    public List<TreeNode> listTree() {
        return mapper.listTree();
    }

    @Override
    public List<SysDepartmentDTO> selectByPid(String pid, String name, String pname) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid",pid);
        map.put("name",name);
        map.put("pname",pname);
        return mapper.selectByPid(map);
    }

    @Override
    public List<SysDepartment> findByUserId(String userId) {
        return mapper.findByUserId(userId);
    }

    @Override
    public SysDepartmentDTO listDtoById(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        return mapper.listDtoById(map);
    }



}