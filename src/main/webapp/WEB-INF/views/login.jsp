<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Insert title here</title>
</head>
<body>
	<h1>로그인</h1> 
	<form id="login" method="post"> 
		<p>ID</p> 
		<p><input type="text" name="id" value="" /></p> 
		<p>패스워드</p> 
		<p><input type="password" name="pw" value="" /></p> 
		<p><button type="submit">로그인 요청</button></p> 
	</form> 
</body>
</html>