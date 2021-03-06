<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>myMedications - ${username}</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/resources" var="resources" />
<c:url value="/medDetails" var="medDetails" />
<c:url value="/recalls" var="recalls" />
<c:url value="/removeMeds" var="removeMeds" />
<c:url value="/logout" var="logout" />
<c:url value="/checkInteractions" var="checkInteractions" />
<c:url value="/faq" var="faq" />

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
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><img class="img-responsive"
					src="${resources}/img/clearAvenue_highres.jpg"
					alt="clearAvenue logo" /></a>
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
		<div class="row" style="padding-bottom: 10px;">
			<div class="appName">
				<h2>Medication Information and Drug Interactions for
					${username}</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-info">
					<div class="panel-heading">Medication List</div>
					<div class="panel-body">
						<div style="max-height: 500px; overflow: auto">
							<ul id="medListBox" class="list-group checked-list-box">
							</ul>
						</div>
						<button type="button" id="clearButton" class="btn btn-sm btn-info">Uncheck</button>
						<button type="button" id="removeButton"
							class="btn btn-sm btn-info">Remove</button>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<button type="button" id="addMedByNameButton"
					class="btn btn-primary actionButton">Add Medication by
					Name</button>
				<button type="button" id="addMedByPClassButton"
					class="btn btn-primary actionButton">Add Medication by
					PharmClass</button>
				<button type="button" id="medDetails"
					class="btn btn-primary actionButton">Medicine Details</button>
				<button type="button" id="adverseDetails"
					class="btn btn-primary actionButton">Adverse Reactions</button>
				<button type="button" id="interactionDetails"
					class="btn btn-primary actionButton">Check for Drug
					Interactions</button>
				<button type="button" id="recalls"
					class="btn btn-primary actionButton">Recalls</button>
			</div>
		</div>
	</div>


	<script src="${resources}/js/jquery-2.1.4.min.js"
		type="text/javascript"></script>
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
				message = message.substring(0, message.length - 2);
			}
			message += "<br />";
			if(interactions.length > 0){
				message += 'The following medications have interactions with others that you take: ';
				for(interaction in interactions){
					message += interactions[interaction] + ', ';
				}
				message = message.substring(0, message.length - 2);
			}

			if(message.length > 15 && ${showAlert}){
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
			
			$('#disclaimerButton').click(function() {
				bootbox.alert("This website is for demonstration purposes. Information in this website should not be taken as medical advice.");
			});
			
			$('#removeButton').click(function() {
		        var form = document.createElement("form");
			    form.setAttribute("method", "post");
			    form.setAttribute("action", '${removeMeds}');
			    
			    var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
	            hiddenField.setAttribute("name", "medlist");
	            checkedMeds = '';
	            $("#medListBox li.active").each(function(idx, li) {
					checkedMeds += $(li).text() + '---,---';
		        });

	            if(checkedMeds.length > 1){
	                hiddenField.setAttribute("value", checkedMeds);
	                form.appendChild(hiddenField);
	    	        document.body.appendChild(form);
	    	        form.submit();
	            }
			});
			
			$('#addMedByNameButton').click(function() {
				window.location.href = 'addMedByName';
			});
			
			$('#addMedByPClassButton').click(function() {
				window.location.href = 'addMedByPharmClass';
			});
			
			function showDetails(form){
				var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
	            hiddenField.setAttribute("name", "medlist");
	            checkedMeds = '';
	            $("#medListBox li.active").each(function(idx, li) {
					checkedMeds += $(li).text() + '---,---';
		        });

	            if(checkedMeds.length > 1){
	                hiddenField.setAttribute("value", checkedMeds);
	                form.appendChild(hiddenField);
	    	        document.body.appendChild(form);
	    	        form.submit();
	            }
			};
			
			function detailsOnly(section){
				var form = document.createElement("form");
			    form.setAttribute("method", "post");
			    form.setAttribute("action", '${medDetails}');
			    
			    var attributes = ["showSideEffects", "showUsage", "showIndications", "showInteractions", "showCounterindications"];
	            for(att in attributes){
	            	var newHiddenField = document.createElement("input");
	            	newHiddenField.setAttribute("type", "hidden");
	            	newHiddenField.setAttribute("name", attributes[att]);
	            	newHiddenField.setAttribute("value", attributes[att] == section);
	            	form.appendChild(newHiddenField);
	            }

	            showDetails(form);
			}
			
			$('#medDetails').click(function() {
		        var form = document.createElement("form");
			    form.setAttribute("method", "post");
			    form.setAttribute("action", '${medDetails}');
			    
			    var attributes = ["showSideEffects", "showUsage", "showIndications", "showInteractions", "showCounterindications"];
	            for(att in attributes){
	            	var newHiddenField = document.createElement("input");
	            	newHiddenField.setAttribute("type", "hidden");
	            	newHiddenField.setAttribute("name", attributes[att]);
	            	newHiddenField.setAttribute("value", "true");
	            	form.appendChild(newHiddenField);
	            }

	            showDetails(form);
			});
			
			$('#adverseDetails').click(function() {
		        detailsOnly('showSideEffects');
			});

			$('#interactionDetails').click(function(){
				checkedMeds = '';
				numCheckedMeds = 0;
	            $("#medListBox li.active").each(function(idx, li) {
					checkedMeds += $(li).text() + '---,---';
					numCheckedMeds++;
		        });
	            if(numCheckedMeds > 1){
	            	$.post( "${checkInteractions}", { medList: checkedMeds }, function( data ) {
	            	    if(data == 'true'){
	            	    	message = '<p class="text-danger">The selected medications have interactions with each other.</p>';
	            	    	buttonClass = "btn-primary";
	            	    	otherButtonClass = "hidden";
	            	    }else{
	            	    	message = '<p class="text-success">The selected medications do not interact with each other.</p>';
	            	    	buttonClass = "hidden";
	            	    	otherButtonClass = "btn-primary";
	            	    }
	            	    bootbox.dialog({
	    			        message: message,
	    				    title: "Check for Interactions Result",
	    				    buttons: {
	    				        details: {
	    				            label: "Details",
	    				    	    className: buttonClass,
	    				    	    callback: function(){
	    				    	    	detailsOnly('showInteractions');
	    				    	    }
	    				        },
	    				        ignore: {
	    				        	label: "Ignore",
	    				        	className: buttonClass
	    				        },
	    				        ok: {
	    				        	label: "Ok",
	    				        	className: otherButtonClass
	    				        }
	    				    }
	    			    });
	            	}).fail(function(error){
	            		bootbox.dialog({
	    			        message: "There was an error connecting to the server.",
	    				    title: "Error"
	    				    
	    			    });
	            	});
	            }
			});
			
			$('#recalls').click(function(){
				var form = document.createElement("form");
			    form.setAttribute("method", "post");
			    form.setAttribute("action", '${recalls}');
			    
			    var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
	            hiddenField.setAttribute("name", "medlist");
	            checkedMeds = '';
	            $("#medListBox li.active").each(function(idx, li) {
					checkedMeds += $(li).text() + '---,---';
		        });

	            if(checkedMeds.length > 1){
	                hiddenField.setAttribute("value", checkedMeds);
	                form.appendChild(hiddenField);
	    	        document.body.appendChild(form);
	    	        form.submit();
	            }
	            
			})
		});
		
	</script>

</body>
</html>
