package com.jcso.springboot.app.zuul.server.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PreFilterTimeBehavior extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(PreFilterTimeBehavior.class);


    @Override
    public String filterType() {
        return "pre";
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

        logger.info(String.format("%s rquest enviado a %s", request.getMethod(), request.getRequestURL().toString()));

        Long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return null;
    }
}
