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
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<link rel="stylesheet" type="text/css" href="/segaDental/css/jquery.multiSelect.css" />
	
	<title>Crear Usuario</title>
	<script>

	function roomField() {
		var select = document.getElementById("txtRoleId");
		var position = select.options.selectedIndex;
		var roleId = select.options[position].value;
		var div = document.getElementById("roomInfo");

		if((roleId == "-1") || (roleId == "1") || (roleId == "3") || (roleId == "7")){
			div.style.display="none";
		} else {
			div.style.display = "block";
		}	
	}

	</script>
	<script type="text/javascript">
			
			$(document).ready( function() {
				
				// Options displayed in comma-separated list
				$("#txtProductoId").multiSelect({ 
						oneOrMoreSelected: '*',
						selectAllText: 'Todos',
						noneSelected: 'Seleccionar'}
				);
				
			});
			
	</script>
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
		<div id="contentP">
				<h2>Agregar Usuario:</h2>
	        	<p>&nbsp;</p>
           		<p>&nbsp;</p>
				<form name="form" action="/segaDental/CreateUserServlet" onsubmit="return validate(this)" method="post">
					<jsp:useBean id="userRoles"
						type="java.util.ArrayList<domain.UserRole>" scope="request" />
					<jsp:useBean id="userRooms"
						type="java.util.ArrayList<domain.UserRoom>" scope="request" />
					<jsp:useBean id="products"
						type="java.util.ArrayList<domain.Product>" scope="request" />
					<div id="contentAux">
						<fieldset>
							<label for="name">Nombres:</label>
							<input type="text" name="txtName" id="txtName" maxlength="50" size="40" /> <br><br>
							<label for="LastName">Apellidos:</label>
							<input type="text" name="txtLastName" id="txtLastName" maxlength="50" size="40" /> <br><br>
							<label for="cedId">C&eacute;dula de identidad:</label>
							<select name="txtCedId" id="txtCedId">
								<option value="V-">V</option>
								<option value="E-">E</option>
							</select>
							<input type="text" name="txtCedIdNum" id="txtCedIdNum" maxlength="50" size="18" /> <br><br>
							<label for="name">Nombre de usuario:</label>
							<input type="text" name="txtUserName" id="txtUserName" maxlength="50" /> <br><br>
							<label for="name">Contraseña:</label>
							<input type="password" name="txtPassword" id="txtPassword" maxlength="50" /><br><br>
							<label for="name">Repetir Contraseña:</label>
							<input type="password" name="txtPasswordRpt" id="txtPasswordRpt" maxlength="50" /><br><br>
							<label for="name">Rol:</label>
								<select name="txtRoleId" id="txtRoleId" onchange="roomField();">
									<option value="-1">Seleccionar</option>
									<%
										for(domain.UserRole u : userRoles) {
									%>
									<option value="<%=u.getId()%>"><%=u.getName()%></option>
									<%
										}
									%>
								</select><br><br>
							<div id="roomInfo"  style="display:none">	
							<label for="sala">Sala:</label>
								<select name="txtNumSal" id="txtNumSal">
									<option value="0">Seleccionar</option>
									<%
										for(domain.UserRoom ur : userRooms) {
									%>
									<option value="<%=ur.getId()%>"><%=ur.getName()%></option>
									<%
										}
									%>
								</select>
							</div>
						</fieldset>
					</div>
					<div id="contentProduct">
						<fieldset>
							<label for="producto">Producto:</label>
							<select name="txtProductoId[]" id="txtProductoId"  class="txtProductoId" multiple="multiple">
									<%
										for(Product upr : products) {
									%>
										<option value="<%= upr.getId()%>"><%= upr.getName()%></option>

									<%
									}	
									%>
							</select><br><br>
						</fieldset>
					</div>
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
					<div style="text-align:center">
							<input type="button" class="button" value="Volver"  onClick="javascript:history.back();"/>
							<input type="submit"  class="button"  name="sbmtButton" value="Agregar" style="margin-left:20px;" />
					</div>				
			</form>
		    <div id="footer"></div>
		</div>
	</div>
</body>
</html>