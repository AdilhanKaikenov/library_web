package com.epam.adk.web.library.listener;

import com.epam.adk.web.library.action.ActionFactory;
import com.epam.adk.web.library.dao.jdbc.JdbcDao;
import com.epam.adk.web.library.dbcp.ConnectionPool;
import com.epam.adk.web.library.filter.RoleFilter;
import com.epam.adk.web.library.servlet.FrontControllerServlet;
import com.epam.adk.web.library.servlet.ImageServlet;
import com.epam.adk.web.library.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ConfigurationListener class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public final class ConfigurationListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.debug("Entering ConfigurationListener class, contextInitialized() method");
        try {
            ConnectionPool.configure();
            JdbcDao.configure();
            ActionFactory.configure();
            RoleFilter.configure();
            FormValidator.configure();
            FrontControllerServlet.configure();
            ImageServlet.configure();
        } catch (Exception e) {
            log.error("Error: ConfigurationListener class, called configure{} method failed.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.debug("Configuration end");
    }
}
