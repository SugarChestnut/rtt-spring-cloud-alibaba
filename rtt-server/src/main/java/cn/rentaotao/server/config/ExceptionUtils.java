package cn.rentaotao.server.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rtt
 * @date 2023/8/21 14:04
 */
public class ExceptionUtils {

    public static ClientHttpResponse handleException(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException exception) {
        return new AbstractClientHttpResponse() {
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return "Sentinel block!";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("Sentinel block!".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                Map<String, List<String>> headers = new HashMap<>();
                headers.put("Content-Type", List.of("application/json"));
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(headers);
                return httpHeaders;
            }
        };
    }
}
