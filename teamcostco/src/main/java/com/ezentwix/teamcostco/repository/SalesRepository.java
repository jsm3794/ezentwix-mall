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

    /**
     * 판매 등록
     */
    public Long insertSales(SalesDTO sales) {
        sqlSession.insert("Sales.insertSales", sales);
        return sales.getSales_id();
    }
    

    /**
     * 판매 조회 by ID
     */
    public SalesDTO selectSalesById(Long salesId) {
        return sqlSession.selectOne("Sales.selectSalesById", salesId);
    }

    /**
     * 모든 판매 조회
     */
    public List<SalesDTO> selectAllSales() {
        return sqlSession.selectList("Sales.selectAllSales");
    }

    /**
     * 페이징 처리된 판매 조회
     */
    public List<SalesDTO> selectSalesWithPagination(int offset, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        return sqlSession.selectList("Sales.selectSalesWithPagination", params);
    }

    /**
     * 판매 수정
     */
    public void updateSales(SalesDTO sales) {
        sqlSession.update("Sales.updateSales", sales);
    }

    /**
     * 판매 소프트 딜리트 (상태 업데이트)
     */
    public void updateSalesStatus(Long salesId, String salesStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("salesId", salesId);
        params.put("salesStatus", salesStatus);
        sqlSession.update("Sales.updateSalesStatus", params);
    }

    /**
     * 판매 소프트 딜리트: DELETE 엔드포인트 대체
     */
    public void deleteSales(Long salesId) {
        // 실제 삭제 대신 소프트 딜리트를 수행
        updateSalesStatus(salesId, "CANCELED");
    }

    /**
     * 활성화된 판매 조회
     */
    public List<SalesDTO> selectActiveSales() {
        return sqlSession.selectList("Sales.selectActiveSales");
    }

    /**
     * 특정 상태의 판매 조회
     */
    public List<SalesDTO> selectSalesByStatus(String salesStatus) {
        return sqlSession.selectList("Sales.selectSalesByStatus", salesStatus);
    }

    // SalesItem CRUD

    /**
     * 판매 항목 등록
     */
    public boolean insertSalesItem(SalesItemsDTO salesItem) {
        int row = sqlSession.insert("Sales.insertSalesItem", salesItem);
        return row > 0;
    }

    /**
     * 판매 항목 조회 by ID
     */
    public SalesItemsDTO selectSalesItemById(Long salesItemId) {
        return sqlSession.selectOne("Sales.selectSalesItemById", salesItemId);
    }

    /**
     * 모든 판매 항목 조회
     */
    public List<SalesItemsDTO> selectAllSalesItems() {
        return sqlSession.selectList("Sales.selectAllSalesItems");
    }

    /**
     * 판매 ID로 판매 항목 조회
     */
    public List<SalesItemsDTO> selectSalesItemsBySalesId(Long salesId) {
        return sqlSession.selectList("Sales.selectSalesItemsBySalesId", salesId);
    }

    /**
     * 판매 항목 수정
     */
    public void updateSalesItem(SalesItemsDTO salesItem) {
        sqlSession.update("Sales.updateSalesItem", salesItem);
    }

    /**
     * 판매 항목 삭제
     */
    public void deleteSalesItem(Long salesItemId) {
        sqlSession.delete("Sales.deleteSalesItem", salesItemId);
    }
}
