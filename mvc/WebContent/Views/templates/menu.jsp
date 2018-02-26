<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="Styles/w3.css">
        <title>Menu template</title>
    </head>

	<style>
	body,h1,h2,h3,h4,h5 {font-family: "Poppins", sans-serif}
	body {font-size:16px;}
	</style>

    <body class="w3-light-grey">
        
        <div class="w3-container w3-blue-grey w3-opacity w3-right-align">
	    	<h1>Phone Book</h1>
	    </div>
	        
        <!-- Sidebar/menu -->
		<nav class="w3-sidebar w3-blue-grey w3-collapse w3-top w3-large w3-padding" style="z-index:3;width:275px;font-weight:bold;" id="mySidebar"><br>
			<a href="javascript:void(0)" onclick="w3_close()" class="w3-button w3-hide-large w3-display-topleft" style="width:100%;font-size:22px;padding-top: 21px;padding-bottom: 20px;">Close Menu</a>
		  	<div class="w3-container w3-padding-24" style="margin-top: 38px;padding-right: 0px;">
		    	<div class="w3-display-container">
				    <b><c:out value="${pageContext.request.userPrincipal.name}" default="null username?" /></b>
    				<a href="/mvc/login" class="w3-button w3-display-right">
    					<i class="fa fa-close"></i>
					</a>
				</div>
		 	</div>
			<div class="w3-bar-block">
			 	<% request.setAttribute("isAdmin", request.isUserInRole("admin_role")); %>
				<c:if test='${requestScope.isAdmin}'>
				    <a href="/mvc/phones" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">Phones</a> 
			    	<a href="/mvc/users" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">Users</a> 
		    	</c:if>
		    	
		    	<% request.setAttribute("isUser", request.isUserInRole("user_role")); %>
				<c:if test='${requestScope.isUser}'>
			    	<a href="/mvc/phones" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">Phones</a> 
		    	</c:if>
			</div>
		</nav>
		
		<!-- Top menu on small screens -->
		<header class="w3-container w3-top w3-hide-large w3-blue-grey w3-xlarge" style="padding-top: 11px;padding-bottom: 11px;">
			<a href="javascript:void(0)" class="w3-button w3-blue w3-margin-right" onclick="w3_open()">☰</a>
		 	<div class="w3-display-right w3-margin-right"><h1>Phone Book</h1></div>
		</header>
		
		<!-- Overlay effect when opening sidebar on small screens -->
		<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>
		        
        <!-- After import content will be here -->
        
		<script>
		// Script to open and close sidebar
		function w3_open() {
		    document.getElementById("mySidebar").style.display = "block";
		    document.getElementById("myOverlay").style.display = "block";
		}
		 
		function w3_close() {
		    document.getElementById("mySidebar").style.display = "none";
		    document.getElementById("myOverlay").style.display = "none";
		}
		</script>
    </body>
</html>