<%@page import="domain.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="/segaDental/js/messages.js"></script>
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js" type="text/javascript"></script>
	<script src="/segaDental/js/jquery.bgiframe.min.js" type="text/javascript"></script>
	<script src="/segaDental/js/jquery.multiSelect.js" type="text/javascript"></script>
	<link href="/segaDental/css/jquery.multiSelect.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	
	<title>Edición del Usuario</title>	
</head>
<body>
	<div id="container">
		<div id="header">
        	<img alt="logo" src="/segaDental/images/loguito.png"/>
        </div>   
		<div id="menu">
			<div class="menuitemHome"><a href="UserLoginServlet">Home</a></div>	
			<ul>      
				<li class="menuitem"><a href="ListUsersServlet">Ver Usuarios</a></li>
				<li class="menuitem"><a href="CreateUserServlet">Agregar Usuario</a></li>
			</ul>
			<div class="menuitemSalir"><a href="index.jsp">Salir</a></div>	
        </div>
	 	<div id="leftmenu">
        	<div id="leftmenu_top"></div>
			<div id="leftmenu_main">                    
            	<jsp:include page="/inc/adminMenu.jsp"></jsp:include>
			</div>
            <div id="leftmenu_bottom"></div>
        </div>  
		<div id="content">
        		<h2>Modificar contraseña del usuario:</h2>
	        	<p>&nbsp;</p>
           		<p>&nbsp;</p>
           		<%
					String error = (String) request.getAttribute("error");
					if (error != null){
					%>
						<p class="error-msg"><%= error %></p>      
					<%
					}
					%>	
				<br>
				<form name="form" action="/segaDental/EditUserPasswordServlet" onsubmit="return validatePassword(this)" method="post">
					<fieldset>
						<jsp:useBean id="userId" type="java.lang.Integer" scope="request"/> 
						<input type="hidden" name="txtUserId" value="<%= request.getAttribute("userId") %>" />
						<label for="oldPassword">Contraseña anterior:</label>
						<input type="password" name="txtOldPassword" id="txtOldPassword" maxlength="50"  /><br><br>
						<label for="LastName">Contraseña nueva:</label>
						<input type="password" name="txtPassword" id="txtPassword" maxlength="50"  /><br><br>
						<label for="LastName">Repita contraseña:</label>
						<input type="password" name="txtPasswordRpt" id="txtPasswordRpt" maxlength="50" /><br><br>
					
					<br><br><br><br><br>
					<div style="text-align:center">
							<input type="button" class="button" value="Volver"  onClick="javascript:history.back();"/>
							<input type="submit"  class="button"  name="sbmtButton" value="Modificar" style="margin-left:20px;" />
					</div>	
				</fieldset>	
			</form>      
            <div id="footer"></div>
		</div>
	</div>
</body>
</html>