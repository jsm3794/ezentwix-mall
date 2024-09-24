package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeDetailService implements PageMetadataProvider {

    private final EmployeeRepository employeeRepository;

    public CustomerDTO getEmp(Integer emp_id) {
        return employeeRepository.getEmp(emp_id);
    }

    @Override
    public String getUri() {
        return "employee/emp_detail";
    }

    @Override
    public String getPageTitle() {
        return "사원정보";
    }

}
