package com.hcsp.wxshop.service;

import static com.hcsp.wxshop.service.TelVerificationServiceTest.VALID_PARAMETER;
import static com.hcsp.wxshop.service.TelVerificationServiceTest.VALID_PARAMETER_CODE;
import static java.net.HttpURLConnection.HTTP_OK;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.hcsp.wxshop.entity.LoginResponse;
import com.hcsp.wxshop.generate.User;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

public class AbstractIntegrationTest {
    @Autowired
    Environment environment;

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    @BeforeEach
    public void initDatabase() {
        // 在每个测试开始前，执行一次flyway:clean flyway:migrate
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(databaseUrl, databaseUsername, databasePassword);
        Flyway flyway = new Flyway(conf);
        flyway.clean();
        flyway.migrate();
    }

    public static ObjectMapper objectMapper = new ObjectMapper();

    public String getUrl(String apiName) {
        // 获取集成测试的端口号
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    public UserLoginResponse loginAndGetCookie() throws JsonProcessingException {
        // 最开始默认情况下，访问/api/v1/status 处于未登录状态，
        String statusResponse = doHttpRequest("/api/v1/status", "GET", null, null).body;
        LoginResponse statusResponseData = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertFalse(statusResponseData.isLogin());

        // 发送验证码
        int respondseCode = doHttpRequest("/api/v1/code", "POST", VALID_PARAMETER, null).code;
        Assertions.assertEquals(HTTP_OK, respondseCode);

        // 带着验证码进行登录，得到cookie
        HttpResponse loginResponse = doHttpRequest("/api/v1/login", "POST", VALID_PARAMETER_CODE, null);
        List<String> setCookie = loginResponse.headers.get("Set-Cookie");
        String cookie = getSeesionIdFromSetCookie(setCookie.stream().filter(c -> c.contains("JSESSIONID"))
                .findFirst()
                .get());

        statusResponse = doHttpRequest("/api/v1/status", "GET", null, cookie).body;
        statusResponseData = objectMapper.readValue(statusResponse, LoginResponse.class);

        return new UserLoginResponse(cookie, statusResponseData.getUser());
    }

    protected String getSeesionIdFromSetCookie(String setCookie) {
        // 当前手工处理Cookie的方式非常原始，是因为用的库不支持，HttpClient有更完善的Cookie支持，不需要手动处理
        // JSESSIONID=61e6bc98-20a1-4cfb-a262-8e3f75b67ee0; Path=/; HttpOnly; SameSite=lax -> JSESSIONID=61e6bc98-20a1-4cfb-a262-8e3f75b67ee0
        int semiColonIndex = setCookie.indexOf(";");
        return setCookie.substring(0, semiColonIndex);
    }

    public static class UserLoginResponse {
        String cookie;
        User user;

        public UserLoginResponse(String cookie, User user) {
            this.cookie = cookie;
            this.user = user;
        }
    }

    public static class HttpResponse {
        int code;
        String body;
        Map<String, List<String>> headers;

        HttpResponse(int code, String body, Map<String, List<String>> headers) {
            this.code = code;
            this.body = body;
            this.headers = headers;
        }

        HttpResponse assertOkStatusCode() {
            Assertions.assertTrue(code >= 200 && code < 300);
            return this;
        }

        public <T> T asJsonObject(TypeReference<T> typeReference) throws JsonProcessingException {

            T result = objectMapper.readValue(body, typeReference);
            return result;
        }
    }

    private HttpRequest createRequest(String url, String method) {
        if ("PATCH".equalsIgnoreCase(method)) {
            HttpRequest request = new HttpRequest(url, "POST");
            request.header("X-HTTP-Method-Override", "PATCH");
            return request;
        } else {
            return new HttpRequest(url, method);
        }
    }

    public HttpResponse doHttpRequest(String apiName, String httpMethod, Object requestBody,
                                      String cookie)
            throws JsonProcessingException {
        HttpRequest request = createRequest(getUrl(apiName), httpMethod);

        if (cookie != null) {
            request.header("Cookie", cookie);
        }
        request.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);
        if (requestBody != null) {
            request.send(objectMapper.writeValueAsString(requestBody));
        }

        return new HttpResponse(request.code(), request.body(), request.headers());
    }
}
