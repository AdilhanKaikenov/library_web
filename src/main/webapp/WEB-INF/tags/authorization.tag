<%@tag body-content="empty" pageEncoding="UTF-8"%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<form action="${base}/do/" method="post">
    <table>
        <input hidden="hidden" name="action" value="authorization">
        <tr>
            <td><ftm:message key="auth.form.login.field"/></td>
            <td><input type="text" name="login"></td>
        </tr>
        <tr>
            <td><ftm:message key="auth.form.password.field"/></td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td><a href="${base}/do/?action=registration"><ftm:message key="registration.page"/></a></td>
        </tr>
    </table>
        <tr align="center">
            <td colspan="2"><br><button type="submit"
                                         class="link-style"><ftm:message key="sign.in.button"/></button></td>
        </tr>
</form>