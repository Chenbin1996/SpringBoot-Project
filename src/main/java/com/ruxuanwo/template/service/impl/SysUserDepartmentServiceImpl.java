package com.ruxuanwo.template.service.impl;

import com.ruxuanwo.template.base.BaseServiceImpl;
import com.ruxuanwo.template.domain.SysUserDepartment;
import com.ruxuanwo.template.mapper.SysUserDepartmentMapper;
import com.ruxuanwo.template.service.SysUserDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户部门service实现类
 *
 * @author ruxuanwo
 */
@Service
public class SysUserDepartmentServiceImpl extends BaseServiceImpl<SysUserDepartmentMapper, SysUserDepartment> implements SysUserDepartmentService {
    @Autowired
    private SysUserDepartmentMapper departmentMapper;


}