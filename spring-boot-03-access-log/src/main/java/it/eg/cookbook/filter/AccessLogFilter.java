package it.eg.cookbook.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class AccessLogFilter implements Filter {

    public static final String CORRELATION_ID_NAME = "X-Request-ID";
    public static final String ACTUATOR = "/actuator";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Instant start = Instant.now();

        String correlationId = req.getHeader(CORRELATION_ID_NAME);

        if (correlationId == null || "".equals(correlationId.trim())) {
            correlationId = generateUniqueCorrelationId();
        }

        resp.setHeader(CORRELATION_ID_NAME, correlationId);
        try (MDC.MDCCloseable m = MDC.putCloseable(CORRELATION_ID_NAME, correlationId)) {
            boolean doLog = req.getRequestURI() == null || !req.getRequestURI().startsWith(ACTUATOR);

            // Access Log IN
            if (doLog) {
                log.info("IN  | {} {}{}", req.getMethod(), req.getRequestURI(), getQueryString(req));

                String payload = this.getMessagePayload(req);
                if (payload != null) {
                    log.debug("    | payload {}", payload);
                }
            }

            chain.doFilter(request, response);

            // Access Log OUT
            if (doLog) {
                log.info("OUT | {} {}{} | status {}, duration {}", req.getMethod(), req.getRequestURI(), getQueryString(req), resp.getStatus(), ChronoUnit.MILLIS.between(start, Instant.now()));
            }
        }
    }

    private String generateUniqueCorrelationId() {
        return new StringBuilder().append("generated:")
                .append(UUID.randomUUID())
                .toString();
    }

    private String getQueryString(HttpServletRequest request) {
        String payload = request.getQueryString();
        if (payload != null) {
            return "?" + payload;
        } else {
            return "";
        }
    }

    private String getMessagePayload(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 100);

                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding()).replace("\n", "").replace("\r", "");
                } catch (UnsupportedEncodingException var6) {
                    return "[unknown]";
                }
            }
        }

        return null;
    }

}