<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="<c:url value='/meals'/>" class="navbar-brand"><spring:message code="app.title"/></a>
        <div class="collapse navbar-collapse">
            <form:form class="navbar-form navbar-right" action="logout" method="post">
                <sec:authorize access="isAuthenticated()">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a class="btn btn-info" role="button" href="users"><spring:message code="users.title"/></a>
                    </sec:authorize>
                    <a class="btn btn-info" role="button" href="profile">${userTo.name} profile</a>
                    <input type="submit" class="btn btn-primary" value="<spring:message code="app.logout"/>">
                </sec:authorize>
            </form:form>

            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> ${pageContext.response.locale}<b
                        class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a onclick="show('en')">English</a></li>
                    <li><a onclick="show('ru')">Русский</a></li>
                </ul>
            </li>
            <script type="text/javascript">
                function show(lang) {
                    window.location.href = window.location.href.split('?')[0] + '?lang=' + lang;
                }
            </script>
        </div>
    </div>
</div>
