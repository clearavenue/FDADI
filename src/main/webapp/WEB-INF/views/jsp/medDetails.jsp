<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>FDADI - Medication information}</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/resources" var="resources" />
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
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">FDADI - ${username}</a>
			</div>
		</div>
	</nav>
	<div class="container fdadi-template">
		<div class="row">
			<div class="panel panel-default">
				<div class="panel-heading">${medList.get(0).getBrandName() }</div>
				<div class="panel-body">
					${medList.get(0).getUsage() }
				</div>
			</div>
		</div>
	</div>

	<script src="${resources}/js/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="${resources}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${resources}/js/checklistbox.js" type="text/javascript"></script>


	<script>
		var meds = [
	                  <c:forEach var="medication" items="${medList}" varStatus="loop">
	                    "${medication.medicationName}" 
	                    <c:if test="${!loop.last}">,</c:if>
	                  </c:forEach>
	                ]

		$(document).ready(function() {

			$.each(meds, function(i, med) {
				if (med) {
					$('#medListBox').append('<li class="list-group-item" data-color="info">' + med + '</li>');
				}
			});

			makeCheckedListBox();
			
			$('#addMedByNameButton').click(function() {
				window.location.href = 'addMedByName';
			});

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