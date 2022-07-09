package com.market.filter;

import com.market.entity.User;
import com.market.util.Constants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author kinoz
 * @Date 2022/7/9 - 10:41
 * @apiNote
 */
public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //1.把形参转换为我们需要的类型
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //过滤器,从session中获取用户
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);

        //2.若session值为空就跳转失败页面
        if (user == null){
            resp.sendRedirect(req.getContextPath()+"/error.jsp");
        }else {
            //3.保过滤器继续走下去
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
