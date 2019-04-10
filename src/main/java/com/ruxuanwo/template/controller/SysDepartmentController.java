package com.ruxuanwo.template.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.domain.SysDepartment;
import com.ruxuanwo.template.domain.SysUser;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.dto.SysDepartmentDTO;
import com.ruxuanwo.template.dto.TreeNode;
import com.ruxuanwo.template.service.SysDepartmentService;
import com.ruxuanwo.template.service.SysUserService;
import com.ruxuanwo.template.service.token.TokenService;
import com.ruxuanwo.template.utils.ResultUtil;
import com.ruxuanwo.template.utils.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 部门controller
 *
 * @author ruxuanwo
 */
@Controller
@RequestMapping("/department")
public class SysDepartmentController {
    @Autowired
    private SysDepartmentService departmentService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysUserService userService;

    /**
     * 查询部门列表(分页)
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Result list() {
        List<SysDepartment> SysDepartments = departmentService.list();
        return ResultUtil.success(SysDepartments);
    }

    /**
     * 跳转表单页面
     *
     * @return
     */
    @GetMapping("departmentListUI")
    public String departmentListUI() {
        return "/department/departmentListUI";
    }

    /**
     * 构建部门树
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/listTree")
    public Result listTree() {
        List<TreeNode> treeNodes = departmentService.listTree();
        return ResultUtil.success(TreeUtil.convert(treeNodes));
    }

    /**
     * 新增部门
     *
     * @param name
     * @param shortName
     * @param grade
     * @param pid
     * @param status
     * @param sortOrder
     * @param primaryPerson
     * @param deputyPerson
     * @param remark
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public Result add(@RequestParam(name = "name") String name,
                      @RequestParam(name = "shortName", required = false) String shortName,
                      @RequestParam(name = "grade", required = false) Integer grade,
                      @RequestParam(name = "pid") String pid,
                      @RequestParam(name = "status", required = false) Integer status,
                      @RequestParam(name = "sortOrder", required = false) Byte sortOrder,
                      @RequestParam(name = "primaryPerson", required = false) String primaryPerson,
                      @RequestParam(name = "deputyPerson", required = false) String deputyPerson,
                      @RequestParam(name = "remark", required = false) String remark,
                      HttpServletRequest request) {
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setName(name);
        sysDepartment.setShortName(shortName);
        sysDepartment.setGrade(grade);
        if (StringUtils.isEmpty(pid)) {
            pid = "0";
        }
        sysDepartment.setPid(pid);
        if (status == null) {
            status = 1;
        }
        sysDepartment.setStatus(status);
        sysDepartment.setSortOrder(sortOrder);
        sysDepartment.setPrimaryPerson(primaryPerson);
        sysDepartment.setDeputyPerson(deputyPerson);
        sysDepartment.setRemark(remark);
        sysDepartment.setCreateTime(new Date());
        sysDepartment.setCreateUser(tokenService.getUserIdByRequest(request));
        departmentService.save(sysDepartment);
        return ResultUtil.success();
    }

    /**
     * 通过PID查询
     *
     * @param pid
     * @param name
     * @param pname
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/selectByPid")
    @ResponseBody
    public Result selectByPid(@RequestParam(name = "pid", required = false) String pid,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "pname", required = false) String pname,
                              @RequestParam(name = "currentPage", defaultValue = Constant.PAGE, required = false) Integer currentPage,
                              @RequestParam(name = "pageSize", defaultValue = Constant.SIZE, required = false) Integer pageSize) {
        IPage<SysDepartmentDTO> sysDepartmentDTOS = departmentService.selectByPid(new Page<SysDepartmentDTO>(currentPage, pageSize), pid, name, pname);
        return ResultUtil.success(sysDepartmentDTOS);
    }

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     * @return
     */
    @ResponseBody
    @GetMapping("/updateStatus")
    public Result updateStatus(@RequestParam String id,
                               @RequestParam Integer status) {
        SysDepartment sysDepartment = departmentService.getById(id);
        sysDepartment.setStatus(status);
        departmentService.updateById(sysDepartment);
        return ResultUtil.success();
    }

    /**
     * 跳转新增页面
     *
     * @param request
     * @return
     */
    @GetMapping("/departmentAddUI")
    public String departmentAddUI(HttpServletRequest request) {
        List<SysDepartment> sysDepartments = departmentService.list();
        List<SysUser> users = userService.list();
        request.setAttribute("users", users);
        request.setAttribute("departments", sysDepartments);
        return "/department/departmentAddUI";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @PostMapping("/remove")
    @ResponseBody
    public Result remove(@RequestParam(name = "id") String id) {
        departmentService.removeById(id);
        return ResultUtil.success();
    }

    /**
     * 跳转编辑页面
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/editDepartmentUI")
    public String ediSysDepartmentUI(String id,
                                     HttpServletRequest request) {
        List<SysDepartment> sysDepartments = departmentService.list();
        List<SysUser> users = userService.list();
        SysDepartmentDTO sysDepartmentDTOS = departmentService.listDtoById(id);
        request.setAttribute("departmentDTOS", sysDepartmentDTOS);
        request.setAttribute("users", users);
        request.setAttribute("departments", sysDepartments);
        return "/department/departmentEditUI";
    }

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param shortName
     * @param grade
     * @param pid
     * @param primaryPerson
     * @param deputyPerson
     * @param remark
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestParam(name = "id") String id,
                         @RequestParam(name = "name") String name,
                         @RequestParam(name = "shortName", required = false) String shortName,
                         @RequestParam(name = "grade", required = false) Integer grade,
                         @RequestParam(name = "pid") String pid,
                         @RequestParam(name = "primaryPerson", required = false) String primaryPerson,
                         @RequestParam(name = "deputyPerson", required = false) String deputyPerson,
                         @RequestParam(name = "remark", required = false) String remark) {
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setId(id);
        sysDepartment.setName(name);
        sysDepartment.setShortName(shortName);
        sysDepartment.setGrade(grade);
        if (StringUtils.isEmpty(pid)) {
            pid = "0";
        }
        sysDepartment.setPid(pid);
        sysDepartment.setPrimaryPerson(primaryPerson);
        sysDepartment.setDeputyPerson(deputyPerson);
        sysDepartment.setRemark(remark);
        sysDepartment.setCreateTime(new Date());
        departmentService.updateById(sysDepartment);
        return ResultUtil.success();
    }

}