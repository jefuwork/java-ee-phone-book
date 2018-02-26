<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>TITLE CHANGE</title>
	</head>
	<body class="w3-light-grey">
        <jsp:include page="<%= "../templates/menu.jsp" %>" />

		<div class="w3-main" style="margin-left:275px;">
	        <div class="w3-container w3-padding-16">
				<div class="w3-card-4 w3-center">
		        	<div class="w3-container w3-light-blue">
		            	<h2>TITLE INSIDE CHANGE +color?</h2>
		            </div>
				    
				    <!-- CONTENT GOES HERE -->
				    
		        </div>
		    </div>
		</div>
		<jsp:include page="<%= "../templates/footer.jsp" %>" />
    </body>
</html>