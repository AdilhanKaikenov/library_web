package com.epam.adk.web.library.servlet;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.action.ActionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FrontControllerServlet class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class FrontControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(FrontControllerServlet.class);
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final ActionFactory factory = ActionFactory.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = factory.getAction(getActionName(request));
        if (action == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String view = action.execute(request, response);
        proceedTo(request, response, view);
    }

    private void proceedTo(HttpServletRequest request, HttpServletResponse response, String view) throws IOException, ServletException {
        if (view == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (view.contains(REDIRECT_PREFIX)){
            response.sendRedirect(getRedirectURL(request, view));
        } else {
            request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
        }
    }

    private String getRedirectURL(HttpServletRequest request, String view) {
        return request.getContextPath() + "/do/?action=show-page&page=" + view.substring(REDIRECT_PREFIX.length());
    }

    /**
     * The method to get action name from request.
     *
     * @param request
     * @return String
     */
    private String getActionName(HttpServletRequest request) {
        return request.getParameter("action");
    }
}