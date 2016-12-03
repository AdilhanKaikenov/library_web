package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

/**
 * SelectLocaleAction class created on 30.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class SelectLocaleAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(SelectLocaleAction.class);
    private static final String REGION_PARAMETER = "region";
    private static final String SELECT_LOCALE_PATH_INFO = "/?action=set-locale&region=";
    private static final String ACTION_PARAMETER = "action";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The action of set locale started execute.");

        String region = request.getParameter(REGION_PARAMETER);
        Config.set(request.getSession(), Config.FMT_LOCALE, new java.util.Locale(region));

        String referer = request.getHeader("referer");

        if (referer.contains(SELECT_LOCALE_PATH_INFO)){
            return "redirect:welcome";
        }

        if (referer.contains(ACTION_PARAMETER)) {
            String currentPage = referer.substring(referer.indexOf("=") + 1);
            log.debug("PAY ATTENTION: Current page '{}'", currentPage);
            return "redirect:" + currentPage;
        }
        return "redirect:welcome";
    }
}
