package com.fiap.challenge.production.infra.database.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.challenge.production.infra.database.entity.ProductionEntity;

public interface ProductionRepository extends JpaRepository<ProductionEntity, UUID> {
	ProductionEntity findByOrderNumber(Long orderNumber);
	
}
