<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="request.processing.page">

    <%--@elvariable id="order" type="com.epam.adk.web.library.model.Order"--%>
    <div align="center">
        <h1><ftm:message key="order.number"/>: ${order.id}</h1>
        <h2><ftm:message key="order.type"/>: ${order.orderType.value}</h2>
    </div>
    <div>
        <h3><ftm:message
                key="client.full.name"/>: ${order.user.surname} ${order.user.firstname} ${order.user.patronymic}</h3>
        <h3><ftm:message key="address.field"/>: ${order.user.address}</h3>
        <h3><ftm:message key="email.field"/>: ${order.user.email}</h3>
        <h3><ftm:message key="mobphone.field"/>: ${order.user.mobilePhone}</h3>
    </div>
    <hr>
    <h4><ftm:message key="books.header"/>:</h4>
    <%--@elvariable id="ordersBooks" type="java.util.List"--%>
    <table cellpadding="10" style="border: rebeccapurple; background: beige;" border="1px" align="center">
        <tr align="center" style="background: whitesmoke">
            <th width="200px">
                <ftm:message key="book.title"/>
            </th>
            <th width="200px">
                <ftm:message key="book.publish.year.field"/>
            </th>
            <th width="200px">
                <ftm:message key="book.author.field"/>
            </th>
            <th width="200px">
                <ftm:message key="available.num.books"/>
            </th>
            <th width="200px">

            </th>
        </tr>
        <c:forEach items="${ordersBooks}" var="orderBook">
            <%--@elvariable id="orderBook" type="com.epam.adk.web.library.model.OrderBook"--%>
            <tr align="center" class="tr">
                <td width="200px">
                        ${orderBook.book.title}
                </td>
                <td width="200px">
                        ${orderBook.book.publishYear}
                </td>
                <td width="200px">
                        ${orderBook.book.author.name}
                </td>
                <td width="200px">
                        ${orderBook.availableBookAmount}
                </td>
                <td width="200px">
                    <form action="${base}/do/" method="post">
                        <input hidden="hidden" name="action" value="remove-book-from-order-request">
                        <input hidden="hidden" name="userID" value="${orderBook.user.id}">
                        <input hidden="hidden" name="bookID" value="${orderBook.book.id}">
                        <input hidden="hidden" name="orderID" value="${order.id}">
                        <button type="submit"
                                onclick="return confirm('<ftm:message key="confirm.warning"/>')"
                                class="link-style"><ftm:message key="delete.button"/></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="5" align="center">
                    <c:if test="${ordersBooks.size() != 0}">
                        <form action="${base}/do/" method="post">
                            <input hidden="hidden" name="action" value="lend-out-book">
                            <input hidden="hidden" name="orderID" value="${order.id}">
                            <button type="submit"
                                    onclick="return confirm('<ftm:message key="confirm.lend.out.books"/>')"
                                    class="link-style"><ftm:message key="lend.out"/></button>
                        </form>
                    </c:if>
                    <form action="${base}/do/" method="post">
                        <input hidden="hidden" name="action" value="reject-book-order">
                        <input hidden="hidden" name="orderID" value="${order.id}">
                        <button type="submit"
                                onclick="return confirm('<ftm:message key="confirm.warning"/>')"
                                class="link-style"><ftm:message key="reject.request"/></button>
                    </form>
            </td>
        </tr>
    </table>

</t:page>