<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Test</title>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
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

<link rel="shortcut icon" href="assets/ico/favicon.png">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/ico/apple-touch-icon-57-precomposed.png">
<style>
#products {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
	align: centre;
}

#products td, #products th {
	font-size: 1.1em;
	text-align: centre;
	border: 1px solid #98bf21;
	padding: 3px 7px 2px 7px;
}

#products th {
	font-size: 1.1em;
	text-align: centre;
	padding-top: 5px;
	padding-bottom: 4px;
	background-color: #A7C942;
	color: #ffffff;
}

#products tr.alt td {
	color: #000000;
	background-color: #EAF2D3;
}

#products button {
	background-color: #A7C942;
}

#productForm {
	visibility: hidden;
}
</style>
<script>
            
//             method="POST" action='my' name="frmAddUser"
			var json; 
            $(document).ready(function() {
//             	var form = $('<form name="login" method="post" action="service?pagename=request"/>').appendTo($('#products'));
                var table = $('<table class="table"/>').appendTo($('#products'));
                $('<tr/>').appendTo($('<thead/>').appendTo(table))
			  	.append($('<th/>').text("Id"))
			  	.append($('<th/>').text("Name"))
			  	.append($('<th/>').text("Quantity"))
			  	.append($('<th/>').text("threshold"))
			  	.append($('<th/>').text("Edit"));

//                 window.alert("before ajax");
                $.ajax({
                    type: "GET",
                    url: "service?action=showToAdmin",  //"/AjaxServletCalculator",
                    data: {
                    },
                    cache:false,
                    dataType: "json",
                    success: function (data) {
                    	debugger;
                    	json=data;
                        $(json).each(function(i, product) {
                            debugger;	
                            if(product.quantity<product.threshold)
                            	{
                                $('<tr class="danger"/><Strong/>').appendTo(table)
                                	.append($('<td class="danger"/>').text(product.id))
                                    .append($('<td class="danger"/>').text(product.name))
                                    .append($('<td class="danger"/>').text(product.quantity))
                                    .append($('<td class="danger"/>').text(product.threshold))
                                    .append($('<td><button type="submit" class="btn btn-info" id="'+i+'" onclick="displayFunction1(this.id)">Edit</button><button type="submit" class="btn btn-info" id="'+i+'" onclick="deleteFunction(this.id)">Delete</button></td>'));
                            	}
                            else{
                            	$('<tr/>').appendTo(table)
                            	.append($('<td/>').text(product.id))
                                .append($('<td/>').text(product.name))
                                .append($('<td/>').text(product.quantity))
                                .append($('<td/>').text(product.threshold))
                                .append($('<td><button type="submit" class="btn btn-info" id="'+i+'" onclick="displayFunction1(this.id)">Edit</button><button type="submit" class="btn btn-info" id="'+i+'" onclick="deleteFunction(this.id)">Delete</button></td>'));
                        	
                            	
                            }
                            });
                    }
                });
//                 $('<tr/>').appendTo(table)
//                 .append($('<td/><input type="button" class = "btn btn-info" value="Submit1"/>')).text("Submit");
//                 var btnOrder = $('<input type = "Submit" class=""  value="Submit" id ="btnOrder"/>').appendTo(form);
                var btnOrder = $('<button class="btn btn-info btn-lg" type="submit"  onclick="displayFunction()">Add Product</button>').appendTo($('#products'));
                var btnAddUser = $('<button class="btn btn-info btn-lg" type="submit"  onclick="AddNewUser()">Add New user</button>').appendTo($('#products'));
                var btnShowReport = $('<button class="btn btn-info btn-lg" type="submit"  onclick="getFilteredData()">View Transaction(s)</button>').appendTo($('#products'));
//                 var btnOrder = $('<button type="submit"  onclick="toggleVisibility('productForm');">Add Product</button>').appendTo($('#products'));
            });
            
            </script>
<script type="text/javascript">
function AddNewUser() {
	$('#userForm').replaceWith('<div id="userForm"></div>');
	var Text = $('<h3>Enter New Details</h3>').appendTo($('#userForm'));
	 var Pform = $('<form name="Userform" role="form" class="form-group" method="post" action="service?pagename=adduser"/>').appendTo($('#userForm'));
     var Ptable = $('<table class="table"/>').appendTo(Pform);

     $('<tr/>').appendTo(Ptable)
     .append($('<td/>').text("Username"))
     .append( $('<td><input type="text" name = "Uusername"></td>'));
     $('<tr/>').appendTo(Ptable)
     .append($('<td/>').text("Password"))
     .append( $('<td><input type="password" name = "Upassword"></td>'));
     $('<tr/>').appendTo(Ptable)
     .append($('<td/>').text("Employee ID"))
     .append( $('<td><input type="text" name = "UemployeeID"></td>'));
     $('<tr/>').appendTo(Ptable)
     .append($('<td/>').text("Privilege"))
     .append( $('<td><label class="radio-inline"><input type="radio" value="0" name="optradio">User</label><label class="radio-inline"><input type="radio" value="1" name="optradio">Admin</label></td>'));
     var PbtnOrder = $('<button type="submit">Submit</button>').appendTo(Pform);
// 	document.getElementById("productForm").style.visibility = 'visible';
}
        </script>

