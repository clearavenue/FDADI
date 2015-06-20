<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
<title>FDADI - ${username}</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/resources" var="resources"/>
<link href="${resources}/css/bootstrap.min.css" rel="stylesheet" media="screen">

<!--[if lt IE 9]>
	<script src="${resources}/js/html5shiv.min.js"></script>
	<script src="${resources}/js/respond.min.js"></script>
<![endif]-->

<style type="text/css">
.center-text {
	height: 100%;
	width: 100%;
	text-align: center;
}
.required::after {
	content: "*";
	padding-left:1%;
}
.form-control {
	width:97%;
	float:left;
}
</style>
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<div class="center-text">
				<h1>FDADI</h1>
			</div>
		</div>
		<div class="row">
			<div class="panel panel-info col-sm-10 center-text">
			Welcome, ${username}
			</div>
		</div>
	</div>

	<script src="${resources}/js/jquery-2.1.4.min.js"></script>
	<script src="${resources}/js/bootstrap.min.js"></script>
</body>
</html>