package com.ezentwix.teamcostco.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.pagination.PaginationRepository;
import com.ezentwix.teamcostco.pagination.PaginationResult;
import com.ezentwix.teamcostco.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService implements PageMetadataProvider {
    private final EmployeeRepository employeeRepository;
    private final PaginationRepository paginationRepository;

    public PaginationResult<CustomerDTO> getPage(String query, Integer page, Integer limit,
            Map<String, Object> params) {
        return paginationRepository.getPage(
                query,
                "Employees.getAll",
                PageRequest.of(page, limit),
                params,
                CustomerDTO.class);
    }

    public List<CustomerDTO> getEmpList() {
        return employeeRepository.getEmpList();
    }

    @Override
    public String getUri() {
        return "employee/emp_list";
    }

    @Override
    public String getPageTitle() {
        return "직원관리";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/employee.css");
    }

    @Override
    public List<String> getJsFiles() {
        return List.of(
                "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js", // Bootstrap JS 추가
                "/js/contents/employee.js" // 기존의 JS 파일
        );
    }

}