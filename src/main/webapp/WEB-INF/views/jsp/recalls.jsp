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
<c:url value="/logout" var="logout" />

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
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><img class="img-responsive" src="${resources}/img/clearAvenue_highres.jpg" alt="clearAvenue logo" /></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
				    <li><a href="#" id="disclaimerButton">Disclaimer</a></li>
					<li><a href="${logout}">logout</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container fdadi-template">
		<div class="row">
	    <c:choose>	
			<c:when test="${not empty recallList}">
			<c:forEach var="recalls" items="${recallList}" varStatus="loop">
			
				<div class="panel panel-primary">
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
			</c:when>
		    <c:otherwise>
		        <div class="panel panel-default">
		            <div class="panel-body">
		        		The selected medications have no ongoing recalls.
		            </div>
		        </div>
			</c:otherwise>
		</c:choose>
	    <button type="button" id="backButton" class="btn btn-primary">Back</button>
		</div>
	</div>

	<script src="${resources}/js/jquery-2.1.4.min.js"
		type="text/javascript"></script>
	<script src="${resources}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${resources}/js/checklistbox.js" type="text/javascript"></script>
	<script src="${resources}/js/bootbox.min.js" type="text/javascript"></script>
	<script>
	    $('#backButton').click(function() {
		    window.location.href = ${index};
	    });
	    
	    $('#disclaimerButton').click(function() {
			bootbox.alert("This website is for demonstration purposes. Information in this website should not be taken as medical advice.");
		});
	</script>
</body>
</html>