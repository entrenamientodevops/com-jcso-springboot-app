package com.jcso.springboot.app.zuul.server.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PostFilterTimeBehavior extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(PostFilterTimeBehavior.class);


    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        logger.info("Entrando en el post filter");

        Long startTime = (Long) request.getAttribute("startTime");
        Long endTime = System.currentTimeMillis();

        Long time = endTime - startTime;

        logger.info(String.format("Tiempo transcurrido en segundos %s seg", time.doubleValue() / 1000.00));
        logger.info(String.format("Tiempo transcurrido en milisegundos %s ms", time));

        return null;
    }
}
