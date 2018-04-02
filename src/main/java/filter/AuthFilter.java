package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) request).getSession(true);
        if(session.getAttribute("authorized") != null && session.getAttribute("authorized").equals(true)) {
            chain.doFilter(request, response);
        } else if ( ((HttpServletRequest)request).getServletPath().contains("login.jsp")) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("/WebProvider_war/login.jsp");
        }
    }

    public void init(FilterConfig config) {

    }

}
