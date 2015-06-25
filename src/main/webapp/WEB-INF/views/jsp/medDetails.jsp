<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>myMedications - Medication information</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/" var="index" />
<c:url value="/resources" var="resources" />
<c:url value="/logout" var="logout" />
<c:url value="/faq" var="faq" />

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
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${index}"><img class="img-responsive" src="${resources}/img/clearAvenue_highres.jpg" alt="clearAvenue logo" /></a>
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
			<c:forEach var="med" items="${medList}" varStatus="loop">
			    <c:set var="shouldShowSideEffects" value="${not empty med.getSideEffects() && showSideEffects}"></c:set>
			    <c:set var="shouldShowUsage" value="${not empty med.getUsage() && showUsage}"></c:set>
			    <c:set var="shouldShowInteractions" value="${not empty med.getInteractions() && showInteractions}"></c:set>
			    <c:set var="shouldShowIndications" value="${not empty med.getIndications() && showIndications}"></c:set>
			    <c:set var="shouldShowCounterindications" value="${not empty med.getCounterindications() && showCounterindications}"></c:set>
			    
				
				<div class="panel panel-primary">
					<div class="panel-heading">${med.getBrandName() }(Generic name: ${med.getGenericName()})</div>
					
					<div class="panel-body">
					<c:choose>
					    <c:when test="${ shouldShowSideEffects || shouldShowUsage || shouldShowInteractions || shouldShowIndications || shouldShowCounterindications}">
						<c:if test="${shouldShowSideEffects}">
							<div class="panel panel-warning">
								<div class="panel-heading">Adverse Reactions</div>
								<div class="panel-body">${med.getSideEffects() }</div>
							</div>
						</c:if>

						<c:if test="${shouldShowUsage}">
							<div class="panel panel-default">
								<div class="panel-heading">Usage</div>
								<div class="panel-body">${med.getUsage() }</div>
							</div>
						</c:if>

						<c:if test="${shouldShowInteractions}">
							<div class="panel panel-default">
								<div class="panel-heading">Drug Interactions</div>
								<div class="panel-body">${med.getInteractions() }</div>
							</div>
						</c:if>

						<c:if test="${shouldShowIndications}">
							<div class="panel panel-default">
								<div class="panel-heading">Usage Indications</div>
								<div class="panel-body">${med.getIndications() }</div>
							</div>
						</c:if>

						<c:if test="${shouldShowCounterindications}">
							<div class="panel panel-warning">
								<div class="panel-heading">Counter-indications</div>
								<div class="panel-body">${med.getCounterindications() }</div>
							</div>
						</c:if>
                        </c:when>
					<c:otherwise>
					    No information.
					</c:otherwise>
					</c:choose>
					</div>
				</div>
			</c:forEach>

			<button type="button" id="backButton" class="btn btn-primary">Back</button>
		</div>
	</div>
	
	<script src="${resources}/js/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="${resources}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${resources}/js/checklistbox.js" type="text/javascript"></script>
    <script src="${resources}/js/bootbox.min.js" type="text/javascript"></script>
	<script>
		$('#backButton').click(function() {
			window.location.href = '${index}';
		});
		
		$('#disclaimerButton').click(function() {
			bootbox.alert("This website is for demonstration purposes. Information in this website should not be taken as medical advice.");
		});
	</script>
</body>
</html>
