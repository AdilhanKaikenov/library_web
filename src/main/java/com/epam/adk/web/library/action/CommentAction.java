package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Comment;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

import static com.epam.adk.web.library.util.ConstantsHolder.*;

/**
 * CommentAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class CommentAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CommentAction.class);

    private static final String REGEX_ENTER = "\\r";
    private static final String COMMENT_PARAMETER = "comment";
    private static final String LINE_BREAK_HTML_TAG = "<p>";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The CommentAction started execute.");

        boolean isCreated = false;
        HttpSession session = request.getSession(isCreated);

        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        User user = ((User) session.getAttribute(USER_PARAMETER));
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        log.debug("Book ID = {}", bookID);
        log.debug("Request parameters valid.");

        String text = request.getParameter(COMMENT_PARAMETER).replaceAll(REGEX_ENTER, LINE_BREAK_HTML_TAG);

        Comment comment = createComment(time, user, bookID, text);

        CommentService commentService = new CommentService();

        try {
            commentService.writeComment(comment);
            log.debug("New Comment added.");
        } catch (ServiceException e) {
            throw new ActionException("Error: CommentAction class, execute() method. Called CommentService class, writeComment() failed.", e);
        }

        return REDIRECT_PREFIX + ABOUT_BOOK_ID_PAGE_NAME + bookID;
    }

    /**
     * Creating Comment based on data from the request.
     *
     * @param time
     * @param user User from session
     * @param bookID
     * @param text text of comment
     * @return Comment
     */
    private Comment createComment(Timestamp time, User user, int bookID, String text) {
        Book book = new Book();
        book.setId(bookID);

        Comment comment = new Comment();
        comment.getUser().setId(user.getId());
        comment.getUser().setLogin(user.getLogin());
        comment.getUser().setFirstname(user.getFirstname());
        comment.getUser().setSurname(user.getSurname());
        comment.setBook(book);
        comment.setTime(time);
        comment.setText(text);
        log.debug("New Comment created.");
        return comment;
    }
}
