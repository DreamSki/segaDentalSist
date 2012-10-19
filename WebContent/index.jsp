<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="../css/styleAdmin.css" />
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<title>SegaDental - Administrador</title>
	<script type="text/javascript" src="/segaDental/js/messages.js"></script>
</head>
<body>
	<div id="container">
		<div id="header">
			<img alt="logo" src="/segaDental/images/loguito.png"/>
        </div>           
       
		<div id="contentLogin">
				<form name="userForm" action="/segaDental/UserLoginServlet" onsubmit="return validateLogin(this)" method="post">
					<fieldset>
						<label for="name">Nombre de usuario:</label>
						<input type="text" name="txtName" id="txtName" maxlength="50" /> <br><br>
						<label for="name">Contraseña:</label>
						<input type="password" name="txtPassword" id="txtPassword" maxlength="50" />
					</fieldset>
					
<%
	String error = (String) request.getAttribute("error");

	if (error != null){
%>
					<div>
						<%= error %>
					</div>
<%
	}
%>		
					<br>
					<div class="buttonCenter">
						<input type="submit" name="sbmtButton" class="button" value="Acceder" />
					</div>
				</form>
			</div>
            <div id="footer"></div>
   			</div>
   </body>
</html>