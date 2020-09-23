package com.hy.gmall.search.demo;

import io.searchbox.client.JestClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class SearchTest {

    @Autowired
    private JestClient jestClient;

    @Test
    public void search(){
        try {
            jestClient.execute(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
