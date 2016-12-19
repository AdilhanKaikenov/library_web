package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.UserDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Gender;
import com.epam.adk.web.library.model.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcUserDao class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 * @see UserDao
 */
public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);

    private static final String TABLE_NAME = "user";
    private static final String CREATE_QUERY = queryProperties.get("insert.user");
    private static final String SELECT_BY_LOGIN_PASSWORD = queryProperties.get("select.by.login.password");
    private static final String SELECT_ALL = queryProperties.get("select.all.users");
    private static final String SELECT_RANGE = queryProperties.get("select,range.users");
    private static final String SELECT_BY_ID = queryProperties.get("select.user.by.id");
    private static final String UPDATE_QUERY = queryProperties.get("update.user");

    public JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<User> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcUserDao class, createListFrom() method");
        List<User> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                log.debug("Creating user from resultSet");
                user.setId(resultSet.getInt("ID"));
                user.setLogin(resultSet.getString("LOGIN"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setFirstname(resultSet.getString("FIRSTNAME"));
                user.setSurname(resultSet.getString("SURNAME"));
                user.setPatronymic(resultSet.getString("PATRONYMIC"));
                user.setGender(Gender.from(resultSet.getString("GENDER")));
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setMobilePhone(resultSet.getString("MOBILE_PHONE"));
                user.setRole(Role.from(resultSet.getString("ROLE")));
                user.setStatus(resultSet.getBoolean("STATUS"));
                log.debug("User successfully created in createListFrom() method. User id = {}", user.getId());
                result.add(user);
            }
            log.debug("Leaving JdbcUserDao class, createListFrom() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class createListFrom() method. I can not create List of users from resultSet. {}", e);
            throw new DaoException("Error: JdbcUserDao class createListFrom() method. I can not create List of users from resultSet.", e);
        }
        return result;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, User user) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInCreatePreparedStatement() method. User = {}", user.getLogin());
        try {
            log.debug("Set login: {}", user.getLogin());
            preparedStatement.setString(1, user.getLogin());
            log.debug("Set password: {}", user.getPassword());
            preparedStatement.setString(2, user.getPassword());
            log.debug("Set email: {}", user.getEmail());
            preparedStatement.setString(3, user.getEmail());
            log.debug("Set firstname: {}", user.getFirstname());
            preparedStatement.setString(4, user.getFirstname());
            log.debug("Set surname: {}", user.getSurname());
            preparedStatement.setString(5, user.getSurname());
            log.debug("Set patronymic: {}", user.getPatronymic());
            preparedStatement.setString(6, user.getPatronymic());
            log.debug("Set gender: {}",  user.getGender());
            preparedStatement.setInt(7, user.getGender().ordinal());
            log.debug("Set address: {}", user.getAddress());
            preparedStatement.setString(8, user.getAddress());
            log.debug("Set mobile phone: {}", user.getMobilePhone());
            preparedStatement.setString(9, user.getMobilePhone());
            log.debug("Set role: {}", user.getRole());
            preparedStatement.setInt(10, user.getRole().ordinal());
            log.debug("Set status, is active: {}", true);
            preparedStatement.setBoolean(11, true);
            log.debug("Leaving JdbcUserDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcUserDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, User user) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInReadByEntityPreparedStatement() method. User = {}", user.getLogin());
        try {
            log.debug("Set login: {}", user.getLogin());
            preparedStatement.setString(1, user.getLogin());
            log.debug("Set password: {}", user.getPassword());
            preparedStatement.setString(2, user.getPassword());
            log.debug("Leaving JdbcUserDao class, setFieldsInReadByEntityPreparedStatement() method");
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class setFieldsInReadByEntityPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcUserDao class setFieldsInReadByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, User user) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            log.debug("Set password: {}", user.getPassword());
            preparedStatement.setString(1, user.getPassword());
            log.debug("Set email: {}", user.getEmail());
            preparedStatement.setString(2, user.getEmail());
            log.debug("Set address: {}", user.getAddress());
            preparedStatement.setString(3, user.getAddress());
            log.debug("Set mobile phone: {}", user.getMobilePhone());
            preparedStatement.setString(4, user.getMobilePhone());
            log.debug("Set role: {}", user.getRole());
            preparedStatement.setInt(5, user.getRole().ordinal());
            log.debug("Set status, is active: {}", user.isStatus());
            preparedStatement.setBoolean(6, user.isStatus());
            log.debug("Set user ID: {}", user.getId());
            preparedStatement.setInt(7, user.getId());
            log.debug("Leaving JdbcUserDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcUserDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM user WHERE id LIKE ?";
    }

    @Override
    protected String getDeleteByIdQuery() {
        return null;
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getReadByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getReadAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getReadByEntityQuery() {
        return SELECT_BY_LOGIN_PASSWORD;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getReadRangeQuery() {
        return SELECT_RANGE;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return null;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return null;
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return null;
    }

}
