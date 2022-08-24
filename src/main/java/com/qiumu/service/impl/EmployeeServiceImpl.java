package com.qiumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.pojo.Employee;
import com.qiumu.service.EmployeeService;
import com.qiumu.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author 86134
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-08-17 11:17:47
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




