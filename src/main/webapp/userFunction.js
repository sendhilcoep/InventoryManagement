
var username;
function changePassword() {
	//	window.alert("in the ready function");	
	var Id = 1;
	//	window.alert("in the script");
	var Name = $('#Uusername').val();
	var oldPassword = $('#Opassword').val();
	var newPassword = $('#Npassword').val();
	var newPassword1 = $('#Npassword1').val();
	debugger;
	//	window.alert("Name is "+Name);
	$.ajax({
		type : 'POST',
		data : {
			Name : Name,
			oldPassword : oldPassword,
			newPassword : newPassword,
			newPassword1 : newPassword1
		},
		url : 'service?pagename=changepwd',
		success : function(result) {
			$('#results').html(result);
		}
	})
};

var json;
$(document)
		.ready(
				function() {
					username = document.getElementById('currentUser').innerHTML;
					$('#uniqueUsername').val(username);
//					alert(username);
					var form = $(
							'<form name="login" method="post" action="service?pagename=request"/>')
							.appendTo($('#products'));
					var table = $(
							'<table class="table table-bordered table-striped"/>')
							.appendTo(form);
					$('<tr/>').appendTo($('<thead/>').appendTo(table))
							.append($('<th/>').text("Product ")).append(
									$('<th/>').text(
											"Enter the Required Quantity"));
					$
							.ajax({
								type : "GET",
								url : "service?action=showToUser", //"/AjaxServletCalculator",
								data : {},
								dataType : "json",
								cache : false,
								success : function(data) {
									json = data;
									$(json)
											.each(
													function(i, product) {
														$('<tr/>')
																.appendTo(
																		table)
																.append(
																		$(
																				'<td/>')
																				.text(
																						product.name))
																.append(
																		$('<td><input type="text" name = "quantity" class="form-control" value="0"</td>'));
													});
								}
							});
					var idinput = $(
							'<input type="hidden" name="username" value="'+username+'" />')
							.appendTo(form);
					var btnOrder = $('<button onclick="order()" type="submit">Order</button>')
							.appendTo(form);
					var btnPwd = $(
							'<button type="button" onclick="check()">Change Password</button>')
							.appendTo(form);
					
				});

function order(){
//	alert("hi");
//	username = document.getElementById('currentUser').innerHTML;
//	$('#uniqueUsername').val(username);
//	alert(username);
};


function getTransactions()
{
//	 var username = $('#username').val();
//	 var stime = $('#stime').val();
//	 var etime = $('#etime').val();
//	 var product = $('#Pname').val();
//	 window.alert($('#datetimepicker').val());
//	 console.log( $('#username').val);
//	 console.log($('#username'));
//	debugger;
	 $.ajax({
        type: "GET",
        url: "service?action=showUserTransaction",  //"/AjaxServletCalculator",
        data: {username:username
        },
        cache:false,
        dataType: "json",
        success: function (data) {
//        	debugger;
//        	window.alert(data);
//       	 debugger;
//        	 alert(data[0]);
//        	 alert(data[1]);
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
//       	 				debugger;
       	 				telement.append($('<td/>').text(count));
       	 			});
       	 		});
       	 		var PbtnOrder = $('<input type="button" value="Submit" id="exportExcel" onclick="exportToExcel()"/>').appendTo('#viewTransaction');
                           }
       	 
	 });
}
function check() {
	$('#changePassword').replaceWith('<div id="changePassword"></div>');
	var Text = $('<h3>Enter New Details</h3>').appendTo(
			$('#changePassword'));
	var Pform = $('<form name="Userform" role="form" class="form-group" />')
			.appendTo($('#changePassword'));
	var Ptable = $('<table class="table"/>').appendTo(Pform);

	$('<tr/>')
			.appendTo(Ptable)
			.append($('<td/>').text("Username"))
			.append(
					$('<td><input type="text" id="Uusername" value = "${username}"></td>'));
	$('<tr/>')
			.appendTo(Ptable)
			.append($('<td/>').text("Old Password"))
			.append(
					$('<td><input type="password" id="Opassword" value="" name = "Upassword"></td>'));
	$('<tr/>')
			.appendTo(Ptable)
			.append($('<td/>').text("New Password"))
			.append(
					$('<td><input type="password" id="Npassword" value="" name = "Npassword"></td>'));
	$('<tr/>')
			.appendTo(Ptable)
			.append($('<td/>').text("New Password"))
			.append(
					$('<td><input type="password" id="Npassword1" value="" name = "Npassword1"></td>'));

	var PbtnOrder = $(
			'<input type="button" value="Submit" id="chnpwd" onclick="changePassword()"/>')
			.appendTo(Pform);
}