package com.abn.futuretxn.reader;

import com.abn.futuretxn.domain.TxnRecord;
import com.abn.futuretxn.producer.FutureTxnProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@Slf4j
public class TxnReader implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Autowired
    private FutureTxnProducer producer;

     public void readTransactionFile() throws IOException {
        Resource resource = resourceLoader.getResource("file:Feed.txt");
        InputStream inputStream = resource.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;

            TxnRecord record = new TxnRecord();
            record.setClientType(line.substring(3,7).trim());
            record.setClientNumber(line.substring(7,11).trim());
            record.setAccountNumber(line.substring(11,15).trim());
            record.setSubAccountNumber(line.substring(15,19).trim());
            record.setExchangeCode(line.substring(27,31).trim());
            record.setProductGroupCode(line.substring(25,27).trim());
            record.setSymbol(line.substring(31,37).trim());
            record.setExpirationDate(line.substring(37, 45).trim());
            record.setQuantityLong(line.substring(52,62).trim());
            record.setQuantityShort(line.substring(63,73).trim());
            log.info("Message :{}", record);

            producer.sendTxEvents(record);

        }
        reader.close();
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
