package zzvcom.sys.util.filter;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.service.ModuleService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc:
 * User: Administrator
 * Date: 2008-10-7
 * Time: 14:24:05
 */
public class AuthFilter implements Filter {
    protected FilterConfig config;

    //protected String loginPage = "";
    public void destroy() {
        // TODO Auto-generated method stub
        //this.loginPage = null;

    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setCharacterEncoding("utf8");
        request.setCharacterEncoding("utf8");


        if (request.getMethod().equalsIgnoreCase("post")) {
//            System.out.println("is post=" + request.getMethod());
//            try {
//                request.setCharacterEncoding("utf8");
//                Iterator iter = request.getParameterMap().values().iterator();
//                while (iter.hasNext()) {
//                    String[] parames = (String[]) iter.next();
//
//                    for (int i = 0; i < parames.length; i++) {
//                        System.out.println("post value=" + parames[i]);
//                    }
//                }
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        } else {
            System.out.println("is get=" + request.getMethod());
            Iterator iter = request.getParameterMap().values().iterator();
            while (iter.hasNext()) {
                String[] parames = (String[]) iter.next();
                for (int i = 0; i < parames.length; i++) {
                    try {
                        parames[i] = new String(parames[i].getBytes("iso8859-1"), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

}
