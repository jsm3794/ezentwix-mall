package com.ezentwix.teamcostco.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.sales.SalesDTO;
import com.ezentwix.teamcostco.dto.sales.SalesItemsDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SalesRepository {
    private final SqlSessionTemplate sqlSession;

    // Namespace를 상수로 정의하여 일관성 유지
    private static final String NAMESPACE = "Sales.";

    /**
     * 판매 등록
     * 
     * @param sales 판매 데이터
     * @return 생성된 sales_id
     */
    public Long insertSales(SalesDTO sales) {
        sqlSession.insert(NAMESPACE + "insertSales", sales);
        return sales.getSales_id();
    }

    /**
     * 판매 조회 by ID
     * 
     * @param salesId 판매 ID
     * @return SalesDTO 객체
     */
    public SalesDTO selectSalesById(Long salesId) {
        return sqlSession.selectOne(NAMESPACE + "selectSalesById", salesId);
    }

    /**
     * 모든 판매 조회
     * 
     * @return SalesDTO 리스트
     */
    public List<SalesDTO> selectAllSales() {
        return sqlSession.selectList(NAMESPACE + "selectAllSales");
    }

    /**
     * social_id 로 판매 조회
     * 
     * @return SalesDTO 리스트
     */
    public List<SalesDTO> selectSalesBySocialId(String social_id) {
        List<SalesDTO> list = sqlSession.selectList(NAMESPACE + "selectSalesBySocialId", social_id);
        System.out.println("@@@@@");
        System.out.println(social_id);
        System.out.println(list);
        System.out.println("@@@@@");
        return list;
    }

    /**
     * 페이징 처리된 판매 조회
     * 
     * @param offset 시작 위치
     * @param limit  조회할 데이터 수
     * @return SalesDTO 리스트
     */
    public List<SalesDTO> selectSalesWithPagination(int offset, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        return sqlSession.selectList(NAMESPACE + "selectSalesWithPagination", params);
    }

    /**
     * 판매 수정
     * 
     * @param sales 수정할 판매 데이터
     */
    public void updateSales(SalesDTO sales) {
        sqlSession.update(NAMESPACE + "updateSales", sales);
    }

    /**
     * 판매 소프트 딜리트 (상태 업데이트)
     * 
     * @param salesId     판매 ID
     * @param salesStatus 새로운 판매 상태
     */
    public void updateSalesStatus(Long salesId, String salesStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("salesId", salesId);
        params.put("salesStatus", salesStatus);
        sqlSession.update(NAMESPACE + "updateSalesStatus", params);
    }

    /**
     * 판매 소프트 딜리트: DELETE 엔드포인트 대체
     * 
     * @param salesId 판매 ID
     */
    public void deleteSales(Long salesId) {
        // 실제 삭제 대신 소프트 딜리트를 수행
        updateSalesStatus(salesId, "CANCELED");
    }

    /**
     * 활성화된 판매 조회
     * 
     * @return 활성화된 SalesDTO 리스트
     */
    public List<SalesDTO> selectActiveSales() {
        return sqlSession.selectList(NAMESPACE + "selectActiveSales");
    }

    /**
     * 특정 상태의 판매 조회
     * 
     * @param salesStatus 판매 상태
     * @return SalesDTO 리스트
     */
    public List<SalesDTO> selectSalesByStatus(String salesStatus) {
        return sqlSession.selectList(NAMESPACE + "selectSalesByStatus", salesStatus);
    }

    public boolean updateDeliveryFee(Long sales_id, Long delivery_fee){
        int row = sqlSession.update(NAMESPACE + "updateDeliveryFee", Map.of("sales_id", sales_id, "delivery_fee", delivery_fee));
        return row > 0;
    }
    // SalesItem CRUD

    /**
     * 판매 항목 등록
     * 
     * @param salesItem 판매 항목 데이터
     * @return 등록 성공 여부
     */
    public boolean insertSalesItem(SalesItemsDTO salesItem) {
        int row = sqlSession.insert(NAMESPACE + "insertSalesItem", salesItem);
        return row > 0;
    }

    /**
     * 판매 항목 조회 by ID
     * 
     * @param salesItemId 판매 항목 ID
     * @return SalesItemsDTO 객체
     */
    public SalesItemsDTO selectSalesItemById(Long salesItemId) {
        return sqlSession.selectOne(NAMESPACE + "selectSalesItemById", salesItemId);
    }

    /**
     * 모든 판매 항목 조회
     * 
     * @return SalesItemsDTO 리스트
     */
    public List<SalesItemsDTO> selectAllSalesItems() {
        return sqlSession.selectList(NAMESPACE + "selectAllSalesItems");
    }

    /**
     * 판매 ID로 판매 항목 조회
     * 
     * @param salesId 판매 ID
     * @return SalesItemsDTO 리스트
     */
    public List<SalesItemsDTO> selectSalesItemsBySalesId(Long salesId) {
        return sqlSession.selectList(NAMESPACE + "selectSalesItemsBySalesId", salesId);
    }

    /**
     * 판매 항목 수정
     * 
     * @param salesItem 수정할 판매 항목 데이터
     */
    public void updateSalesItem(SalesItemsDTO salesItem) {
        sqlSession.update(NAMESPACE + "updateSalesItem", salesItem);
    }

    /**
     * 판매 항목 삭제
     * 
     * @param salesItemId 판매 항목 ID
     */
    public void deleteSalesItem(Long salesItemId) {
        sqlSession.delete(NAMESPACE + "deleteSalesItem", salesItemId);
    }
}
