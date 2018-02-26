//Spinner de\activation
$(document).ajaxStart(function(){
	$("#spinner").show();
}).ajaxStop(function(){
	$("#spinner").fadeOut('slow');
});

$(document).ready(function(){
	// Loads table with given JSON array + function declarations
	function loadTable(data) {
		// Loads table with given JSON array
		$('#tbody').html('');
		var list = data == null ? [] : (data instanceof Array ? data : [data]);
		if (list.length == 0) {
			notifyError('There are no records for you!');
			return;
		}
		$.each(list, function (index, item) {
			var eachrow = "<tr>"
				+ "<td>" + item.phoneId + "</td>"
				+ "<td>" + item.fullname + "</td>"
				+ "<td>" + item.address + "</td>"
				+ "<td>" + item.number + "</td>"
				+ "</tr>";
			$('#tbody').append(eachrow);
		});
	}

	// Gets current page from rest
	function getRecords() {
		var email = $("#mySidebar > div.w3-container.w3-padding-24 > div > b").html();
		console.log(email);
		
		$.get("/restEJB/numbers/email/" + email, function(data){
			loadTable(data);
		});
	}
	
	getRecords();
	
	function notifyError(message){
		$('#mainContainer').prepend("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">" +
            	"<span onclick=\"this.parentElement.style.display='none'\"" +
            	"class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-cyan w3-hover-border-grey\">×</span>\n" +
               	"<h5>" + message + "</h5></div>"
    )};
});