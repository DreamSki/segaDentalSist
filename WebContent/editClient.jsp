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
		
	<script type="text/javascript" src="/segaDental/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="/segaDental/js/jquery-ui-1.8.24.custom.min.js"></script>
	<script type="text/javascript" src="/segaDental/js/jquery.ui.datepicker-es.js"></script>
	
	<title>Editar Cliente</title>
	<script type="text/javascript">
	$(function(){
		$.datepicker.setDefaults($.datepicker.regional['es']);
		$('#txtDateIni').datepicker({
			showOn: "button",
			buttonImage: "images/calendar.png",
			buttonImageOnly: true,
			buttonText: "Seleccione una fecha",
			dateFormat:'dd/mm/yy',
			changeMonth: true,
		    changeYear: true,
		});
	} );
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
        		<h2>Editar Cliente:</h2>
	        	<form name="form" class="formClient" action="/segaDental/EditClientServlet"  method="post">
					<fieldset>
						<jsp:useBean id="clientInfo" type="domain.Client" scope="request"/> 
						<input type="hidden" name="txtClientId" value="<%= request.getParameter("clientId") %>" />
						<input type="hidden" name="txtPropertyTypeId" value="<%= clientInfo.getAddress().getPropertyTypeId() %>" />
						
						<label for="name">Nombres:</label>
						<input type="text" name="txtName" id="txtName" maxlength="50" size="40" value="<%= clientInfo.getFirstName() %>" /> <br><br>
						<label for="LastName">Apellidos:</label>
						<input type="text" name="txtLastName" id="txtLastName" maxlength="50" size="40" value="<%= clientInfo.getLastName() %>" /> <br><br>
						<label for="cedId">C&eacute;dula de identidad:</label>
						<%
						String identityCard = clientInfo.getIdentityCard();
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
						<label for="date">Fecha de Nacimiento:</label>
						<input  type="text" name="txtDateIni" id="txtDateIni" maxlength="50" size="10" value="<%= clientInfo.getBirthday() %>" /><br><br> 
						<label for="email">Correo Electrónico:</label>
						<input type="text" name="txtEmail" id="txtEmail" maxlength="50" size="40" value="<%= clientInfo.getEmail() %>" /> 
						
					</fieldset>
					<span style="margin-left: 200px; color:gray;">----------- Dirección -----------</span><br>
						
					<div class="address">	
						Estado:
					    <input id="txtState" class="good_input" name="txtState" type="text"  value="<%= clientInfo.getAddress().getState() %>"/>
						<span id="txtCityLabel">Ciudad: </span>
					    <input id="txtCity" class="good_input" name="txtCity" type="text"  value="<%= clientInfo.getAddress().getCity() %>"/>
					  
						 <br><br>
						Municipio:
					    <input id="txtMunicipality" class="good_input" name="txtMunicipality" type="text"  value="<%= clientInfo.getAddress().getMunicipality() %>"/>
						<span id="txtUrbanizationLabel">Urbanizacion:</span>
					    <input id="txtUrbanization" class="good_input" name="txtUrbanization" type="text"  value="<%= clientInfo.getAddress().getUrbanization() %>"/>
						 <br><br>
						Calle:
					    <input id="txtStreet" class="good_input" name="txtStreet" type="text"  value="<%= clientInfo.getAddress().getStreet() %>"/>
					    <span id="txtPropertyLabel">Nombre Propiedad:</span>
					    <input id="txtPropetyName" class="good_input" name="txtPropetyName" type="text"  value="<%= clientInfo.getAddress().getPropertyName() %>"/>
						 <br><br>
					    <%
					    	if (clientInfo.getAddress().getPropertyTypeId() == 1 || clientInfo.getAddress().getPropertyTypeId() == 3
					    	||  clientInfo.getAddress().getPropertyTypeId() == 5 )
					    	{
					    
					    %>
						   	Torre:
						    <input id="txtTower" class="good_input" name="txtTower" type="text" size="15" value="<%= clientInfo.getAddress().getTower() %>"/>
						    <span id="txtFloorLabel">Piso:</span>
						    <input id="txtFloor" class="good_input" name="txtFloor" type="text" size="5" value="<%= clientInfo.getAddress().getFloor() %>"/>
						    <span id="txtAPLabel">Apartamento:</span>
						    <input id="txtApartment" class="good_input" name="txtApartment" type="text" size="5"  value="<%= clientInfo.getAddress().getApartment() %>"/>
							<br>
						<%
				    		}
				    
				   		 %>
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
					<div style="text-align:center">
							<input type="button" class="button" value="Volver"  onClick="javascript:history.back();"/>
							<input type="submit"  class="button"  name="sbmtButton" value="Modificar" style="margin-left:20px;" />
					</div>	
			</form>      
            <div id="footer"></div>
		</div>
	</div>
</body>
</html>