package com.ruxuanwo.template.dto;

import com.ruxuanwo.template.domain.SysDepartment;
import lombok.Data;

/**
 * 部门表DTO
 *
 * @author ruxuanwo
 */
@Data
public class SysDepartmentDTO extends SysDepartment {
    private String pname;
    private String primaryName;
    private String deputyName;
}
