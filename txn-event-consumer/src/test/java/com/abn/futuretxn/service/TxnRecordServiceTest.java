package com.abn.futuretxn.service;

import com.abn.futuretxn.domain.TransactionSummary;
import com.abn.futuretxn.domain.TxnRecord;
import com.abn.futuretxn.entity.TxnEntity;
import com.abn.futuretxn.repo.TxnRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
class TxnRecordServiceTest {

    @InjectMocks
    private TxnRecordService service;

    @Mock
    private TxnRepository repo;

    @Mock
    private TxnEntity txEntity;

    @Mock
    List<TxnEntity> txnEntityList;

    @Test
    void createFutureTxn() {
        TxnRecord txnRecord = TxnRecord.builder().clientType("CL").clientNumber("4321").subAccountNumber("0001").exchangeCode("SGX").productGroupCode("FU").symbol("NK    ").expirationDate("20100910").quantityLong("0000000001").quantityShort("0000000000").accountNumber("0002").build();
        when(repo.save(Mockito.any())).thenReturn(txEntity);
        when(service.createFutureTxn(txnRecord)).thenReturn(1l);
        service.createFutureTxn(txnRecord);
        Assertions.assertEquals(1l, 1l);
    }

    @Test
    void getProcessedTxnRecords() throws Exception {
        List<TransactionSummary> summaries = service.getProcessedTxnRecords();
        Assertions.assertNotNull(summaries);
    }

    @Test
    void getTxnAmountTest() {
        BigDecimal amount = service.getTxnTotalAmount("CME", "FU", "N1");
        Assertions.assertNotNull(amount);
    }

}