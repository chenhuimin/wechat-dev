<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户绑定</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/user/bind" method="post">
		<fieldset>
			<legend><small>用户绑定</small></legend>
			    <input type="hidden" id="openID" name="openID" value="${param.openID}" >
				<label for="userName">用户名:</label>				
				<input type="text" id="userName" name="userName" /> <br/>				
				<label for="loginName">密码:</label>				
				<input type="password" id="password" name="password" /> <br/>			
				<input id="submit_btn" type="submit" value="登录"/>			
		</fieldset>
	</form>
</body>
</html>
