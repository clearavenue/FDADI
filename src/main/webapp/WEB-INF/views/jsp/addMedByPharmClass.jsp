<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>FDADI - ${username} - Add Med By PharmClass</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/" var="index" />
<c:url value="/processAddMedByPharmClass" var="processAddMedByPharmClass" />
<c:url value="/resources" var="resources" />
<c:url value="/logout" var="logout" />

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
				<a class="navbar-brand" href="#"><img class="img-responsive" src="${resources}/img/clearAvenue_highres.jpg" alt="clearAvenue logo"/></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${logout}">logout</a></li>
				</ul>
			</div>			
		</div>
	</nav>
	<div class="container fdadi-template">
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-info">
					<div class="panel-heading">All Pharmaceutical Class List</div>
					<div class="panel-body">
						<div style="max-height: 500px;overflow: auto">
							<ul id="pharmListBox" class="list-group checked-list-box">
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<button type="button" id="getButton" class="btn btn-primary actionButton">Get Medications for PharmClass</button>
				<button type="button" id="clearButton" class="btn btn-primary actionButton">Clear</button>
				<button type="button" id="cancelButton" class="btn btn-primary actionButton">Cancel</button>
			</div>
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
	<script src="${resources}/js/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="${resources}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${resources}/js/checklistbox.js" type="text/javascript"></script>


	<script>

	var pharmClasses = [
					<c:forEach var="pharmClass" items="${allPharmClasses}" varStatus="loop">
					"${pharmClass}" 
					<c:if test="${!loop.last}">,</c:if>
					</c:forEach>
              ]

	$(document).ready(function() {

		$.each(pharmClasses, function(i, pc) {
			if (pc) {
				$('#pharmListBox').append('<li class="list-group-item" data-color="info">' + pc + '</li>');
			}
		});

		makeCheckedListBox();
		
		$('#getButton').click(function() {
			var checkedItems = [];
	        $("#pharmListBox li.active").each(function(idx, li) {
	            checkedItems.push($(li).text());
	        });

	        var form = document.createElement("form");
		    form.setAttribute("method", "post");
		    form.setAttribute("action", '${processAddMedByPharmClass}');
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "pharmclasses");
            hiddenField.setAttribute("value", checkedItems.toString());
            form.appendChild(hiddenField);
    	    document.body.appendChild(form);
    	    form.submit();
		});
		
		$('#cancelButton').click(function() {
			window.location.href = "${index}";
		});

		$('#clearButton').click(function() {
			$("#pharmListBox li.active").each(function(idx, li) {
				$(li).trigger('click');
	        });
		});

	});
	</script>

</body>
</html>