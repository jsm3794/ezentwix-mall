package com.ezentwix.teamcostco.controller.purchase;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezentwix.teamcostco.dto.sales.SalesDTO;
import com.ezentwix.teamcostco.dto.sales.SalesItemsDTO;
import com.ezentwix.teamcostco.dto.sales.SalesRequestDTO;
import com.ezentwix.teamcostco.dto.sales.SalesRequestDTO.ItemDto;
import com.ezentwix.teamcostco.service.CartService;
import com.ezentwix.teamcostco.service.ProductService;
import com.ezentwix.teamcostco.service.SalesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales")
public class PurchaseRestController {
    private final SalesService salesService;
    private final ProductService productService;
    private final CartService cartService;

    /**
     * 판매 등록
     */
    @PostMapping
    public ResponseEntity<String> createSales(@RequestBody SalesRequestDTO salesRequestDTO) {
        // Sales creation logic
        Long sales_id = salesService.createSales(salesRequestDTO);
        if (sales_id != null) {
            
            salesRequestDTO.getItems().forEach((item) ->{
                cartService.deleteCartByProductCode(item.getProduct_code());
            });
            
            return new ResponseEntity<>(sales_id.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("주문 실패", HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * 판매 단건 조회
     */
    @GetMapping("/{id}")
    public SalesDTO getSalesById(@PathVariable("id") Long salesId) {
        return salesService.getSalesById(salesId);
    }

    /**
     * 모든 판매 조회
     */
    @GetMapping
    public List<SalesDTO> getAllSales() {
        return salesService.getAllSales();
    }

    /**
     * 페이징 처리된 판매 조회
     */
    @GetMapping("/paged")
    public List<SalesDTO> getSalesWithPagination(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return salesService.getSalesWithPagination(page, size);
    }

    /**
     * 판매 수정
     */
    @PutMapping("/{id}")
    public void updateSales(@PathVariable("id") Long salesId, @RequestBody SalesDTO salesDTO) {
        salesDTO.setSales_id(salesId);
        salesService.updateSales(salesDTO);
    }

    /**
     * 주문 취소: 소프트 딜리트
     */
    @PutMapping("/{id}/cancel")
    public void cancelSales(@PathVariable("id") Long salesId) {
        salesService.cancelSales(salesId);
    }

    /**
     * 활성화된 모든 판매 조회
     */
    @GetMapping("/active")
    public List<SalesDTO> getActiveSales() {
        return salesService.getActiveSales();
    }

    /**
     * 특정 상태의 판매 조회
     */
    @GetMapping("/status")
    public List<SalesDTO> getSalesByStatus(@RequestParam String salesStatus) {
        return salesService.getSalesByStatus(salesStatus);
    }

    /**
     * SalesItem 단건 조회
     */
    @GetMapping("/items/{itemId}")
    public SalesItemsDTO getSalesItemById(@PathVariable("itemId") Long salesItemId) {
        return salesService.getSalesItemById(salesItemId);
    }

    /**
     * SalesItem 등록
     */
    @PostMapping("/items")
    public void createSalesItem(@RequestBody SalesItemsDTO salesItemDTO) {
        salesService.createSalesItem(salesItemDTO);
    }

    /**
     * SalesItem 수정
     */
    @PutMapping("/items/{itemId}")
    public void updateSalesItem(@PathVariable("itemId") Long salesItemId,
            @RequestBody SalesItemsDTO salesItemDTO) {
        salesItemDTO.setSales_id(salesItemId);
        salesService.updateSalesItem(salesItemDTO);
    }

    /**
     * SalesItem 삭제
     */
    @DeleteMapping("/items/{itemId}")
    public void deleteSalesItem(@PathVariable("itemId") Long salesItemId) {
        salesService.deleteSalesItem(salesItemId);
    }

    /**
     * 판매 소프트 딜리트: DELETE 엔드포인트 대체
     */
    @DeleteMapping("/{id}")
    public void deleteSales(@PathVariable("id") Long salesId) {
        // 소프트 딜리트로 변경: 상태를 'CANCELED'로 설정
        salesService.cancelSales(salesId);
    }
}
