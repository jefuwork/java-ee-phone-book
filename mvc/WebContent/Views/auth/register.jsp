<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Styles/w3.css">
		<script src="js/validation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
		<title>Register</title>
	</head>
	<body class="w3-light-grey">
    	<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
        	<h2>Register new user</h2>
    	</div>
						
		<%
		try {
		    Cookie[] cookies = request.getCookies();
	
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if (cookie.getName().equals("emailTaken")) {
		                out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"
		                        + "<span onclick=\"this.parentElement.style.display='none'\"\n"
		                        + "class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n"
		                        + "<h5>Email '" + cookie.getValue() + "' was already taken!</h5>\n" + "</div>");
		                cookie.setMaxAge(0);
		                response.addCookie(cookie);
		            }
		        }
		    }
		} catch (Exception e) {
	
		}
		%>
				            	
    	<div class="w3-row">
			<div class="w3-col m3 w3-center"><p></p></div>
		  		<div class="w3-col m6">
					<div class="w3-card-4 w3-margin">
				  		<div class="w3-container w3-indigo">
				    		<h2>Register</h2>
				  		</div>
				  		
				  		<form id="registration" class="w3-container" method="POST" onsubmit="return registerCheck()">
					    	<br>      
					    	<label class="w3-text-indigo"><b>Username</b></label>
					    	<input class="w3-input w3-border w3-light-indigo" id="username" name="username" type="text">
					    	<br>
					    	<label class="w3-text-indigo"><b>Email</b></label>
					    	<input class="w3-input w3-border w3-light-indigo" id="email" name="email" type="text">
					    	<br>      
					    	<label class="w3-text-indigo"><b>Password</b></label>
					    	<input class="w3-input w3-border w3-light-indigo" id="password" name="password" type="password">
					    	<br>
					    	<button class="w3-btn w3-indigo" type="submit">Register and Login</button>
					    	<input class="w3-btn" type="reset" value="Reset">
					    	<a class="w3-btn w3-grey" href="/mvc/login">Login</a>
					    	<br><br>
				  		</form>
					</div>	
				</div>
	  		<div class="w3-col m3 w3-center"><p></p></div>
		</div>
		<script>
		function registerCheck() {
			var username = $('#username').val();
	    	var email = $('#email').val();
	    	var password = $('#password').val();
	    	
	    	var validUsername = validateUsername(username);
	    	var validEmail = validateEmail(email);
	    	var validPass = validatePassword(password);

	    	ifInvalidDrawKhaki('#username', validUsername);
	    	ifInvalidDrawKhaki('#email', validEmail);
	    	ifInvalidDrawKhaki('#password', validPass);
	    	
	    	if (validUsername && validEmail && validPass) {
	    		return true;
	    	} else {
	    		return false;
	    	}
		}
		</script>
	</body>
</html>