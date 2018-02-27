//Spinner de\activation
$(document).ajaxStart(function(){
	$("#spinner").show();
}).ajaxStop(function(){
	$("#spinner").fadeOut('slow');
});

$(document).ready(function(){
	$("#phone").intlTelInput();
	$('#phone').val('');

	var totalRecords;
	var recordsPerPage = 10;
	var totalPages;
	var currentPage = 1;

	var inEditMode = false;
	
	// Live Search
	$('#searchString').keyup(function(e) {
		clearTimeout($.data(this, 'timer'));
		if (e.keyCode == 13)
			search(true);
		else
			$(this).data('timer', setTimeout(search, 500));
	});
	function search(force) {
		var existingString = $("#searchString").val();
		if (existingString.length == 0) {
			getRecords();
			updatePages();
			return;
		}
		if (!force && existingString.length < 2 && existingString.length != 0) return; //wasn't enter, not > 1 char
		$.get('/restEJB/numbers/search/' + existingString, function(data) {
			loadTable(data);
			$("#amountOfFoundRecords").show();
			var amountOfFoundRecords = Object.keys(data).length;
			$("#amountOfFoundRecords").html(amountOfFoundRecords);
			$("#paginationBar").css("opacity","0.3");
			$("#paginationSize").css("opacity", "0.3");
			inEditMode = false;
		});
	}

	// Add new record
	window.addRecord = function addRecord() {
		inEditMode = false;
		var fullname = $('#fullname').val();
		var address = $('#address').val();
		var email = $('#selectEmail').val();
		var phone = $('#phone').val();
		var isPrivate = $('#private').is(":checked")

		var validFullname = validateFullname(fullname);
		var validAddress = validateAddress(address);
		var validEmail = validateEmail(email);
		var validPhone = validatePhone(phone);

		ifInvalidDrawKhaki('#fullname', validFullname);
		ifInvalidDrawKhaki('#address', validAddress);
		ifInvalidDrawKhaki('#selectEmail', validEmail);
		ifInvalidDrawKhaki('#phone', validPhone);

		if (validFullname && validAddress && validEmail && validPhone) {
			$.ajax({
				type: 'POST',
				async: true,
				url: "/restEJB/numbers",
				contentType: 'application/x-www-form-urlencoded; charset=utf-8',
				data: { email: email, fullname: fullname, address: address, number: phone, isPrivate: isPrivate },
				success: function(data, textStatus, jqXHR){
					notify('record added successfully');
					$('#fullname').val('');
					$('#address').val('');
					$('#email').val('');
					$('#phone').val('');
					getRecords();
					updatePages();

					$('#addNewPhoneForm').hide();
				},
				error: function(jqXHR, textStatus, errorThrown){
					notifyError('add record error (Probably this phone is already exists) ');
				}
			});
		} else {
			notifyError('Some fields are invalid.');
		}
	}

	// Loads table with given JSON array + function declarations
	function loadTable(data) {
		inEditMode = false;
		// Loads table with given JSON array
		$('#tbody').html('');
		var list = data == null ? [] : (data instanceof Array ? data : [data]);
		if (list == []) {
			notifyError('There are no records!');
			return;
		}
		$.each(list, function (index, item) {
			var eachrow = "<tr>"
				+ "<td>" + item.phoneId + "</td>"
				+ "<td>" + item.fullname + "</td>"
				+ "<td>" + item.address + "</td>"
				+ "<td>" + item.email + "</td>"
				+ "<td>" + item.number + "</td>"
				+ "</tr>";
			$('#tbody').append(eachrow);
		});

		// Edit mode enter
		$("#tbody > tr").dblclick(function() {
			if (inEditMode == false) {
				$(this).addClass( "w3-orange" );
				var fullName = $("td:nth-child(2)",this).html();
				var address = $("td:nth-child(3)",this).html();
				var email = $("td:nth-child(4)",this).html();
				var phone = $("td:nth-child(5)",this).html();

				$( this ).find( "a:last" ).remove();
				$("td:nth-child(2)",this).html('<input class="w3-input" type="text" value=\"' + fullName + '\">');
				$("td:nth-child(3)",this).html('<input class="w3-input" type="text" value=\"' + address + '\">');
				$("td:nth-child(4)",this).html('<input class="w3-input" type="text" value=\"' + email + '\">');
				$("td:nth-child(5)",this).html('<input class="w3-input" type="text" value=\"' + phone + '\">');

				inEditMode = true;
			}
		});

		// Edit mode leave
		$("#tbody > tr").mouseleave(function() {
			if (inEditMode == true) {
				var idRecord = $("td",this).html();
				var fullNameNew = $("td:nth-child(2) > input",this).val();
				var addressNew = $("td:nth-child(3) > input",this).val();
				var emailNew = $("td:nth-child(4) > input",this).val();
				var phoneNew = $("td:nth-child(5) > input",this).val();

				var validFullname = validateFullname(fullNameNew);
				var validAddress = validateAddress(addressNew);
				var validPhone = validatePhone(phoneNew);
				var validEmail = validateEmail(emailNew);

				ifInvalidDrawKhaki('td:nth-child(2) > input', validFullname);
				ifInvalidDrawKhaki('td:nth-child(3) > input', validAddress);
				ifInvalidDrawKhaki('td:nth-child(4) > input', validEmail);
				ifInvalidDrawKhaki('td:nth-child(5) > input', validPhone);

				if (validFullname && validAddress && validEmail && validPhone) {
					$("td:nth-child(2) > input",this).replaceWith(fullNameNew);
					$("td:nth-child(3) > input",this).replaceWith(addressNew);
					$("td:nth-child(4) > input",this).replaceWith(emailNew);
					$("td:nth-child(5) > input",this).replaceWith(phoneNew);

					$.ajax({
						type: "PUT",
						url: "/restEJB/numbers/" + idRecord,
						contentType: 'application/x-www-form-urlencoded; charset=utf-8',
						data: { email: emailNew, fullname: fullNameNew, address: addressNew, number: phoneNew },
						success: function(data) {
							notify("update successfully");
						},
						error : function(jqXHR, textStatus, errorThrown) {
							notifyError('error in updating ' + jqXHR.responseText);
						},
						timeout: 120000,
					});

					inEditMode = false;
					$(this).fadeOut(700, function() {
						$(this).toggleClass("w3-orange", false);
					});
					$(this).fadeIn(500);
				}
			}
		});

		// Delete button appears
		$("#tbody > tr").hover(
				function() {
					if (inEditMode == false) {
						$( this ).append( $( "<a id=\"delete\" class=\"w3-hover-purple w3-button w3-red\" style=\"position:absolute;margin:5px;right:25px;\"><i class=\"fa fa-close\"></i></a>" ) );
						$("#tbody > tr > a").click(function() {
							var idRecord = $("td",this.parentElement).html();
							var retVal = confirm("Do you want to delete record " + idRecord + "?");
							if( retVal == true ){
								$(this).toggle( "highlight" );
								//this.parentElement.style.display='none';

								$.ajax({
									type: "DELETE",
									url: "/restEJB/numbers/" + idRecord,

									success: function(data) {
										getRecords();
										updatePages();
										notify('record was deleted');
									},
									error : function(jqXHR, textStatus, errorThrown) {
										notifyError('error in deleting ' + jqXHR.responseText);
									},
									timeout: 120000,
								});
							}
						});
					}
				}, function() {
					$( this ).find( "a:last" ).remove();
				}	
		);
	}

	// Update pages with actual amount of phones (no search)
	// and goes to last page - 1 if last page was deleted
	function updatePages() {
		$.get("/restEJB/numbers/amount", function(data){
			totalRecords = parseInt(data);

			totalPages = Math.floor(totalRecords / recordsPerPage);

			if(totalRecords % recordsPerPage != 0){
				totalPages++;
			}
			if (currentPage > totalPages) {
				currentPage = totalPages;
			}
			getRecords();
		});
	}

	countPages();
	updatePages();

	// Counts amount of pages and sets current page to 1
	function countPages() {
		$.ajax({
			type: 'GET',
			url: "/restEJB/numbers/amount",
			dataType: 'json',
			success: function(data, textStatus, jqXHR){
				totalRecords = parseInt(data);
				totalPages = Math.floor(totalRecords / recordsPerPage);
				if(totalRecords % recordsPerPage != 0){
					totalPages++;
				}
				currentPage = 1;
				getRecords();
			},
			error: function(jqXHR, textStatus, errorThrown){
				notifyError('Cannot connect with database ' + jqXHR.responseText);
			},
			timeout: 10000,
		});
	}
	
	function notifyError(message){
		$('#mainContainer').prepend("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">" +
            	"<span onclick=\"this.parentElement.style.display='none'\"" +
            	"class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-cyan w3-hover-border-grey\">×</span>\n" +
               	"<h5>" + message + "</h5></div>"
    )};
    
    function notify(message){
		$('#mainContainer').prepend("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">" +
            	"<span onclick=\"this.parentElement.style.display='none'\"" +
            	"class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-cyan w3-hover-border-grey\">×</span>\n" +
               	"<h5>" + message + "</h5></div>"
    )};

	// Gets current page from rest
	function getRecords() {
		$.get("/restEJB/numbers/page/" + currentPage + "/size/" + recordsPerPage, function(data){
			loadTable(data);
		});

		$("#searchString").val('');
		$("#paginationBar").css("opacity","1.0");
		$("#paginationSize").css("opacity", "1.0");	
		$("#amountOfFoundRecords").hide();
		$("#amountOfFoundRecords").html('');

		$("#firstPage").html(1);
		$("#curPage").html(currentPage);
		$("#lastPage").html(totalPages);

		if(currentPage == totalPages){
			$("#next").addClass("w3-disabled");
			$("#lastPage").removeClass("w3-sand");
			$("#lastPage").addClass("w3-green");
			if (currentPage != 1) {
				$("#curPage").removeClass("w3-green");
				$("#curPage").addClass("w3-white");
				$("#curPage").html(totalPages - 1);
			}
		} else {
			$("#next").removeClass("w3-disabled");
			$("#lastPage").addClass("w3-sand");
			$("#lastPage").removeClass("w3-green");
		}

		if(currentPage == 1){
			if (totalPages == 1) {
				$("#firstPage").hide();
				$("#lastPage").hide();
				$("#curPage").show();
				$("#curPage").addClass("w3-green");
				$("#curPage").removeClass("w3-white");
			} else if (totalPages == 2) {
				$("#firstPage").show();
				$("#lastPage").show();
				$("#curPage").hide();
				$("#firstPage").removeClass("w3-border-right");
				$("#firstPage").removeClass("w3-sand");
				$("#firstPage").addClass("w3-green");
			} else {
				$("#firstPage").show();
				$("#curPage").show();
				$("#lastPage").show();
				$("#firstPage").removeClass("w3-sand");
				$("#firstPage").addClass("w3-green");
				$("#firstPage").addClass("w3-border-right");
				$("#curPage").removeClass("w3-green");
				$("#curPage").addClass("w3-white");
				$("#curPage").html(2);
			}
			$("#back").addClass("w3-disabled");
		} else {
			$("#back").removeClass("w3-disabled");
			$("#firstPage").addClass("w3-sand");
			$("#firstPage").removeClass("w3-green");
			if(currentPage != totalPages){
				$("#curPage").addClass("w3-green");
				$("#curPage").removeClass("w3-white");
			}
		}
	}

	function clearAllBlackHover() {
		$("#size10").removeClass("w3-black");
		$("#size25").removeClass("w3-black");
		$("#size50").removeClass("w3-black");
		$("#size100").removeClass("w3-black");
		$("#size200").removeClass("w3-black");
	}

	$("#curPage").click(function() {
		if (totalPages != 1) {
			if (currentPage == 1) {
				currentPage++;
				getRecords();
			} else if (currentPage == totalPages) {
				currentPage--;
				getRecords();
			}
		}
	});

	$("#next").click(function(){
		if (currentPage != totalPages) {
			currentPage++;
			getRecords();
		}
	});

	$("#back").click(function(){
		if (currentPage != 1) {
			currentPage--;
			getRecords();
		}
	});

	$("#firstPage").click(function(){
		currentPage = 1;
		getRecords();
	});

	$("#lastPage").click(function(){
		currentPage = totalPages;
		getRecords();
	});

	$("#size10").click(function() {
		clearAllBlackHover();
		$("#size10").addClass("w3-black");
		recordsPerPage = 10;
		countPages();
	});

	$("#size25").click(function() {
		clearAllBlackHover();
		$("#size25").addClass("w3-black");
		recordsPerPage = 25;
		countPages();
	});

	$("#size50").click(function() {
		clearAllBlackHover();
		$("#size50").addClass("w3-black");
		recordsPerPage = 50;
		countPages();
	});

	$("#size100").click(function() {
		clearAllBlackHover();
		$("#size100").addClass("w3-black");
		recordsPerPage = 100;
		countPages();
	});

	$("#size200").click(function() {
		clearAllBlackHover();
		$("#size200").addClass("w3-black");
		recordsPerPage = 200;
		countPages();
	});
});