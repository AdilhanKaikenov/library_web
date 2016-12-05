package com.epam.adk.web.library.dao;

import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Order;

/**
 * Interface OrderDao created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 * @see Dao
 */
public interface OrderDao extends Dao<Order> {

    int countOrder(Order order) throws DaoException;

}