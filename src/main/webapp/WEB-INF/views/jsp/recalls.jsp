<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>FDADI - Recalls</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/" var="index" />
<c:url value="/resources" var="resources" />
<link href="${resources}/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" media="screen">
<link href="${resources}/css/fdadi.css" rel="stylesheet" type="text/css"
	media="screen">

<!--[if lt IE 9]>
	<script src="${resources}/js/html5shiv.min.js"></script>
	<script src="${resources}/js/respond.min.js"></script>
<![endif]-->

</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">FDADI -Recalls</a>
			</div>
		</div>
	</nav>
	<div class="container fdadi-template">
		<div class="row">
			<c:forEach var="recalls" items="${recallList}" varStatus="loop">
			<div class="panel panel-info">
				<div class="panel-heading">${recalls.get(0).getDrugName() }</div>
				<div class="panel-body">
					<c:forEach var="recall" items="${recalls}">
					<div class="panel panel-warning">
						<div class="panel-heading">Recall</div>
						<div class="panel-body">
							<div class="panel panel-default">
							    <div class="panel-heading">Drug description</div>
							    <div class="panel-body">${recall.getProductDescription()}</div>
							</div>
							
							<div class="panel panel-default">
							    <div class="panel-heading">Reason for recall</div>
							    <div class="panel-body">${recall.getReason()}</div>
							</div>
							
							<div class="panel panel-default">
							    <div class="panel-heading">Distribution pattern</div>
							    <div class="panel-body">${recall.getDistributionPattern()}</div>
							</div>
						</div>
					</div>
					</c:forEach>

				</div>
			</div>
			</c:forEach>
			
			<button type="button" id="backButton" class="btn btn-primary btn-block">Back</button>
		</div>
	</div>

    <nav id="footer" class="navbar navbar-default navbar-fixed-bottom">
		<div class="container">
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-center">
					<li>DISCLAIMER: This website is for demonstration purposes. Information in this website should not be taken as medical advice.</li>
				</ul>
			</div>	
		</div>
	</nav>
	
	<script src="${resources}/js/jquery-2.1.4.min.js"
		type="text/javascript"></script>
	<script src="${resources}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${resources}/js/checklistbox.js" type="text/javascript"></script>
	
	<script>
	    $('#backButton').click(function() {
		    window.location.href = ${index};
	    });
	</script>
</body>
</html>

<!-- 
var checkedItems = {}
		        $("#check-list-box li.active").each(function(idx, li) {
		            checkedItems[counter] = $(li).text();
		            counter++;
		        });
				
				$.post( "test" );

 -->