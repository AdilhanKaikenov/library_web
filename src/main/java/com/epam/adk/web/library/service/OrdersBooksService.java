package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.OrdersBooksDao;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * OrdersBooksService class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class OrdersBooksService {

    private static final Logger log = LoggerFactory.getLogger(OrdersBooksService.class);

    public OrderBook submitOrderBook(OrderBook order) throws ServiceException {
        log.debug("Entering OrdersBooksService class submitOrderBook() method.");
        OrderBook orderRequest;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderRequest = orderDao.create(order);
            log.debug("Leaving OrdersBooksService class submitOrderBook() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, submitOrderBook() method.", e);
        }
        return orderRequest;
    }

    public OrderBook getOrderBookById(int userID, int bookID) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrderBookById() method. user Id = {}, book Id = {}", userID, bookID);
        OrderBook order;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            order = orderDao.read(userID, bookID);
            log.debug("Leaving OrdersBooksService class getOrderBookById() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrderBookById() method.", e);
        }
        return order;
    }

    public List<OrderBook> getOrdersBooks(int orderID) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrdersBooks() method.");
        List<OrderBook> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            result = orderDao.readAllByIdParameter(orderID);
            log.debug("Leaving OrdersBooksService class getOrdersBooks() method. Amount of orders books comment = {}", result.size());
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrdersBooks() method.", e);
        }
        return result;
    }

    public void update(OrderBook orderBook) throws ServiceException {
        log.debug("Entering OrdersBooksService class update() method. OrderBook Id = {}", orderBook.getId());
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao ordersBooksDao = jdbcDaoFactory.getOrdersBooksDao();
            ordersBooksDao.update(orderBook);
            log.debug("Leaving OrdersBooksService class update() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, update() method.", e);
        }
    }

    public void delete(OrderBook order) throws ServiceException {
        log.debug("Entering OrdersBooksService class delete() method. ");
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderDao.delete(order);
            log.debug("Leaving OrdersBooksService class delete() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, delete() method.", e);
        }
    }

    public int getOrdersNumberByBookID(int bookID) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrdersNumberByBookID() method.");
        int orderBooksNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderBooksNumber = orderDao.getNumberRowsByBookId(bookID);
            log.debug("Leaving OrdersBooksService class getOrdersNumberByBookID() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrdersNumberByBookID() method.", e);
        }
        return orderBooksNumber;
    }

    public int getOrderBooksNumberByOrderId(int orderID) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrderBooksNumberByOrderId() method.");
        int orderBooksNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderBooksNumber = orderDao.getNumberRowsByIdParameter(orderID);
            log.debug("Leaving OrdersBooksService class getOrderBooksNumberByOrderId() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrderBooksNumberByOrderId() method.", e);
        }
        return orderBooksNumber;
    }

    public int getOrderedBooksNumber(OrderBook order) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrderNumber() method.");
        int orderNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderNumber = orderDao.countOrderedBooks(order);
            log.debug("Leaving OrdersBooksService class getOrderNumber() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrderNumber() method.", e);
        }
        return orderNumber;
    }

    public int getAvailableBookAmount(int bookID) throws ServiceException {
        log.debug("Entering OrdersBooksService class getAvailableBookAmount() method.");
        int amount;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao ordersBooksDao = jdbcDaoFactory.getOrdersBooksDao();
            amount = ordersBooksDao.countAvailableAmount(bookID);
            log.debug("Leaving OrdersBooksService class getAvailableBookAmount() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getAvailableBookAmount() method.", e);
        }
        return amount;
    }
}
