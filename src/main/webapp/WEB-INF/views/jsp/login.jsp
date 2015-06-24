<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
<title>FDADI Login</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:url value="/resources" var="resources"/>
<link href="${resources}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${resources}/css/fdadi.css" rel="stylesheet" media="screen">

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
		</div>
	</nav>
	<div class="container fdadi-template">
		<div class="jumbotron jumbotron-background">
			<h1 class="text-center">myMedications Login</h1>
		</div>
		<div class="row centerDiv">
			<div class="messageBox col-sm-10">
				<h5 class="text-center"><strong>New Users:</strong> Enter a username &amp; password and select Register.</h5>
				<h5 class="text-center"><strong>Current Users:</strong> Enter your username &amp; password and select Login.</h5>
			</div>
		</div>
		<div class="row centerDiv">
			<div class="panel panel-info col-sm-10">
				<div class="panel-body">
					<form data-toggle="validator" class="form-horizontal"  method="post" id="loginForm" role="form">
						<div class="form-group" id="usernameGroup">
							<label class="control-label col-sm-2" for="username">Username:</label>
							<div class="col-sm-10">
								<input type="text" pattern="^[a-zA-Z0-9_\.]+$" data-error="Valid characters include letters, numbers, underscore (_), and period (.)" class="form-control required" id="username" name="username" placeholder="Enter username" required>
								<div class="help-block with-errors"></div>
							</div>
						</div>
						<div class="form-group" id="pwdGroup">
							<label class="control-label col-sm-2" for="pwd">Password:</label>
							<div class="col-sm-10">
								<input type="password" data-minlength="4" data-error="Minimum of 6 characters" class="form-control required" id="pwd" name="pwd" placeholder="Enter password" required>
								<div class="help-block with-errors"></div>
							</div>
						</div>

						<div class="col-sm-offset-2 col-sm-10">
							<div>
								<table>
									<tr>
										<td><input class="form-control btn btn-primary loginButton" type="submit" id="registerButton" value="Register" ></td>
										<td><input class="form-control btn btn-primary loginButton" type="submit" id="loginButton" value="Login"></td>
									</tr>
								</table>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div id="warning" class="row">
			<div class="panel panel-danger">
				<div class="panel-body">
					<div>${errMsg}</div>
				</div>
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
	
	<script src="${resources}/js/jquery-2.1.4.min.js"></script>
	<script src="${resources}/js/bootstrap.min.js"></script>
	<script src="${resources}/js/validator.js"></script>

	<script>                     
		$(document).ready(function() {

			var noError = "<%= request.getParameter("loginError") %>";
			if (noError) {
				$('#warning').hide();
			} else {
				$('#warning').show();
			}
			
			// disable form submit on enter
			$('#loginForm').on("keyup keypress", function(e) {
				  var code = e.keyCode || e.which; 
				  if (code  == 13) {               
				    e.preventDefault();
				    return false;
				  }
				});
			
			$('#registerButton').click(function() {
			    $('#loginForm').attr("action", "register");     
			});
			$('#loginButton').click(function() {
			    $('#loginForm').attr("action", "processLogin");       
			});
		});
	</script>
</body>
</html>