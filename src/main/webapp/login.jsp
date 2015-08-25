<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Insert title here</title>
<style>
/* .col-centered{ */
/*     float: none; */
/*     margin: 0 auto; */
/* } */
</style>
</head>
<body>
<div class="page-header">
	<h1>Inventory Management System</h1>
</div>

	<form name="login" method="post" action="service" role="form">
	<input type="hidden" name="pagename" value="login"/>
	<div class="form-group">
		
			<label class="control-label col-sm-2" for="email">username:</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="pwd" name="txtUserName"
					placeholder="Enter username"><br/>
			</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="pwd">Password:</label>
		<div class="col-sm-10">
			<input type="password" class="form-control" id="pwd"
				name="txtPassword" placeholder="Enter password">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			
		</div>
	</div>
	<div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default">Login</button>
      </div>
	</form>

	<!-- <div class="form-group"> -->
<!-- <form name="login" method="post" action="service" role="form"> -->
<!-- <input type="hidden" name="pagename" value="login"/> -->
<!-- <!-- <table class="table"> --> 
<!-- 	<tr> -->
<!-- 		<td>User Name</td> -->
<!-- 		<td><input type="text" name="txtUserName"/></td> -->
<!-- 	</tr> -->
<!-- 		<tr> -->
<!-- 		<td>Password</td> -->
<!-- 		<div class="col-sm-6">           -->
<!--         <td><input type="password" class="form-control" id="pwd" placeholder="Enter password" name="txtPassword"></td> -->
<!--       </div> -->
<!-- <!-- 		<td><input type="password" name="txtPassword"/></td> --> 
<!-- 	</tr> -->
<!-- 		<tr> -->
<!-- 		<td><button type="submit">Login</button></td> -->
<!-- 		<td><label></label></td> -->
<!-- 	</tr> -->
<!-- </table> -->
<!-- </form> -->
</body>
</html>