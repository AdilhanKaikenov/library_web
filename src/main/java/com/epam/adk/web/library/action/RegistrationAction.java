package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.FormValidationException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Gender;
import com.epam.adk.web.library.model.enums.Role;
import com.epam.adk.web.library.service.UserService;
import com.epam.adk.web.library.util.MD5;
import com.epam.adk.web.library.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.adk.web.library.util.ConstantsHolder.*;

/**
 * RegistrationAction class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class RegistrationAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(RegistrationAction.class);

    private static final String USER_EXIST_STORED_MESSAGE = "user.exist.message";
    private static final String SUCCESS_REGISTRATION_PAGE = "success-registration";
    private static final String USER_EXIST_REQUEST_ATTRIBUTE = "userExist";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The RegistrationAction started execute.");

        String login = request.getParameter(LOGIN_PARAMETER);
        log.debug("Login: {}", login);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        log.debug("Email: {}", email);
        String firstname = request.getParameter(FIRSTNAME_PARAMETER);
        log.debug("Firstname: {}", firstname);
        String surname = request.getParameter(SURNAME_PARAMETER);
        log.debug("Surname: {}", surname);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
        log.debug("Patronymic: {}", patronymic);
        Gender gender = Gender.getFromValue(request.getParameter(GENDER_PARAMETER));
        log.debug("Gender: {}", gender);
        String address = request.getParameter(ADDRESS_PARAMETER);
        log.debug("Address: {}", address);
        String mobilePhone = request.getParameter(MOBILE_PHONE_PARAMETER);
        log.debug("Mobile Phone: {}", mobilePhone);
        log.debug("Request parameters valid.");

        if (isFormInvalid(request)) return REGISTRATION_PAGE_NAME;

        User user = createNewUser(login, password, email, firstname, surname, patronymic, gender, address, mobilePhone);

        UserService userService = new UserService();

        User registeredUser;
        try {
            registeredUser = userService.register(user);
            log.debug("New User successfully registered User: id = {}, login = {}", registeredUser.getId(), registeredUser.getLogin());
        } catch (ServiceException e) {
            log.error("Error: RegistrationAction class, Can not register new user: {}", e);
            request.setAttribute(USER_EXIST_REQUEST_ATTRIBUTE, USER_EXIST_STORED_MESSAGE);
            return REGISTRATION_PAGE_NAME;
        }
        log.debug("RegistrationAction success.");
        return REDIRECT_PREFIX + SUCCESS_REGISTRATION_PAGE;
    }

    private boolean isFormInvalid(HttpServletRequest request) throws ActionException {
        try {
            FormValidator validator = new FormValidator();
            boolean isInvalid = validator.isInvalid(REGISTRATION_PAGE_NAME, request);
            log.debug("Registration form validation, invalid = {}", isInvalid);
            if (isInvalid){
                return true;
            }

        } catch (FormValidationException e) {
            throw new ActionException("Error: RegistrationAction class. Validation failed:", e);
        }
        return false;
    }

    private User createNewUser(String login, String password, String email, String firstname, String surname, String patronymic, Gender gender, String address, String mobilePhone) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(MD5.get(password));
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setGender(gender);
        user.setRole(Role.READER);
        user.setAddress(address);
        user.setMobilePhone(mobilePhone);
        boolean isActive = true;
        user.setStatus(isActive);
        log.debug("New User created.");
        return user;
    }
}
