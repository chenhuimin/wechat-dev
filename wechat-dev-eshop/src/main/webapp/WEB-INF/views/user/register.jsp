<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户注册</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/user/register" method="post">
		<fieldset>
			<legend><small>用户注册</small></legend>			
				<label for="userName">用户名:</label>				
				<input type="text" id="userName" name="userName" /> <br/>				
				<label for="loginName">密码:</label>				
				<input type="password" id="password" name="password" /> <br/>			
				<input id="submit_btn" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" type="button" value="返回" onclick="history.back()"/>
		</fieldset>
	</form>
</body>
</html>
