package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

@WebFilter(filterName = "HistoryFilter")
public class HistoryFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)request).getSession(true);
        LinkedList<String> historyList = (LinkedList<String>) session.getAttribute("history");

        if(historyList == null) {
            historyList = new LinkedList<>();
            session.setAttribute("history", historyList);
        }

        if(request.getParameter("backButton") != null && historyList.size() > 0) {
            historyList.removeLast();
            ((HttpServletResponse) response).sendRedirect(historyList.getLast());
            return;
        } else if (request.getParameter("backButton") == null &&  !((HttpServletRequest) request).getRequestURI().contains("login.jsp") && (historyList.isEmpty() || !((HttpServletRequest) request).getRequestURI().equals(historyList.getLast()))) {
            historyList.add(((HttpServletRequest) request).getRequestURI());
        }
        for(String uri: historyList) {
            System.out.println(uri);
        }
        session.setAttribute("historySize", historyList.size());
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
