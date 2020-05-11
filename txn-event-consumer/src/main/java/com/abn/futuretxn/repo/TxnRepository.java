package com.abn.futuretxn.repo;

import com.abn.futuretxn.entity.TxnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TxnRepository extends JpaRepository<TxnEntity, Long> {
    List<TxnEntity> findByExchangeCodeAndProductGroupCodeAndSymbol(String exchangeCode, String productGroupCode, String symbol);
}
