package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezentwix.teamcostco.dto.customer.CustomerDTO;
import com.ezentwix.teamcostco.dto.customer.OrderShippingAddressDTO;
import com.ezentwix.teamcostco.dto.product.ProductDTO;
import com.ezentwix.teamcostco.dto.sales.SalesDTO;
import com.ezentwix.teamcostco.dto.sales.SalesItemsDTO;
import com.ezentwix.teamcostco.dto.sales.SalesRequestDTO;
import com.ezentwix.teamcostco.repository.SalesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderShippingAddressService orderShippingAddressService;

    @Transactional
    public boolean createSales(SalesRequestDTO salesRequest) {
        // 세션에서 고객 정보 가져오기
        CustomerDTO customer = customerService.getCustomerFromSession();

        // 고객 정보가 null이 아닌 경우에만 실행
        if (customer != null) {

            // Sales 객체 생성 및 초기화
            SalesDTO sales = new SalesDTO();
            sales.setCustomer_id(customer.getCustomer_id());
            sales.setPayments_type(salesRequest.getPayment_method());
            sales.setSales_status("ordered");

            // Sales 테이블에 삽입 및 sales_id 반환
            Long sales_id = salesRepository.insertSales(sales);
            if (sales_id == null) {
                throw new RuntimeException("Failed to create sales record.");
            }

            // 판매 항목 처리
            salesRequest.getItems().forEach((item) -> {
                SalesItemsDTO salesItem = new SalesItemsDTO();

                // 제품 정보 가져오기
                ProductDTO product = productService.getByProductCode(item.getProduct_code());
                if (product == null) {
                    throw new RuntimeException("Product not found for code: " + item.getProduct_code());
                }

                // SalesItems 객체 초기화
                salesItem.setSales_id(sales_id);
                salesItem.setProduct_code(item.getProduct_code());
                salesItem.setQty(item.getQty());
                salesItem.setUnit_price(product.getDiscountedPrice());
                salesItem.setTotal_price(item.getQty() * product.getDiscountedPrice());

                // SalesItems 테이블에 삽입
                if (!salesRepository.insertSalesItem(salesItem)) {
                    throw new RuntimeException("Failed to insert sales item for product code: " + item.getProduct_code());
                }
            });

            // 배송 주소 정보 처리
            OrderShippingAddressDTO orderShippingAddressDTO = new OrderShippingAddressDTO();
            orderShippingAddressDTO.setCustomer_id(customer.getCustomer_id());
            orderShippingAddressDTO.setSales_id(sales_id);
            orderShippingAddressDTO.setAddress_alias(salesRequest.getShipping_address().getAddress_alias());
            orderShippingAddressDTO.setDetail_address(salesRequest.getShipping_address().getDetail_address());
            orderShippingAddressDTO.setRoad_name_address(salesRequest.getShipping_address().getRoad_name_address());
            orderShippingAddressDTO.setLot_number_address(salesRequest.getShipping_address().getLot_number_address());
            orderShippingAddressDTO.setPostal_code(salesRequest.getShipping_address().getPostal_code());
            orderShippingAddressDTO.setRecipient_name(salesRequest.getShipping_address().getRecipient_name());
            orderShippingAddressDTO.setRecipient_phone_number(salesRequest.getShipping_address().getRecipient_phone_number());
            orderShippingAddressDTO.setSender_name(salesRequest.getShipping_address().getSender_name());
            orderShippingAddressDTO.setSender_phone_number(salesRequest.getShipping_address().getSender_phone_number());

            // OrderShippingAddress 테이블에 삽입
            if (!orderShippingAddressService.insertOrderShippingAddress(orderShippingAddressDTO)) {
                throw new RuntimeException("Failed to insert order shipping address.");
            }

            return true;
        } else {
            throw new RuntimeException("Customer not found in session.");
        }
    }
    /**
     * 판매 조회 by ID
     */
    public SalesDTO getSalesById(Long salesId) {
        return salesRepository.selectSalesById(salesId);
    }

    /**
     * 모든 판매 조회
     */
    public List<SalesDTO> getAllSales() {
        return salesRepository.selectAllSales();
    }

    /**
     * 페이징 처리된 판매 조회
     */
    public List<SalesDTO> getSalesWithPagination(int page, int size) {
        int offset = (page - 1) * size;
        return salesRepository.selectSalesWithPagination(offset, size);
    }

    /**
     * 판매 수정
     */
    @Transactional
    public void updateSales(SalesDTO sales) {
        // Sales 수정
        salesRepository.updateSales(sales);

        // SalesItems 수정 로직
        List<SalesItemsDTO> items = sales.getSales_items();
        if (items != null) {
            for (SalesItemsDTO item : items) {
                if (item.getSales_item_id() == null) {
                    // 새로운 SalesItem 추가
                    item.setSales_id(sales.getSales_id());
                    item.setTotal_price(item.getUnit_price() * item.getQty());
                    salesRepository.insertSalesItem(item);
                } else {
                    // 기존 SalesItem 수정
                    item.setTotal_price(item.getUnit_price() * item.getQty());
                    salesRepository.updateSalesItem(item);
                }
            }
        }
    }

    /**
     * 판매 삭제 (소프트 딜리트: 상태 업데이트)
     */
    @Transactional
    public void cancelSales(Long salesId) {
        // 판매 상태를 'CANCELED'로 업데이트
        salesRepository.updateSalesStatus(salesId, "CANCELED");
    }

    /**
     * 활성화된 모든 판매 조회
     */
    public List<SalesDTO> getActiveSales() {
        return salesRepository.selectActiveSales();
    }

    /**
     * 특정 상태의 판매 조회
     */
    public List<SalesDTO> getSalesByStatus(String salesStatus) {
        return salesRepository.selectSalesByStatus(salesStatus);
    }

    /**
     * SalesItem 단건 조회
     */
    public SalesItemsDTO getSalesItemById(Long salesItemId) {
        return salesRepository.selectSalesItemById(salesItemId);
    }

    /**
     * SalesItem 등록
     */
    public void createSalesItem(SalesItemsDTO salesItem) {
        // total_price 계산
        salesItem.setTotal_price(salesItem.getUnit_price() * salesItem.getQty());
        salesRepository.insertSalesItem(salesItem);
    }

    /**
     * SalesItem 수정
     */
    public void updateSalesItem(SalesItemsDTO salesItem) {
        // total_price 계산
        salesItem.setTotal_price(salesItem.getUnit_price() * salesItem.getQty());
        salesRepository.updateSalesItem(salesItem);
    }

    /**
     * SalesItem 삭제
     */
    public void deleteSalesItem(Long salesItemId) {
        salesRepository.deleteSalesItem(salesItemId);
    }
}