<script type="text/javascript">
 function ShowTransaction() {
	 $.ajax({
         type: "GET",
         url: "service?action=showReport",  //"/AjaxServletCalculator",
         data: {
         },
         cache:false,
         dataType: "json",
         success: function (data) {
        	 
        	 debugger;
        	 alert(data[0]);
        	 alert(data[1]);
        	 var Ctable = $('<table class="table table-hover"/>').appendTo($('#viewTransaction'));
        	 var trow = $('<tr/>').appendTo($('<thead/>').appendTo(Ctable))
        	 	     $(data[1]).each(function(i, colname) {
        	 	    	 trow.append($('<th/>').text(colname));
        	 	    	 
        	 	     });
        	 		$(data[0]).each(function(i,transaction){
        	 			var telement = $('<tr/>').appendTo(Ctable)
        	 			.append($('<td/>').text(transaction.username))
        	 			.append($('<td/>').text(transaction.time));
        	 			$(transaction.orderCount).each(function(j,count){
        	 				debugger;
        	 				telement.append($('<td/>').text(count));
        	 			});
        	 		});
        	 	     
                            }
        	 
	 });
	 
	}
 </script>
<script type="text/javascript">
 function getFilteredData(){
	 var Text = $('<h3>Enter New Details</h3>').appendTo($('#filterForm'));
	 var Pform = $('<form name="Userform" role="form" class="form-group" />').appendTo($('#filterForm'));
     var Ptable = $('<table class="table"/>').appendTo(Pform);

     $('<tr/>').appendTo(Ptable)
     .append($('<td/>').text("Username"))
     .append( $('<td><input type="text" id="username" name="user" value = ""></td>'));
     $('<tr/>').appendTo(Ptable)
     .append($('<td/>').text("Start Time"))
     .append( $('<td><input type="text" id = "stime" value="2015-08-9 23:20:50.52"></td>'));
     $('<tr/>').appendTo(Ptable)
     .append($('<td/>').text("End Time"))
     .append( $('<td><input type="text" id="etime" name="timestamp1" value="2015-08-9 23:20:50.52" step="1"></td>'));
     $('<tr/>').appendTo(Ptable)
     .append($('<td/>').text("Product Name"))
     .append( $('<td><input type="text" id="Pname" value="" name = ""></td>'));
    
    
     var PbtnOrder = $('<input type="button" value="Submit" id="chnpwd" onclick="getTransactions()"/>').appendTo(Pform);
	 
 }
 function getTransactions()
 {
	 var username = $('#username').val();
	 var stime = $('#stime').val();
	 var etime = $('#etime').val();
	 var product = $('#Pname').val();
	 console.log( $('#username').val);
	 console.log($('#username'));
	 debugger;
	 $.ajax({
         type: "GET",
         url: "service?action=showFilterTransaction",  //"/AjaxServletCalculator",
         data: {username:username,stime:stime,etime:etime,product:product
         },
         cache:false,
         dataType: "json",
         success: function (data) {
        	 alert(data[0]);
        	 alert(data[1]);
        	 var Ctable = $('<table class="table table-hover"/>').appendTo($('#viewTransaction'));
        	 var trow = $('<tr/>').appendTo($('<thead/>').appendTo(Ctable))
        	 	     $(data[1]).each(function(i, colname) {
        	 	    	 trow.append($('<th/>').text(colname));
        	 	    	 
        	 	     });
        	 		$(data[0]).each(function(i,transaction){
        	 			var telement = $('<tr/>').appendTo(Ctable)
        	 			.append($('<td/>').text(transaction.username))
        	 			.append($('<td/>').text(transaction.time));
        	 			$(transaction.orderCount).each(function(j,count){
        	 				debugger;
        	 				telement.append($('<td/>').text(count));
        	 			});
        	 		});
        	 	     
                            }
        	 
	 });
 }
 </script>
