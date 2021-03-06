package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * JdbcDaoFactory class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class JdbcDaoFactory extends DaoFactory {

    private static final Logger log = LoggerFactory.getLogger(JdbcDaoFactory.class);

    private Connection connection;

    public JdbcDaoFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserDao getUserDao() {
        log.debug("JdbcDaoFactory class, JdbcUserDao created");
        return new JdbcUserDao(connection);
    }

    @Override
    public BookDao getBookDao() {
        log.debug("JdbcDaoFactory class, JdbcBookDao created");
        return new JdbcBookDao(connection);
    }

    @Override
    public CommentDao getCommentDao() {
        log.debug("JdbcDaoFactory class, JdbcCommentDao created");
        return new JdbcCommentDao(connection);
    }

    @Override
    public OrdersBooksDao getOrdersBooksDao() {
        log.debug("JdbcDaoFactory class, JdbcOrdersBooksDao created");
        return new JdbcOrdersBooksDao(connection);
    }

    @Override
    public OrdersDao getOrdersDao() {
        log.debug("JdbcDaoFactory class, OrdersDao created");
        return new JdbcOrders(connection);
    }
}