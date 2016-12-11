package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.service.OrderBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowBookAmountAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowBookAmountAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowBookAmountAction.class);
    private static final String ORDERED_BOOK_NUMBER_REQUEST_ATTRIBUTE = "orderedBookNumber";
    private static final String BOOK_ID_PARAMETER = "bookID";
    private static final String BOOK_REQUEST_ATTRIBUTE = "book";
    private static final String EDIT_BOOK_AMOUNT_PAGE_NAME = "edit-book-amount";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowBookAmountAction started execute.");

        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));

        BookService bookService = new BookService();
        OrderBookService orderBookService = new OrderBookService();

        try {
            Book book = bookService.getBookById(bookID);

            int orderedBookNumber = orderBookService.getOrdersNumberByBookID(bookID);

            request.setAttribute(ORDERED_BOOK_NUMBER_REQUEST_ATTRIBUTE, orderedBookNumber);
            request.setAttribute(BOOK_REQUEST_ATTRIBUTE, book);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowBookAmountAction class. ", e);

        }
        return EDIT_BOOK_AMOUNT_PAGE_NAME;
    }
}
