package com.epam.adk.web.library.model;

import com.epam.adk.web.library.model.enums.OrderType;

import java.sql.Date;

/**
 * Order class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class Order extends BaseEntity {

    private int userID;
    private int bookID;
    private Date orderDate;
    private OrderType type;
    private Date from;
    private Date to;
    private boolean status;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}