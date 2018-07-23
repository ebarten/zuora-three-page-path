package com.zuora.coddingchallenge.threepagepath.data;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.zuora.coddingchallenge.threepagepath.Constants.PAGE_PATH_SEPARATOR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserPathsCsvProcessorTest {
    @Test
    public void whenSingleUserVisitsMoreThanThreePagesThenUserPathIsAssembledCorrectly() {
        UserPathsCsvProcessor csvProcessor = new UserPathsCsvProcessor();
        csvProcessor.process("U1, /");
        csvProcessor.process("U1, login");
        csvProcessor.process("U1, subscriber");
        csvProcessor.process("U2, /");
        csvProcessor.process("U2, login");
        csvProcessor.process("U2, subscriber");
        csvProcessor.process("U1, /");

        Map<String, String> actualProcessedData = csvProcessor.getProcessedData();
        Map<String, String> expectedProcessedData = new HashMap<>();
        String user1PagePath = "/" + PAGE_PATH_SEPARATOR +
                "login" + PAGE_PATH_SEPARATOR +
                "subscriber" + PAGE_PATH_SEPARATOR +
                "/";
        expectedProcessedData.put("U1", user1PagePath);
        String user2PagePath = "/" + PAGE_PATH_SEPARATOR +
                "login" + PAGE_PATH_SEPARATOR +
                "subscriber";
        expectedProcessedData.put("U2", user2PagePath);


        assertThat("Unexpected used paths!", actualProcessedData, equalTo(expectedProcessedData));
    }


}
