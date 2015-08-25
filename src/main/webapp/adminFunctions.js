var json; 
$(document).ready(function() {
//	document.getElementById('fillForm').reset();
//	var form = $('<form name="login" method="post" action="service?pagename=request"/>').appendTo($('#products'));
	var table = $('<table class="table"/>').appendTo($('#products'));
	$('<tr/>').appendTo($('<thead/>').appendTo(table))
	.append($('<th/>').text("Id"))
	.append($('<th/>').text("Name"))
	.append($('<th/>').text("Quantity"))
	.append($('<th/>').text("threshold"))
	.append($('<th/>').text("Edit"));

//	window.alert("before ajax");
	$.ajax({
		type: "GET",
		url: "service?action=showToAdmin",  //"/AjaxServletCalculator",
		data: {
		},
		cache:false,
		dataType: "json",
		success: function (data) {
//			debugger;
			json=data;
			$(json).each(function(i, product) {
//				debugger;	
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
	var btnOrder = $('<button class="btn btn-info btn-lg" type="submit"  onclick="displayFunction()">Add Product</button>').appendTo($('#products'));
	var btnAddUser = $('<button class="btn btn-info btn-lg" type="submit"  onclick="AddNewUser()">Add New user</button>').appendTo($('#products'));
	var btnShowReport = $('<button class="btn btn-info btn-lg" type="submit"  onclick="getFilteredData()">View Transaction(s)</button>').appendTo($('#products'));
	var cssId = 'myCss';  // you could encode the css path itself to generate id..
	if (!document.getElementById(cssId))
	{
		var head  = document.getElementsByTagName('head')[0];
		var link  = document.createElement('link');
		link.id   = cssId;
		link.rel  = 'stylesheet';
		link.type = 'text/css';
		link.href = 'http://website.com/css/stylesheet.css';
		link.media = 'all';
		head.appendChild(link);
	}
});


function AddNewUser() {
	$('#fillForm').replaceWith('<div id="fillForm"></div>');
	var Text = $('<h3>Enter New User Details</h3>').appendTo($('#fillForm'));
	var Pform = $('<form name="Userform" role="form" class="form-group" method="post" action="service?pagename=adduser"/>').appendTo($('#fillForm'));
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
}

function ShowTransaction() {
	$.ajax({
		type: "GET",
		url: "service?action=showReport",  //"/AjaxServletCalculator",
		data: {
		},
		cache:false,
		dataType: "json",
		success: function (data) {

//			debugger;
//			alert(data[0]);
//			alert(data[1]);
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
//					debugger;
					telement.append($('<td/>').text(count));
				});
			});

		}

	});

}
function getFilteredData(){
	$('#fillForm').replaceWith('<div id="fillForm"></div>');
	var Text = $('<h3>Enter New Details</h3>').appendTo($('#fillForm'));
	var Pform = $('<form name="Userform" role="form" class="form-group" />').appendTo($('#fillForm'));
	var Ptable = $('<table class="table"/>').appendTo(Pform);
//	var date = new Date();
//	window.alert(date);
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
	$('<tr/>').appendTo(Ptable)
	.append($('<td/>').text("Product Name"))
	.append( $('<div class="well"><div id="datetimepicker" class="input-append date"><input data-format="MM/dd/yyyy HH:mm:ss PP" type="text"></input><span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span></div></div>'));

	var PbtnOrder = $('<input type="button" class"btn-btn-info" value="Submit" id="chnpwd" onclick="getTransactions()"/>').appendTo(Pform);

}
function getTransactions()
{
	var username = $('#username').val();
	var stime = $('#stime').val();
	var etime = $('#etime').val();
	var product = $('#Pname').val();
//	window.alert($('#datetimepicker').val());
//	console.log( $('#username').val);
//	console.log($('#username'));
//	debugger;
	$.ajax({
		type: "GET",
		url: "service?action=showFilterTransaction",  //"/AjaxServletCalculator",
		data: {username:username,stime:stime,etime:etime,product:product
		},
		cache:false,
		dataType: "json",
		success: function (data) {
//			alert(data[0]);
//			alert(data[1]);
			$('#viewTransaction').replaceWith('<div id="viewTransaction"></div>');
			var Ctable = $('<table id="transactionTable" class="table table-hover"/>').appendTo($('#viewTransaction'));
			var trow = $('<tr/>').appendTo($('<thead/>').appendTo(Ctable))
			$(data[1]).each(function(i, colname) {
				trow.append($('<th/>').text(colname));

			});
			$(data[0]).each(function(i,transaction){
				var telement = $('<tr/>').appendTo(Ctable)
				.append($('<td/>').text(transaction.username))
				.append($('<td/>').text(transaction.time));
				$(transaction.orderCount).each(function(j,count){
//					debugger;
					telement.append($('<td/>').text(count));
				});
			});
			var PbtnOrder = $('<input type="button" class="btn btn-info" value="Submit" id="exportExcel" onclick="exportToExcel()"/>').appendTo('#viewTransaction');
			var PbtnOrder1 = $('<input type="button" class="btn btn-info" value="Get From Servlet" id="exportExcelServlet" onclick="getExcelTableFromServlet()"/>').appendTo('#viewTransaction');
		}

	});
}

function getExcelTableFromServlet(){
	window.open(("service?action=showFilterTransaction&page=Excel"), "_blank");
}

function displayFunction1( id) {
	$.ajax({
		type: "GET",
		url: "service?action=changeProduct",  //"/AjaxServletCalculator",
		data: {Id:id
		},
		cache:false,
		dataType: "json",
		success: function (data) {
//			debugger;
			json=data;
			$('#fillForm').replaceWith('<div id="fillForm"></div>');
			var Cform = $('<form role="form" class="form-group" name="changeProduct" method="post" action="service?pagename=editProduct"/>').appendTo($('#fillForm'));
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
		}
	});

}
function deleteFunction(id) {
//	window.alert("alert"+ id);
	$.ajax({
		type: "POST",
		url: "service?pagename=deleteProduct",  //"/AjaxServletCalculator",
		data: {Id:id
		},
		cache:false,
		dataType: "json",
		success: function (data) {
			alert(data);
//			window.location.reload(true);
		},
		failure:function(){
//			window.location.reload(true);
		}
		
	});
	location.reload();

}
function displayFunction() {
	$('#fillForm').replaceWith('<div id="fillForm"></div>');
	var Text = $('<h3>Enter Product Details</h3>').appendTo($('#fillForm'));
	var Pform = $('<form name="Productform" role="form" class="form-group" method="post" action="service?pagename=addproduct"/>').appendTo($('#fillForm'));
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
}

function exportToExcel() {
	var script="table2excel.js";
	$("head").append('<script type="text/javascript" src="' + script + '"></script>');
//	alert("IN function");
	$("#transactionTable").table2excel({
		name: "report.xls"
	});
};