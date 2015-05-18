<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>任务列表</title>
</head>

<body>
	<table>
		<thead><tr><th>用户名</th><th>密码</th><th>微信openID</th></tr></thead>
		<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.userName}</td>
				<td>${user.password}</td>
				<td>${user.openID}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
