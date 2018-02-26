<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Styles/w3.css">
		<title>Login Page</title>
	</head>
	<body class="w3-light-grey">
    	
    	<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
        	<h2>Login to continue</h2>
    	</div>
    	
    	<%
    	try {
    	    if (request.getUserPrincipal().getName() != null) {
            	out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                	"   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                	"   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                   	"   <h5>User '" + request.getUserPrincipal().getName() + "' was logged out!</h5>\n" +
                   	"</div>");
            	session.invalidate();
        	}
    	} catch (Exception e) {
    	    
    	}
        %>
    	
    	<div class="w3-row">
			<div class="w3-col m3 w3-center"><p></p></div>
		  		<div class="w3-col m6">
				    <div class="w3-card-4 w3-margin">
				  		<div class="w3-container w3-teal">
				    		<h2>Login</h2>
				  		</div>
				  		<form class="w3-container" method="POST" action="j_security_check">
					    	<br>      
					    	<label class="w3-text-teal"><b>Email</b></label>
					    	<input class="w3-input w3-border w3-light-grey" name="j_username" type="text">
					    	<br>      
					    	<label class="w3-text-teal"><b>Password</b></label>
					    	<input class="w3-input w3-border w3-light-grey" name="j_password" type="password">
					    	<c:if test='${not empty param["Retry"]}'>
					    		<label class="w3-text-red"><b>Error occured, please try again.</b></label><br>
					    	</c:if>
					    	<br>
					    	<input class="w3-btn w3-teal" type="submit" value="Login">
					    	<input class="w3-btn" type="reset" value="Reset">
							<a class="w3-btn w3-grey" href="/mvc/register">Register</a>
					    	<br><br>
				  		</form>
					</div>
				</div>
	  		<div class="w3-col m3 w3-center"><p></p></div>
		</div>
					
	</body>
</html>