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
	
	<title>Editar Usuario</title>
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
		<div id="contentP">
        		<h2>Editar Usuario:</h2>
	        	<p>&nbsp;</p>
           		<p>&nbsp;</p>
           		<form name="form" action="/segaDental/EditUserServlet" onsubmit="return validateEditUser(this)" method="post">
					<jsp:useBean id="userRoles"
						type="java.util.ArrayList<domain.UserRole>" scope="request" />
					<jsp:useBean id="userRooms"
						type="java.util.ArrayList<domain.UserRoom>" scope="request" />
					<jsp:useBean id="products"
						type="java.util.ArrayList<domain.Product>" scope="request" />						
					<jsp:useBean id="userInfo" type="domain.User" scope="request"/> 
					<jsp:useBean id="userId" type="java.lang.Integer" scope="request"/> 
					<jsp:useBean id="userProducts"
						type="java.util.ArrayList<domain.UserProduct>" scope="request" />	
					<a href="/segaDental/EditUserPasswordServlet?userId=<%= request.getAttribute("userId") %>" style="color: transparent" >
					<img alt="logo" src="/segaDental/images/editPasswordText.png" height="39" width="160" id="editIcon" style="padding-left: 15px;" />
					</a> 
					<div id="contentAux">
						<fieldset>
							<input type="hidden" name="txtUserId" value="<%= request.getAttribute("userId") %>" />
							<label for="name">Nombres:</label>
							<input type="text" name="txtName" id="txtName" maxlength="50" size="40" value="<%= userInfo.getFirstName() %>" /> <br><br>
							<label for="LastName">Apellidos:</label>
							<input type="text" name="txtLastName" id="txtLastName" maxlength="50" size="40" value="<%= userInfo.getLastName() %>" /> <br><br>
							<label for="cedId">C&eacute;dula de identidad:</label>
							<%
							String identityCard = userInfo.getIdentityCard();
							String identityCardId = identityCard.substring(0, 2);
							String identityCardNum = identityCard.substring(2);
							%>
							<select name="txtCedId" id="txtCedId">
							<%
							if(identityCardId.equalsIgnoreCase("V-")){
							%>
								<option value="V-" selected="selected">V</option>
								<option value="E-">E</option>
							<%	
							} else {
							%>	
								<option value="V-">V</option>
								<option value="E-" selected="selected">E</option>
							<%
							}
							%>
							</select>
							<input type="text" name="txtCedIdNum" id="txtCedIdNum" maxlength="50" size="18" value="<%= identityCardNum %>" /> <br><br>
							<label for="name">Nombre de usuario:</label>
							<input type="text" name="txtUserName" id="txtUserName" maxlength="50" value="<%= userInfo.getUserName() %>" /> <br><br>
							<label for="name">Rol:</label>
								<select name="txtRoleId" id="txtRoleId" onchange="roomField();">
									<option value="-1">Seleccionar</option>
									<%
										for(domain.UserRole u : userRoles) {
											if(u.getId()==userInfo.getRoleId()){
									%>
									<option value="<%=u.getId()%>" selected="selected"><%=u.getName()%></option>
									<%
											} else {
									%>
									<option value="<%=u.getId()%>"><%=u.getName()%></option>
									<%		
											}
										}
									%>
								</select><br><br>	
						<%		
							if((userInfo.getRoleId()==2) || (userInfo.getRoleId()==4) || (userInfo.getRoleId()==5) || (userInfo.getRoleId()==6)){
						%>
							<div id="roomInfo"  style="display:block">
						<%	
							} else {
						%>
							<div id="roomInfo"  style="display:none">			
						<%		
							}
						%>
							<label for="sala">Sala:</label>
								<select name="txtNumSal" id="txtNumSal">
									<option value="0">Seleccionar</option>
									<%
										for(domain.UserRoom ur : userRooms) {
											if(ur.getId()==userInfo.getRoomId()){
									%>
									<option value="<%=ur.getId()%>" selected="selected"><%=ur.getName()%></option>
									<%
											} else {
									%>
									<option value="<%=ur.getId()%>"><%=ur.getName()%></option>
									<%		
											}
										}
									%>
								</select><br><br>	
							<label for="turno">Turno:</label>
								<select name="txtTurn" id="txtTurn">
									<option value="0">Seleccionar</option>
									<%
										if(userInfo.getTurn().equalsIgnoreCase("A.M.")){
									%>
									<option value="A.M." selected="selected">A.M.</option>
									<%
										} else {
									%>
									<option value="A.M.">A.M.</option>
									<%		
										}
									
										if(userInfo.getTurn().equalsIgnoreCase("P.M.")){
									%>
									<option value="P.M." selected="selected">P.M.</option>
									<%
										} else {
									%>
									<option value="P.M.">P.M.</option>
									<%		
										}
									%>
								</select><br><br>
							</div>
							</fieldset>
						</div>
						<div id="contentProduct">
							<fieldset>
								<label for="producto">Producto:</label>
								<select name="txtProductoId[]" id="txtProductoId" multiple="multiple">
										<%
											for(Product upr : products) {
												domain.UserProduct userProd = new domain.UserProduct();
												userProd.setUserId(userId);
												userProd.setProductId(upr.getId());
			
												if(userProducts.contains(userProd)){
										%>
											<option value="<%= upr.getId()%>" selected="selected"><%= upr.getName()%></option>
										<%
												} else {
										%>
											<option value="<%= upr.getId()%>"><%= upr.getName()%></option>
			
										<%		
												}
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
				</fieldset>	
			</form>      
            <div id="footer"></div>
		</div>
	</div>
</body>
</html>