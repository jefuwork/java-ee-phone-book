<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@page import="java.util.List"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="entities.User" %>
<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>All users</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	</head>
	<body class="w3-light-grey">
        
        <jsp:include page="<%= "../templates/menu.jsp" %>" />

		<div class="w3-main" style="margin-left:275px;">
	        <div class="w3-container w3-padding-16">
				<div class="w3-card-4 w3-center" style="margin-bottom:80px;">
		                <div class="w3-container w3-light-blue">
		                    <h2>Users</h2>
		                </div>
		                <c:choose>
						    <c:when test="${empty usernames}">
						        <jsp:include page="<%= "../templates/noUsersSign.jsp" %>" />
						    </c:when>
						    <c:otherwise>
						    	<ul class="w3-ul">
	        						<c:forEach items="${usernames}" var="username">
								        <li class="w3-hover-sand">
											<div class="w3-display-container">
												Username: <b>${username.userName}</b> email: <b>${username.email}</b>
												<span onclick="getConfirmationAndDeleteUser(this,'${username.email}')" class="w3-button w3-display-right">
													&times;
												</span>
	  										</div>
										</li>
								    </c:forEach>
								</ul>
						    </c:otherwise>
						</c:choose>
		            </div>
		        </div>
			</div>
		
		<jsp:include page="<%= "../templates/footer.jsp" %>" />
        
        <script>
        function getConfirmationAndDeleteUser(node, email){
        	var retVal = confirm("Do you want to delete account " + email + "?");
        	if( retVal == true ){
        		node.parentElement.parentElement.style.display='none';

    			$.ajax({
    				type: "POST",
    				url: "/mvc/users?del=" + email
    				})
              	return true;
            }
            else{
              	return false;
            }
        }
		</script>
    </body>
</html>