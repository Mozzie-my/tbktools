package com.dm.tbktool;

import com.dm.tbktool.Service.urlHandle;
import net.minidev.json.JSONObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootTest
class TbktoolApplicationTests {


    @Autowired
    RestTemplate restTemplate;
    @Test
    void contextLoads() {

    }

}
