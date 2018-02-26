<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
		<link rel="stylesheet" href="Styles/intlTelInput.css">
		<script src="js/intlTelInput.js"></script>
		<script src="js/validation.js"></script>
		<title>Phones</title>
		<style type="text/css">
		table {
		    border-left-width: 0px !important;
		    border-right-width: 0px !important;
		}
		</style>
	</head>
	<body class="w3-light-grey">
        <% request.setAttribute("isAdmin", request.isUserInRole("admin_role")); %>
		<c:if test='${requestScope.isAdmin}'>
	    <script src="js/admin.js"></script>
		</c:if>
		
		<% request.setAttribute("isUser", request.isUserInRole("user_role")); %>
		<c:if test='${requestScope.isUser}'>
		<script src="js/user.js"></script>
		</c:if>
                
        <jsp:include page="<%= "templates/menu.jsp" %>" />

		<div class="w3-main" style="margin-left:275px;margin-bottom:80px;">
	        <div id="mainContainer" class="w3-container w3-padding-16">
				<div class="w3-card-4 w3-center">
		        	<div class="w3-container w3-teal">
		            	<h2>Phones</h2>
		            	<i id="spinner" class="fa fa-cog fa-spin" style="font-size:24px;position:absolute;right:40px;top:112px;opacity:0.2;"></i>
		            </div>
		            
					<c:if test='${requestScope.isAdmin}'>
		            <div class="w3-container w3-padding">
			            <input id="searchString" class="w3-input w3-left" type="text" placeholder="Search something..." style="width:314px;">
			            <div class="w3-left" style="position:relative;"><div id="amountOfFoundRecords" class="w3-blue w3-left w3-padding" style="position: absolute; user-select: none; border-top-width: 1px; margin-top: 0px; height: 41px;right:0px;display:none;"></div></div>
		            
			            <div id="paginationBar" class="w3-bar w3-border">
	  						<a id="back" class="w3-bar-item w3-button w3-border-right w3-white w3-disabled">&laquo;</a>
	  						<a id="firstPage" class="w3-bar-item w3-button w3-border-right w3-green">1</a>
	  						<a id="curPage" class="w3-bar-item w3-button w3-white">2</a>
	  						<a id="lastPage" class="w3-bar-item w3-button w3-border-left w3-sand"> </a>
	  						<a id="next" class="w3-bar-item w3-button w3-border-left w3-white">&raquo;</a>
	  					</div>

	  					<button onclick="$('#addNewPhoneForm').toggle()" class="w3-button w3-green w3-right w3-hover-cyan" style="width:157px;height:40px;"><i class="fa fa-plus"></i></button>
	  					<div id="paginationSize" class="w3-dropdown-hover w3-margin-right w3-right">
							<button id="pageSize" class="w3-button w3-sand w3-right">Display amount</button>
							<div class="w3-dropdown-content w3-bar-block w3-border">
							    <a id="size10" class="w3-bar-item w3-button w3-black">10</a>
							    <a id="size25" class="w3-bar-item w3-button">25</a>
							    <a id="size50" class="w3-bar-item w3-button">50</a>
							    <a id="size100" class="w3-bar-item w3-button">100</a>
							    <a id="size200" class="w3-bar-item w3-button">200</a>
							</div>
						</div>
		            </div>
					
					<div id="addNewPhoneForm" class="w3-row-padding" style="display:none;">
					<div class="w3-quarter">
						<p></p>
					</div>
					<div class="w3-half w3-padding">
						<div class="w3-border">
							<div class="w3-container w3-cyan">
								<h2>Input Form</h2>
							</div>
							<form method="POST" class="w3-container" action="javascript:void(null);" onsubmit="window.addRecord()">
								<div class="w3-section">
									<input id="fullname" class="w3-input" type="text" placeholder="Full name">
								</div>
								<div class="w3-section">
									<input id="address" class="w3-input" type="text" placeholder="Address">
								</div>
								<div class="w3-section">
									<input id="phone" class="w3-input" type="tel" placeholder="Phone">
								</div>
								<div class="w3-section">
									<c:choose>
									    <c:when test="${empty usernames}">
									        <select id="selectEmail" disabled>Couldn't load emails</select>
									    </c:when>
									    <c:otherwise>
									    	<select id="selectEmail">
					        					<c:forEach items="${usernames}" var="username">
													<option>${username.email}</option>	
											    </c:forEach>
											</select>
									    </c:otherwise>
									</c:choose>
								</div>
								<div class="w3-section">
									<input id="private" class="w3-check" type="checkbox"><label> Private for this email</label>
								</div>
								<div class="w3-section">
									<button type="submit" class="w3-button w3-light-blue w3-hover-green">Add new record</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</c:if>

				<div class="w3-responsive">
					    <table class="w3-table-all w3-hoverable w3-card-4">
							<thead>
								<tr>
									<th>№</th>
									<th>Full name</th>
									<th>Address</th>
									<c:if test='${requestScope.isAdmin}'>
									<th>Email</th>
									</c:if>
									<th>Phone</th>
								</tr>
							</thead>
							<tbody id="tbody">
					     	</tbody>
						</table>
				    </div>
		        </div>
		    </div>
		</div>
		<jsp:include page="<%= "templates/footer.jsp" %>" />
		
    </body>
</html>