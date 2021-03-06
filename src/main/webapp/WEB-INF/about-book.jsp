<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="custom" prefix="ct" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
<t:page title="about.book" optionalTitle="${book.title}">

    <t:book book="${book}"/>

    <ct:hasRole user="${user}" role="READER">
        <%--@elvariable id="availableBookAmount" type="java.lang.Integer"--%>
        <div class="reader-requests-links-section" align="center">
            <c:if test="${availableBookAmount != 0 && empty requestScope.bookAddedToOrder && empty requestScope.bookOrdered}">
                <form action="${base}/do/" method="post">
                    <input hidden="hidden" name="action" value="add-book-to-order">
                    <input hidden="hidden" name="bookID" value="${book.id}">
                    <i><input type="radio" name="order_type" value="Subscription" checked><ftm:message
                            key="subscription"/></i>
                    <i><input type="radio" name="order_type" value="Reading room"><ftm:message key="reading.room"/></i>
                    <br>
                    <button style="margin: 10px" type="submit"
                            onclick="return confirm('<ftm:message key="confirm.warning"/>')"
                            class="link-style"><ftm:message key="add.to.order"/></button>
                </form>
            </c:if>
            <ct:hasRole user="${user}" role="READER">
                <c:if test="${not empty requestScope.bookOrdered}">
                    <li><ftm:message key="${requestScope.bookOrdered}"/></li>
                </c:if>
                <c:if test="${not empty requestScope.bookAddedToOrder}">
                    <li><ftm:message key="${requestScope.bookAddedToOrder}"/></li>
                </c:if>
                <c:if test="${availableBookAmount == 0}">
                    <li><ftm:message key="book.no.available"/></li>
                </c:if>
            </ct:hasRole>
        </div>
    </ct:hasRole>
    <div style="float: left; width: 100%;" align="center">
        <h1><ftm:message key="short.book.info.header"/></h1>
    </div>
    <div class="book-description-section">
        <h1>${requestScope.sentRequestFailed}</h1>
        <p style="text-align: justify">${book.description}</p>
        <hr>
    </div>

    <ftm:message key="min.max.review.length.message" var="maxCommentLength"/>
    <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
    <div class="book-submit-comment-form-section">
        <c:if test="${not empty user}">
            <form action="${base}/do/" method="post">
                <input type="hidden" name="action" value="comment">
                <input type="hidden" name="bookID" value="${book.id}">
                <textarea style="resize: none;overflow: hidden;text-overflow: ellipsis;" type="text" name="comment"
                          cols="125" rows="4" minlength="30" maxlength="250" required
                          autofocus placeholder="${maxCommentLength}"></textarea>
                <br>
                <button type="submit" class="link-style">
                    <ftm:message key="leave.comment.button"/></button>
            </form>
        </c:if>
    </div>
    <div class="send-comment-requirement-section">
        <c:if test="${empty user}">
            <ftm:message key="anon.send.comment.requirement.message"/>
        </c:if>
    </div>
    <%--@elvariable id="bookComments" type="java.util.List"--%>
    <c:if test="${not empty bookComments}">
        <div class="comment-pagination-section" align="center">
                <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
            <c:if test="${pagesNumber != 1}">
                <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                    <%--@elvariable id="genreID" type="java.lang.Integer"--%>
                    <a href="${base}/do/?action=about-book&bookID=${book.id}&page=${i}"
                       class="link-style">${i}</a>
                </c:forEach>
            </c:if>
        </div>
        <div class="book-comments-section">
            <c:forEach items="${bookComments}" var="comment">
                <%--@elvariable id="comment" type="com.epam.adk.web.library.model.Comment"--%>
                <div>
                    <h4>${comment.user.firstname} ${comment.user.surname} (${comment.user.login})
                        <br><ftm:message key="time.field.message"/> <ftm:formatDate value="${comment.time}"/></h4>
                </div>
                <div>
                    <p>${comment.text}
                    <hr>
                </div>
            </c:forEach>
        </div>
    </c:if>
</t:page>
