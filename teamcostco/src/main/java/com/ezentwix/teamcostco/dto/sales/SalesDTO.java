package com.ezentwix.teamcostco.dto.sales;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SalesDTO {
    private Long sales_id;
    private String customer_id;
    private LocalDateTime sale_date;
    private LocalDateTime create_date;
    private String payments_type;    // 결제 유형 (예: 신용카드, 페이팔 등)
    private String sales_status;     // 판매 상태 (예: 대기, 완료, 취소 등)

    private List<SalesItemsDTO> sales_items; // 판매 항목 리스트
}
