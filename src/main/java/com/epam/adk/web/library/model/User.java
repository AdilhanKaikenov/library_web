package com.epam.adk.web.library.model;

import com.epam.adk.web.library.model.enums.Gender;
import com.epam.adk.web.library.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * User class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class User extends BaseEntity {

    private String login;
    private String password;
    private String email;
    private Gender gender;
    private String firstname;
    private String surname;
    private String patronymic;
    private String address;
    private String mobilePhone;
    private Role role;
    private List<Book> subscriptionBooks;
    private List<Book> readingRoomBooks;
    private boolean status;

    public User() {
        subscriptionBooks = new ArrayList<>();
        readingRoomBooks = new ArrayList<>();
    }

    public List<Book> getSubscriptionBooks() {
        return subscriptionBooks;
    }

    public void setSubscriptionBooks(List<Book> subscriptionBooks) {
        this.subscriptionBooks = subscriptionBooks;
    }

    public List<Book> getReadingRoomBooks() {
        return readingRoomBooks;
    }

    public void setReadingRoomBooks(List<Book> readingRoomBooks) {
        this.readingRoomBooks = readingRoomBooks;
    }

    public void addSubscriptionBook(Book book){
        subscriptionBooks.add(book);
    }

    public void addReadingRoomBook(Book book){
        readingRoomBooks.add(book);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
