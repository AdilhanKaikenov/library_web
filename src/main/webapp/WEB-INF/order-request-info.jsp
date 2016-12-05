<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="category.page">
    <div align="center" style="margin: 30px">
        <c:if test="${not empty requestScope.sentRequestFailed}">
            <h1><ftm:message key="${requestScope.sentRequestFailed}"/></h1>
        </c:if>
        <c:if test="${not empty requestScope.sentRequestSuccessful}">
            <h1><ftm:message key="${requestScope.sentRequestSuccessful}"/></h1>
        </c:if>
        <br><a href="${pageContext.request.contextPath}/do/?action=about-book&id=${requestScope.bookID}"
               class="link-style"><ftm:message key="back.button"/></a>
    </div>
</t:page>