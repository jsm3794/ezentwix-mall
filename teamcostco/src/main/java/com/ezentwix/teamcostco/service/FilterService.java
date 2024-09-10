package com.ezentwix.teamcostco.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.dto.filter.EmployeeFilterDTO;
import com.ezentwix.teamcostco.dto.filter.FilterDTO;
import com.ezentwix.teamcostco.dto.filter.InventoryFilterDTO;
import com.ezentwix.teamcostco.dto.filter.OrderRequestFilterDTO;
import com.ezentwix.teamcostco.dto.product.ProductBrandGroupDTO;
import com.ezentwix.teamcostco.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final CategoryService categoryService;

    private enum FilterType {
        INVENTORY,
        EMPLOYEE,
        ORDERREQUEST
    }

    public List<FilterDTO> getFilter(String filterType) {
        FilterType type = FilterType.valueOf(filterType.toUpperCase());

        switch (type) {
            case INVENTORY:
                InventoryFilterDTO inventoryFilterDTO = new InventoryFilterDTO();
                inventoryFilterDTO.setFilterList(List.of(
                        new FilterDTO("상품코드", "input", "text", "product_code", "", null),
                        new FilterDTO("매입가격(최소)", "input", "number", "purchase_price_start", "", null),
                        new FilterDTO("매입가격(최대)", "input", "number", "purchase_price_end", "", null),
                        new FilterDTO("판매가격(최소)", "input", "number", "selling_price_start", "", null),
                        new FilterDTO("판매가격(최대)", "input", "number", "selling_price_end", "", null),
                        new FilterDTO("대분류", "select", "", "category_large", "", categoryService.getLarge())));
                return inventoryFilterDTO.getFilterList();

            case EMPLOYEE:
                EmployeeFilterDTO employeeFilterDTO = new EmployeeFilterDTO();
                employeeFilterDTO.setFilterList(List.of(
                        new FilterDTO("이메일", "input", "text", "emp_email", "", null),
                        new FilterDTO("연락처", "input", "text", "phone_number", "", null),
                        new FilterDTO("직책", "select", "", "job_title", "", Map.of(
                                "", "전체",
                                "관리자", "관리자",
                                "개발자", "개발자")),
                        new FilterDTO("고용일자", "input", "date", "hire_date", "", null),
                        new FilterDTO("가입일자", "input", "date", "join_date", "", null)));
                return employeeFilterDTO.getFilterList();
            case ORDERREQUEST:
                OrderRequestFilterDTO orderRequestFilterDTO = new OrderRequestFilterDTO();
                orderRequestFilterDTO.setFilterList(List.of(
                        new FilterDTO("판매처", "input", "text", "mall_name", "", null),
                        new FilterDTO("주문번호", "input", "text", "order_number", "", null),
                        new FilterDTO("주문날짜", "input", "date", "order_date", "", null),
                        new FilterDTO("상태", "select", "", "request_status", "", Map.of(
                                "", "전체",
                                "pending", "대기중",
                                "ordered", "주문됨",
                                "received", "입고중",
                                "completed", "완료됨",
                                "rejected", "거부됨",
                                "cancelled", "취소됨")),
                        new FilterDTO("직원이름", "input", "text", "emp_name", "", null)));

                return orderRequestFilterDTO.getFilterList();
            default:
                return List.of();
        }
    }
}
