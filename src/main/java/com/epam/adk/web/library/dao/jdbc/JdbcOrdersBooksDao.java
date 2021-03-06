package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.OrdersBooksDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.DaoUnsupportedOperationException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcOrdersBooksDao class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 * @see OrdersBooksDao
 **/
public class JdbcOrdersBooksDao extends JdbcDao<OrderBook> implements OrdersBooksDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcOrdersBooksDao.class);

    private static final String TABLE_NAME = "orders";
    private static final String SELECT_COLUMNS_PART = queryProperties.get("select.order.book.columns");
    private static final String DELETE_QUERY = queryProperties.get("delete.order.book");
    private static final String INSERT_QUERY = queryProperties.get("insert.order.book");
    private static final String UPDATE_QUERY = queryProperties.get("update.order.book");
    private static final String DELETE_ORDER_ID_QUERY = queryProperties.get("delete.orders.books");
    private static final String SELECT_COUNT_QUERY = queryProperties.get("select.orders.books.count");
    private static final String SELECT_BY_USER_AND_BOOK_ID_QUERY = queryProperties.get("select.by.user.and.book.id");
    private static final String SELECT_ALL_BY_ORDER_ID_QUERY = queryProperties.get("select.all.orders.books.by.order.id");
    private static final String SELECT_COUNT_AVAILABLE_AMOUNT = queryProperties.get("select.count.available.books.amount");
    private static final String SELECT_COUNT_BY_BOOK_ID_QUERY = queryProperties.get("select.count.orders.books.by.book.id");
    private static final String SELECT_COUNT_BY_ORDER_ID_QUERY = queryProperties.get("select.count.orders.books.by.order.id");

    public JdbcOrdersBooksDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<OrderBook> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, createListFrom() method");
        List<OrderBook> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                OrderBook orderBook = new OrderBook();
                log.debug("Creating book of order getFromValue resultSet");
                User user = new User();
                user.setId(resultSet.getInt(USER_ID_COLUMN_NAME));
                user.setLogin(resultSet.getString(LOGIN_COLUMN_NAME));
                user.setFirstname(resultSet.getString(FIRSTNAME_COLUMN_NAME));
                user.setSurname(resultSet.getString(SURNAME_COLUMN_NAME));
                user.setPatronymic(resultSet.getString(PATRONYMIC_COLUMN_NAME));
                user.setAddress(resultSet.getString(ADDRESS_COLUMN_NAME));
                user.setEmail(resultSet.getString(EMAIL_COLUMN_NAME));
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE_COLUMN_NAME));
                orderBook.setUser(user);
                Book book = new Book();
                book.setId(resultSet.getInt(BOOK_ID_COLUMN_NAME));
                book.setTitle(resultSet.getString(TITLE_COLUMN_NAME));
                book.getAuthor().setName(resultSet.getString(AUTHOR_COLUMN_NAME));
                book.setPublishYear(Year.of(resultSet.getInt(PUBLISH_YEAR_COLUMN_NAME)));
                orderBook.setBook(book);
                Order order = new Order();
                order.setId(resultSet.getInt(ORDER_ID_COLUMN_NAME));
                order.setOrderDate(resultSet.getDate(ORDER_DATE_COLUMN_NAME));
                order.setFrom(resultSet.getDate(DATE_FROM_COLUMN_NAME));
                order.setTo(resultSet.getDate(DATE_TO_COLUMN_NAME));
                order.setStatus(resultSet.getBoolean(STATUS_COLUMN_NAME));
                orderBook.setOrder(order);
                orderBook.setAvailableBookAmount(countAvailableAmount(book.getId()));
                log.debug("OrderBook successfully created in createFrom() method.");
                result.add(orderBook);
            }
            log.debug("Leaving JdbcOrdersBooksDao class, createListFrom() method. Amount of orders books = {}", result.size());
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcOrdersBooksDao class createListFrom() method. I can not create List of orders books getFromValue resultSet.", e);
        }
        return result;
    }

    @Override
    public OrderBook read(int userID, int bookID) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, read(userID, bookID) method, User ID = {}, book ID = {}", userID, bookID);
        OrderBook result;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(SELECT_COLUMNS_PART + SELECT_BY_USER_AND_BOOK_ID_QUERY);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, userID);
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, bookID);
            resultSet = preparedStatement.executeQuery();
            result = createFrom(resultSet);
            log.debug("Leaving JdbcOrdersBooksDao class, read(userID, bookID) method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcOrdersBooksDao class read(userID, bookID) method. I can not read book of order by id getFromValue resultSet.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public void delete(int userID, int bookID) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, delete(userID, bookID) method, User ID = {}, book ID = {}", userID, bookID);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, userID);
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, bookID);
            preparedStatement.execute();
            log.debug("Leaving JdbcOrdersBooksDao class, delete(userID, bookID) method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcOrdersBooksDao class delete(userID, bookID) method. I can not delete book of order by id.", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public int countAvailableAmount(int bookID) throws DaoException {
        log.debug("Entering JdbcBookDao class, countAvailableAmount() method. Book ID = {}", bookID);
        int count = 0;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = getConnection().prepareStatement(SELECT_COUNT_AVAILABLE_AMOUNT);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, bookID);
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, bookID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(FIRST_COLUMN_INDEX);
            }
            log.debug("Leaving JdbcBookDao class, countAvailableAmount() method. Book amount = {}", count);
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcBookDao class countAvailableAmount() method.", e);
        }
        return count;
    }

    @Override
    public int countOrderedBooks(OrderBook order) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, countOrderedBooks() method");
        int count = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(SELECT_COUNT_QUERY);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, order.getBook().getId());
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, order.getUser().getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(FIRST_COLUMN_INDEX);
            }
            log.debug("Leaving JdbcOrdersBooksDao class, countOrderedBooks() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcOrdersBooksDao class countOrderedBooks() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return count;
    }

     @Override
    public int getNumberRowsByBookId(int bookID) throws DaoException {
         log.debug("Entering JdbcOrderDao class, getNumberRowsByStatusId() method. Book Id = {}", bookID);
         int numberRows = 0;
         PreparedStatement preparedStatement = null;
         ResultSet resultSet = null;
         try {
             preparedStatement = getConnection().prepareStatement(SELECT_COUNT_BY_BOOK_ID_QUERY);
             preparedStatement = setFieldInCountNumberRowsByIdPreparedStatement(preparedStatement, bookID);
             resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
                 numberRows = resultSet.getInt(FIRST_COLUMN_INDEX);
             }
             log.debug("Leaving JdbcOrderDao class getNumberRowsByStatusId() method. Rows number = {}", numberRows);
         } catch (SQLException e) {
             throw new DaoException("Error: JdbcOrderDao class, getNumberRowsByStatusId() method.", e);
         } finally {
             close(preparedStatement, resultSet);
         }
         return numberRows;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, orderBook.getUser().getId());
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, orderBook.getBook().getId());
            preparedStatement.setInt(THIRD_PARAMETER_INDEX, orderBook.getOrder().getId());

            log.debug("Leaving JdbcOrdersBooksDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcOrdersBooksDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInDeleteByEntityStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, setFieldsInDeleteByEntityStatement() method.");
        try {
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, orderBook.getUser().getId());
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, orderBook.getBook().getId());
            log.debug("Leaving JdbcOrdersBooksDao class, setFieldsInDeleteByEntityStatement() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcOrdersBooksDao class setFieldsInDeleteByEntityStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            preparedStatement.setBoolean(FIRST_PARAMETER_INDEX, orderBook.isIssued());
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, orderBook.getUser().getId());
            preparedStatement.setInt(THIRD_PARAMETER_INDEX, orderBook.getBook().getId());
            log.debug("Leaving JdbcOrdersBooksDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcOrdersBooksDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getCreateQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteByIdQuery() {
        return DELETE_ORDER_ID_QUERY;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return SELECT_COUNT_BY_ORDER_ID_QUERY;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return SELECT_COLUMNS_PART + SELECT_ALL_BY_ORDER_ID_QUERY;
    }

    @Override
    protected String getReadByEntityQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getReadByIdQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getReadRangeQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }
}
