package com.fiap.challenge.production.infra.database.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fiap.challenge.production.infra.database.entity.ProductionEntity;

public interface ProductionRepository extends JpaRepository<ProductionEntity, UUID> {
	ProductionEntity findByOrderNumber(Long orderNumber);
	
	@Query(value = "SELECT * from public.production p where p.order_status in ('EM_PREPARACAO', 'PRONTO')" , nativeQuery = true)
	public List<ProductionEntity> findOrdersPrepareAndReady();
	
	
}
