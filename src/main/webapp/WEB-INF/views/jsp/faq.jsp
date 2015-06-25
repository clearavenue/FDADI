<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>myMedications FAQ</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/resources" var="resources" />
<c:url value="/logout" var="logout" />
<c:url value="/" var="index" />

<link href="${resources}/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="${resources}/css/fdadi.css" rel="stylesheet" type="text/css" media="screen">

<!--[if lt IE 9]>
	<script src="${resources}/js/html5shiv.min.js"></script>
	<script src="${resources}/js/respond.min.js"></script>
<![endif]-->

</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${index}"><img class="img-responsive" src="${resources}/img/clearAvenue_highres.jpg" alt="clearAvenue logo"/></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${faq}">FAQ</a></li>
					<li><a href="#" id="disclaimerButton">Disclaimer</a></li>
					<li><a href="${logout}">Logout</a></li>
				</ul>
			</div>			
		</div>
	</nav>
	<div class="container fdadi-template">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-heading">myMedication FAQ</div>
					<div class="panel-body">
						<div style="max-height: 500px;overflow: auto">
							<dl>
								<dt>FAQ Question 1</dt>
								<dd>Answer 1</dd>
								<dt>FAQ Question 2</dt>
								<dd>Answer 2</dd>
								<dt>FAQ Question 3</dt>
								<dd>Answer 3</dd>
								<dt>FAQ Question 4</dt>
								<dd>Answer 4</dd>
							</dl>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	   	
	<script src="${resources}/js/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="${resources}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${resources}/js/checklistbox.js" type="text/javascript"></script>
    <script src="${resources}/js/bootbox.min.js" type="text/javascript"></script>

	<script>
		$(document).ready(function() {

			$('#disclaimerButton').click(function() {
				bootbox.alert("This website is for demonstration purposes. Information in this website should not be taken as medical advice.");
			});
			
		});
		
	</script>

</body>
</html>
