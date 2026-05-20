package com.primebiz.inventory.repository;

import com.primebiz.inventory.model.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLogRepository extends JpaRepository<StockLog, Long> {
}
