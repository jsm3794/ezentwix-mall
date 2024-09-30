package com.ezentwix.teamcostco.controller.customer.sales;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ezentwix.teamcostco.dto.customer.OrderShippingAddressDTO;
import com.ezentwix.teamcostco.dto.sales.SalesDTO;
import com.ezentwix.teamcostco.service.OrderShippingAddressService;
import com.ezentwix.teamcostco.service.SalesDetailService;
import com.ezentwix.teamcostco.service.SalesService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class SalesDetailController {
    private final SalesService salesService;
    private final SalesDetailService salesDetailService;
    private final OrderShippingAddressService orderShippingAddressService;

    @GetMapping("/customer/sales/detail/{sales_id}")
    public String getMethodName(@PathVariable(value="sales_id") Long sales_id, Model model) {
        salesDetailService.configureModel(model);

        OrderShippingAddressDTO orderShippingAddressDTO = orderShippingAddressService.selectOrderShippingAddressBySalesId(sales_id);
        SalesDTO item = salesService.getSalesById(sales_id);
        model.addAttribute("item", item);
        model.addAttribute("shipping_data", orderShippingAddressDTO);


        return "index";
    }
    
}
