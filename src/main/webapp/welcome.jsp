<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Test</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login Page - Inventory Management</title>

<!-- CSS -->
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="assets/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/form-elements.css">
<link rel="stylesheet" href="assets/css/style.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

<!-- Favicon and touch icons -->
<link rel="shortcut icon" href="assets/ico/favicon.png">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/ico/apple-touch-icon-57-precomposed.png">


</head>
<script type="text/javascript">
	
</script>
<script src="userFunction.js"></script>

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
				<a class="navbar-brand"
					href="http://getbootstrap.com/examples/jumbotron/#"><strong>IDeaS
					Revenue Management Solutions</strong></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
			
				<form class="navbar-form navbar-right">
					
					<div class="form-group">
						<h4><strong>Welcome</strong></h4> <h4 id="currentUser">${username}</h4>				
					</div>
					<div class="form-group">
						<button class="button">Transaction History</button>
					</div>
					<div class="form-group">
						<button class="float-left submit-button">Logout</button>
					</div>
					<div class="form-group">
						<button type="button" onclick="check()">Change Password</button>
					</div>
				</form>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</nav>
	
	<br /><br/><br/>
	<div class="container">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 text">
				<h1>
					<strong>Inventory Management Application</strong>
				</h1>
			</div>
		</div>
		<div class="page-header">
			<div class="jumbotron">
				<h2>USER LOGIN</h2>
				<h4>Your order was successfully executed.!</h4>
				<div class="jumbotron-left">
					<div class="btn-group">
						<form action="service?action=loginpage" class="inline"></form>
					</div>
				</div>
			</div>
			<div></div>

		</div>
		<div id="products"></div>
		<div id="changePassword"></div>
		<div id="results"></div>
		<div id="test">
			<%-- 		<p>"${username}"</p> --%>
			<input type="hidden" id="uniqueUsername" name="username" value="${username}" />
		</div>
	</div>
	<!-- 	<div id="identifyuser"> </div> -->
</body>
</html>