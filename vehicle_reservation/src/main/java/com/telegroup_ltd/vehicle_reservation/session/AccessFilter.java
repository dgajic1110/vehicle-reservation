package com.telegroup_ltd.vehicle_reservation.session;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AccessFilter implements Filter {

    private WebApplicationContext springContext;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        UserBean userBean = (UserBean) springContext.getBean("userBean");
/*
        if(!userBean.getLoggedIn()){
            response.sendError(401);
            return;
        }
*/
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    }

}
