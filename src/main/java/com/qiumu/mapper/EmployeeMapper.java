package com.qiumu.mapper;

import com.qiumu.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86134
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-08-17 11:17:47
* @Entity com.qiumu.pojo.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}




