<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>FDADI - ${username}</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/resources" var="resources" />
<c:url value="/medDetails" var="medDetails"/>
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
			<div class="col-md-6">
				<div class="panel panel-info">
					<div class="panel-heading">Medication List</div>
					<div class="panel-body">
						<div style="max-height: 500px;overflow: auto">
							<ul id="medListBox" class="list-group checked-list-box">
							</ul>
						</div>
						<button type="button" id="clearButton" class="btn btn-sm btn-info">Clear</button>
					</div>
				</div>
			</div>
			<div class="col-md-6" style="display: inline-block">
				<button type="button" id="addMedByNameButton" class="btn btn-primary btn-block">Add Medication by Name</button>
				<button type="button" id="addMedByPClassButton" class="btn btn-primary btn-block">Add Medication by PharmClass</button>
				<button type="button" id="medDetails" class="btn btn-primary btn-block">Medicine Details</button>
				<button type="button" id="adverseDetails" class="btn btn-primary btn-block">Adverse Reactions</button>
			</div>
		</div>
	</div>

	<script src="${resources}/js/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="${resources}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${resources}/js/checklistbox.js" type="text/javascript"></script>
    <script src="${resources}/js/bootbox.min.js" type="text/javascript"></script>

	<script>
		var meds = [
	                  <c:forEach var="medication" items="${medList}" varStatus="loop">
	                    "${medication.medicationName}" 
	                    <c:if test="${!loop.last}">,</c:if>
	                  </c:forEach>
	                ]
		
		$(document).ready(function() {

			
			recalls = [
			                  <c:forEach var="recall" items="${recallList}" varStatus="loop">
			                    "${recall}" 
			                    <c:if test="${!loop.last}">,</c:if>
			                  </c:forEach>
			              ];
		    interactions = [
			                  <c:forEach var="interaction" items="${interactionList}" varStatus="loop">
			                      "${interaction}" 
			                      <c:if test="${!loop.last}">,</c:if>
			                  </c:forEach>
			              ];
		    
			message = 'Warning: '
		    if(recalls.length > 0){
				message += 'The following medications have ongoing recalls: ';
				for(med in recalls){
					message += recalls[med] + ", ";
				}
			}
			message += "<br />";
			if(interactions.length > 0){
				message += 'The following medications have interactions with others that you take: ';
				for(interaction in interactions){
					message += interactions[interaction] + ', ';
				}
			}

			if(message.length > 15){
		        bootbox.dialog({
			        message: message,
				    title: "Warning"
			    });
			}
			$.each(meds, function(i, med) {
				if (med) {
					$('#medListBox').append('<li class="list-group-item" data-color="info">' + med + '</li>');
				}
			});

			makeCheckedListBox();

			$('#clearButton').click(function() {
				$("#medListBox li.active").each(function(idx, li) {
					$(li).trigger('click');
		        });
			});
			
			$('#addMedByNameButton').click(function() {
				window.location.href = 'addMedByName';
			});
			
			$('#addMedByPClassButton').click(function() {
				window.location.href = 'addMedByPharmClass';
			});
			
			$('#medDetails').click(function() {
		        var form = document.createElement("form");
			    form.setAttribute("method", "post");
			    form.setAttribute("action", '${medDetails}');
	            var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
	            hiddenField.setAttribute("name", "medlist");
	            checkedMeds = '';
	            $("#medListBox li.active").each(function(idx, li) {
					checkedMeds += $(li).text() + ',';
		        });
	            alert(checkedMeds);
	            hiddenField.setAttribute("value", checkedMeds);
	            form.appendChild(hiddenField);
	    	    document.body.appendChild(form);
	    	    form.submit();
			});
			
			checkForRecallsOrDrugInteractions();

		});
		
		function checkForRecallsOrDrugInteractions() {
			if (${recallsOrInteractions}) {
				bootbox.dialog({
					  message: "The following was recalled: ${recalledMeds}",
					  title: "Drug Recall or Interaction Found"
					});
			}
		};
	</script>

</body>
</html>
