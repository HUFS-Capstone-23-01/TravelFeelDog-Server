package travelfeeldog.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean<CustomContextPathFilter> loggingFilter() {
        FilterRegistrationBean<CustomContextPathFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CustomContextPathFilter());
        registrationBean.addUrlPatterns("/web/*");  // 이 패턴에 해당하는 요청만 필터 적용

        return registrationBean;
    }
}