<script type="text/javascript">
        function displayFunction1( id) {
        $.ajax({
            type: "GET",
            url: "service?action=changeProduct",  //"/AjaxServletCalculator",
            data: {Id:id
            },
            cache:false,
            dataType: "json",
            success: function (data) {
            	debugger;
            	json=data;
            	 var Cform = $('<form role="form" class="form-group" name="changeProduct" method="post" action="service?pagename=editProduct"/>').appendTo($('#editForm'));
                 var Ctable = $('<table class="table table-hover"/>').appendTo(Cform);
                 $('<tr/>').appendTo($('<thead/>').appendTo(Ctable))
 			  	.append($('<th/>').text("Particulars"))
 			  	.append($('<th/>').text("Present Details"))
 			  	.append($('<th/>').text("Modified Details"))
                 $('<tr/>').appendTo(Ctable)
                 .append($('<td/>').text("Name"))
                 .append($('<td/>').text(data.name))
                 .append($('<input type="text" name = "Pname" value='+data.name+' readonly>'));
                 $('<tr/>').appendTo(Ctable)
                 .append($('<td/>').text("Present Quantity"))
                 .append($('<td/>').text(data.quantity))
                 .append($('<input type="text" name = "Pcount" value='+data.quantity+'>'));
                 $('<tr/>').appendTo(Ctable)
                 .append($('<td/>').text("Threshold"))
                 .append($('<td/>').text(data.threshold))
                 .append($('<input type="text" name = "Pthreshold" value='+data.threshold+'>'));
                 var idinput = $('<input type="hidden" name="Pid" value='+data.id+' />').appendTo(Cform);
                 var CbtnOrder = $('<button type="submit" class="btn btn-info">Submit</button>').appendTo(Cform);
            	document.getElementById("productForm").style.visibility = 'visible';
            }
        });
        
        }
        function deleteFunction(id) {
//          window.alert("alert"+ id);
         $.ajax({
             type: "GET",
             url: "service?action=deleteProduct",  //"/AjaxServletCalculator",
             data: {Id:id
             },
             cache:false,
             dataType: "json",
             success: function (data) {
            	 alert("Successful");
            	
             }
         });
         window.location.reload(true);
         
         }
        </script>
<script type="text/javascript">
    function displayFunction() {
    	var Text = $('<h3>Enter Product Details</h3>').appendTo($('#productForm'));
    	 var Pform = $('<form name="Productform" role="form" class="form-group" method="post" action="service?pagename=addproduct"/>').appendTo($('#productForm'));
         var Ptable = $('<table class="table"/>').appendTo(Pform);
         $('<tr/>').appendTo(Ptable)
         .append($('<td/>').text("Name"))
         .append( $('<td><input type="text" name = "Pname"</td>'));
         $('<tr/>').appendTo(Ptable)
         .append($('<td/>').text("Initial Count"))
         .append( $('<td><input type="text" name = "Pcount"</td>'));
         $('<tr/>').appendTo(Ptable)
         .append($('<td/>').text("Threshold"))
         .append( $('<td><input type="text" name = "Pthreshold"</td>'));
         
         var PbtnOrder = $('<button type="submit">Submit</button>').appendTo(Pform);
    	document.getElementById("productForm").style.visibility = 'visible';
    }
    	
    	
    
    
    </script>
<script>
    
    </script>
</head>
<body>
	<div class="row">
		<div class="col-sm-8 col-sm-offset-2 text">
			<h1>
				<strong>Inventory Management Application</strong>
			</h1>

		</div>
	</div>
	<div class="page-header">
		<div class="jumbotron">

			<h2>ADMIN LOGIN</h2>
		</div>
	</div>
	<c:if test="${fn:length(belowThresholds) gt 0}">

		<div class="alert alert-warning">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Warning!</strong> The following products are below threshold
			value:
			<div id="unique">
				<c:forEach items="${belowThresholds}" var="product">
					<c:out value="${product.getName()}" />
					<br />

				</c:forEach>
			</div>

		</div>
	</c:if>
	<c:if test="${fn:length(outOfStocks) gt 0}">
		<div id="alertForm" class="alert alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Out of Stock!</strong> The following products are out of
			stock:<br />
			<p>"${belowThreshold}"
			<p>
			<div id="unique">
				<c:forEach items="${outOfStocks}" var="product">
					<c:out value="${product.getName()}" />
					<br />

				</c:forEach>
			</div>
		</div>
	</c:if>
	<div id="productForm"></div>
	<div id="userForm"></div>
	<div id="filterForm"></div>
	<div id="editForm"></div>
	<div id="products"></div>

	<div id=viewTransaction></div>


</body>
</html>