package com.hcsp.wxshop.service;

import static com.hcsp.wxshop.service.TelVerificationServiceTest.VALID_PARAMETER;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.kevinsawicki.http.HttpRequest;
import com.hcsp.wxshop.WxshopApplication;
import com.hcsp.wxshop.entity.LoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"})
class AuthIntegrationTest extends AbstractIntegrationTest {
    @Test
    void loginLogoutTest() throws JsonProcessingException {
        String sessionid = loginAndGetCookie().cookie;

        // 带着Cookie进行访问 /api/status 应该处于登录状态
        String statusResponse = doHttpRequest("/api/status", true, null, sessionid).body;
        LoginResponse response = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertTrue(response.isLogin());
        Assertions.assertEquals(VALID_PARAMETER.getTel(), response.getUser().getTel());

        // 调用/api/logout
        // 注销登录，注意注销登录也需要到Cookie
        doHttpRequest("/api/logout", false, null, sessionid);

        // 再次带着Cookie访问/api/status 恢复成为未登录状态
        statusResponse = doHttpRequest("/api/status", true, null, sessionid).body;
        response = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertFalse(response.isLogin());
    }

    @Test
    void returnHttpOKWhenParameterIsCorrect() throws JsonProcessingException {
        int respondseCode = HttpRequest.post(getUrl("/api/code"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .send(objectMapper.writeValueAsString(VALID_PARAMETER))
            .code();

        Assertions.assertEquals(HTTP_OK, respondseCode);
    }

    @Test
    void returnHttpBadRequestWhenParameterIsCorrect() throws JsonProcessingException {
        int respondseCode = HttpRequest.post(getUrl("/api/code"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .send(objectMapper.writeValueAsString(TelVerificationServiceTest.EMPTY_TEL))
            .code();

        Assertions.assertEquals(HTTP_BAD_REQUEST, respondseCode);
    }

    @Test
    public void returnUnauthorizedIfNotLogin() {
        int respondseCode = HttpRequest.post(getUrl("/api/any"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .code();

        Assertions.assertEquals(HTTP_UNAUTHORIZED, respondseCode);
    }
}
