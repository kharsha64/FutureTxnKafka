package com.abn.futuretxn.reader;

import com.abn.futuretxn.producer.FutureTxnProducer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.verify;

@SpringBootTest
@Profile("local")
public class TxnReaderTest {

    @Mock
    private FutureTxnProducer producer;

    @Mock
    private ResourceLoader loader;

    @Mock
    private Resource resource;

    @Mock
    private InputStream inputStream;

    @Mock
    private BufferedReader bufferedReader;

    @Mock
    private TxnReader reader;

    @Test
    @Disabled
    void readTransactionFile() throws IOException {
        //String mockLine = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
        //   InputStream is = new ByteArrayInputStream(mockLine.getBytes());
        // when(loader.getResource(Mockito.anyString())).thenReturn(resource);
        //InputStream inputStream = resource.getInputStream();
        //   when(resource.getInputStream()).thenReturn(inputStream);
        //InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        //when(new InputStreamReader(Mockito.any())).thenReturn(inputStreamReader);
        //when(new InputStreamReader(Mockito.any())).thenReturn(inputStreamReader);
        //when(new BufferedReader(inputStreamReader)).thenReturn(bufferedReader);
        //

        //when(bufferedReader.readLine()).thenReturn(mockLine);
        //doNothing().when(producer).sendTxEvents(Mockito.any());

        //reader.readTransactionFile();

        verify(reader).readTransactionFile();
    }
}