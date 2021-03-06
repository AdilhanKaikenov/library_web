package com.epam.adk.web.library.action.librarian;

import static com.epam.adk.web.library.util.ConstantsHolder.ORDERS_PAGE_NAME;

/**
 * ShowAllAllowedOrdersAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAllAllowedOrdersAction extends AbstractShowOrdersAction {

    private static final boolean ALLOWED_STATUS = true;

    @Override
    protected String getPage() {
        return ORDERS_PAGE_NAME;
    }

    @Override
    protected boolean getOrderStatus() {
        return ALLOWED_STATUS;
    }
}
