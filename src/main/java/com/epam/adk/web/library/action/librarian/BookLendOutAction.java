package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.enums.OrderStatus;
import com.epam.adk.web.library.model.enums.OrderType;
import com.epam.adk.web.library.service.OrderBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * BookLendOutAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookLendOutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookLendOutAction.class);

    private static final int TWO_WEEKS_TIME_DURATION = 1209600000;
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String ORDER_ID_PARAMETER = "orderID";
    private static final String REQUESTS_PAGE_NAME = "requests";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The BookLendOutAction started execute.");

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));

        OrderBookService orderBookService = new OrderBookService();

        try {
            Order order = orderBookService.getOrderById(orderID);

            order.setStatus(OrderStatus.ALLOWED);

            java.util.Date today = new java.util.Date();
            Date fromDate = new Date(today.getTime());
            Date toDate = new Date(today.getTime() + TWO_WEEKS_TIME_DURATION);

            if (order.getType() == OrderType.SUBSCRIPTION) {
                order.setFrom(fromDate);
                order.setTo(toDate);
            }

            if (order.getType() == OrderType.READING_ROOM) {
                order.setFrom(fromDate);
                order.setTo(fromDate);
            }

            orderBookService.updateOrder(order);

        } catch (ServiceException e) {
            throw new ActionException("Error: BookLendOutAction class:", e);
        }
        return REDIRECT_PREFIX + REQUESTS_PAGE_NAME;
    }
}