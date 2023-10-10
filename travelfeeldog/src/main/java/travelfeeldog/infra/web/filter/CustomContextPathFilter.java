package travelfeeldog.infra.web.filter;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomContextPathFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/")) {
            request.getRequestDispatcher("/api/v1/").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
