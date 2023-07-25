package com.qiumu.controll;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiumu.common.R;
import com.qiumu.pojo.Employee;
import com.qiumu.pojo.PageBean;
import com.qiumu.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<Employee>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());

        Employee one = employeeService.getOne(queryWrapper);
        if (one == null) {
            return R.error("登录失败");
        }
        if (!password.equals(one.getPassword())) {
            return R.error("登录失败");
        }
        if (one.getStatus() == 0) {
            return R.error("登录失败");
        }

        httpServletRequest.getSession().setAttribute("empId", one.getId());

        return R.success(one);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("empId");

        return R.success("ok");
    }

    @PostMapping
    public R<String> add(@RequestBody Employee employee) {

/*  在MyMetaObjectHandler处理
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser((Long) request.getSession().getAttribute("empId"));
        employee.setUpdateUser((Long) request.getSession().getAttribute("empId"));
*/
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);

        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<PageBean<Employee>> list(Integer page, Integer pageSize, String name) {

        Page<Employee> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), Employee::getName, name);

        long total = employeeService.count(queryWrapper);
        List<Employee> records = employeeService.page(pages, queryWrapper).getRecords();

        PageBean<Employee> pageBean = new PageBean<>(total, records);

        return R.success(pageBean);
    }

    @GetMapping("/{id}")
    public R<Employee> getOneUpdate(@PathVariable String id) {
        Employee employee = employeeService.getById(id);

        return R.success(employee);
    }

    @PutMapping()
    public R<String> updateStatus(@RequestBody Employee employee) {
        boolean update = employeeService.updateById(employee);
        if (!update) {
            return R.error("修改失败!");
        }

        return R.success("修改成功");
    }

}
