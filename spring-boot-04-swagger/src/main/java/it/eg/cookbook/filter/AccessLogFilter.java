package it.eg.cookbook.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
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
@Order(1)
public class AccessLogFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID_NAME = "X-Request-ID";
    public static final String ACTUATOR = "/actuator";

    public static final int CONTENT_CACHE_LIMIT = 1000;

    protected boolean isAsyncDispatch(HttpServletRequest request) {
        return DispatcherType.ASYNC.equals(request.getDispatcherType());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest requestToUse = request;
        boolean isFirstRequest = !this.isAsyncDispatch(request);
        if (isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request, CONTENT_CACHE_LIMIT);
        }

        Instant start = Instant.now();

        String correlationId = requestToUse.getHeader(CORRELATION_ID_NAME);
        if (correlationId == null || "".equals(correlationId.trim())) {
            correlationId = generateUniqueCorrelationId();
        }

        response.setHeader(CORRELATION_ID_NAME, correlationId);
        try (MDC.MDCCloseable m = MDC.putCloseable(CORRELATION_ID_NAME, correlationId)) {
            boolean doLog = requestToUse.getRequestURI() == null || !requestToUse.getRequestURI().startsWith(ACTUATOR);

            // Access Log IN
            if (doLog) {
                log.info("IN  | {} {}{}", requestToUse.getMethod(), requestToUse.getRequestURI(), getQueryString(requestToUse));

                String payload = getMessagePayload(requestToUse);
                if (payload != null) {
                    log.debug("    | payload {}", payload);
                }
            }

            filterChain.doFilter(requestToUse, response);

            // Access Log OUT
            if (doLog) {
                log.info("OUT | {} {}{} | status {}, duration {}", requestToUse.getMethod(), requestToUse.getRequestURI(), getQueryString(requestToUse), response.getStatus(), ChronoUnit.MILLIS.between(start, Instant.now()));
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