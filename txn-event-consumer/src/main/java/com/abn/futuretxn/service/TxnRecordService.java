package com.abn.futuretxn.service;

import com.abn.futuretxn.domain.ClientInfo;
import com.abn.futuretxn.domain.ProductInfo;
import com.abn.futuretxn.domain.TransactionSummary;
import com.abn.futuretxn.domain.TxnRecord;
import com.abn.futuretxn.entity.TxnEntity;
import com.abn.futuretxn.repo.TxnRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TxnRecordService {
    @Autowired
    private TxnRepository txnRepository;

    public Long createFutureTxn(TxnRecord record) {
        TxnEntity txnEntity = copyToTxnEntity(record);
        txnEntity = txnRepository.save(txnEntity);
        log.info("Created Txn - Txn.ID: {}", txnEntity.getId());
        return txnEntity.getId();
    }

    private TxnEntity copyToTxnEntity(TxnRecord record) {
        TxnEntity entity = new TxnEntity();
        entity.setAccountNumber(record.getAccountNumber());
        entity.setClientNumber(record.getClientNumber());
        entity.setClientType(record.getClientType());
        entity.setExchangeCode(record.getExchangeCode());
        entity.setExpirationDate(record.getExpirationDate());
        entity.setProductGroupCode(record.getProductGroupCode());
        entity.setSymbol(record.getSymbol());
        entity.setSubAccountNumber(record.getSubAccountNumber());
        entity.setQuantityLong(record.getQuantityLong());
        entity.setQuantityShort(record.getQuantityShort());
        return entity;
    }

    public List<TransactionSummary> getProcessedTxnRecords() {
        List<TxnEntity> list = txnRepository.findAll();
        List<TransactionSummary> txnSummaryList = prepareTxnSummary(list);
        return txnSummaryList;
    }

    /*private List<TxnRecord> copyAllffTmxnRecords(List<TxnEntity> txnEntityList) {
        List<TxnRecord> txnRecordList = new ArrayList<>();
        for(TxnEntity entity : txnEntityList) {
            TxnRecord record = new TxnRecord();
            record.setAccountNumber(entity.getAccountNumber());
            record.setClientNumber(entity.getClientNumber());
            record.setClientType(entity.getClientType());
            record.setExchangeCode(entity.getExchangeCode());
            record.setExpirationDate(entity.getExpirationDate());
            record.setProductGroupCode(entity.getProductGroupCode());
            record.setSymbol(entity.getSymbol());
            record.setQuantityLong(entity.getQuantityLong());
            record.setQuantityShort(entity.getQuantityShort());
        }
        return txnRecordList;
    }*/

    private List<TransactionSummary> prepareTxnSummary(List<TxnEntity> txnEntityList) {
        List<TransactionSummary> transactionSummary = new ArrayList<>();
        for(TxnEntity entity : txnEntityList) {
            TransactionSummary summary = new TransactionSummary();

            ProductInfo productInfo = new ProductInfo();
            productInfo.setExchangeCode(entity.getExchangeCode());
            productInfo.setExpDate(entity.getExpirationDate());
            productInfo.setProductGroupCode(entity.getProductGroupCode());
            productInfo.setSymbol(entity.getSymbol());

            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setAccountNumber(entity.getAccountNumber());
            clientInfo.setClientNumber(entity.getClientNumber());
            clientInfo.setClientType(entity.getClientType());
            clientInfo.setSubAccountNumber(entity.getSubAccountNumber());

            summary.setClientInfo(clientInfo);
            summary.setProductInfo(productInfo);

            summary.setNetTotal(new BigDecimal(entity.getQuantityLong()).subtract(new BigDecimal(entity.getQuantityShort())));
            transactionSummary.add(summary);
        }
        return transactionSummary;
    }

    public BigDecimal getTxnTotalAmount(String exchangeCode, String productGroupCode, String symbol) {
        List<TxnEntity> list = txnRepository.findByExchangeCodeAndProductGroupCodeAndSymbol(exchangeCode, productGroupCode, symbol);
        BigDecimal totalAmount = new BigDecimal(0);

        for (TxnEntity txnEntity : list) {
            BigDecimal netAmount = new BigDecimal(txnEntity.getQuantityLong()).subtract(new BigDecimal(txnEntity.getQuantityShort()));
            totalAmount = totalAmount.add(netAmount);
        }
        return totalAmount;
    }
}
