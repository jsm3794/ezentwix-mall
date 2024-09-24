package com.ezentwix.teamcostco.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.customer.CustomerDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
    private final SqlSessionTemplate sql;

    public CustomerDTO getByIdAndPw(String id, String pw) {
        Map<String, String> params = Map.of("id", id, "pw", pw);
        return sql.selectOne("Employees.getByIdAndPw", params);
    }

    public List<CustomerDTO> getEmpList() {
        return sql.selectList("Employees.getAll");
    }

    public CustomerDTO getEmp(Integer emp_id) {
        return sql.selectOne("Employees.getById", emp_id);
    }
}
