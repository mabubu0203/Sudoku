package com.mabubu0203.sudoku.web.config;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@WebFilter("/*")
public class MetaFilter implements Filter {

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader(
                "Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void destroy() {
    }
}
