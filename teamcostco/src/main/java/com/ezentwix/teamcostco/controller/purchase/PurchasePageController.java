package com.ezentwix.teamcostco.controller.purchase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezentwix.teamcostco.dto.product.ProductDTO;
import com.ezentwix.teamcostco.dto.product.ProductPurchaseDTO;
import com.ezentwix.teamcostco.service.CustomerService;
import com.ezentwix.teamcostco.service.ProductService;
import com.ezentwix.teamcostco.service.PurchaseService;
import com.ezentwix.teamcostco.service.ShippingAddressService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PurchasePageController {
    private final PurchaseService purchaseService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;

    @GetMapping("/purchase")
    public String getMethodName(Model model,
            @RequestParam(value = "product_list", required = true) String product_list) {
        purchaseService.configureModel(model);

        List<ProductPurchaseDTO> productList = new ArrayList<>();

        if (product_list == null || product_list.trim().isEmpty()) {
            throw new IllegalArgumentException("product_list 파라미터가 비어 있습니다.");
        }

        String[] items = product_list.split(",");
        for (String item : items) {
            String[] parts = item.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("잘못된 product_list 형식: " + item);
            }

            Long productCode = Long.parseLong(parts[0].trim());

            int quantity;
            try {
                quantity = Integer.parseInt(parts[1].trim());
                if (quantity < 1) {
                    throw new IllegalArgumentException("수량은 1 이상이어야 합니다: " + item);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("수량이 유효한 숫자가 아닙니다: " + parts[1]);
            }

            ProductPurchaseDTO productPurchaseDTO = new ProductPurchaseDTO();
            productPurchaseDTO.setProduct(productService.getByProductCode(productCode));
            productPurchaseDTO.setCount(quantity);

            productList.add(productPurchaseDTO);

        }
        // 총 가격 및 할인 계산

        int totalQuantity = productList.stream()
                .mapToInt(p -> p.getCount())
                .sum();

        int totalPrice = productList.stream()
                .mapToInt(p -> p.getProduct().getSelling_price() * p.getCount())
                .sum();

        int totalDiscount = productList.stream()
                .mapToInt(p -> (p.getProduct().getSelling_price() * p.getCount())
                        - (p.getProduct().getDiscountedPrice()) * p.getCount())
                .sum();

        int finalPrice = totalPrice - totalDiscount;

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("totalQuantity", totalQuantity);
        
        model.addAttribute("finalPrice", finalPrice);

        model.addAttribute("default_shipping", shippingAddressService.getDefaultAddressBySocialId());
        model.addAttribute("user", customerService.getCustomerFromSession());
        model.addAttribute("items", productList);
        return "index";
    }
}
