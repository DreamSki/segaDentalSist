<%@page import="domain.PhoneType"%>
<%@page import="domain.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<title>SegaDental Admin</title>
	
<script type="text/javascript">

function printPageContentB() {
	div = document.getElementById('botonera');
	div.style.display = "none";
	window.print();
};

</script>
<style type="text/css">
#especial{
	background-image:url(null);
	margin-left: 20%;
}
input{
	border: none;
}

label, span {
	font-weight: bold;
}
</style>
</head>
<body id="especial">
		<div id="header">
        	<img alt="logo" src="/segaDental/images/loguito.png"/>
        </div>  
		<jsp:useBean id="clientInfo" type="domain.Client" scope="request"/> 
		<jsp:useBean id="phoneType" type="java.util.ArrayList<domain.PhoneType>" scope="request"/>  	
					
	<br>
	
	<div id="title" style="font-size:16px; font-weight: bold;"> Informacion del cliente <%= clientInfo.getFirstName() + " " + clientInfo.getLastName()  %> </div>
			
		<br><br>
			<%
				if (clientInfo.getType().equalsIgnoreCase("titular")){
			%>		
			
					<fieldset style="border:none;">
					<label for="name">Nombres:</label>
					<input type="text" name="txtName" id="txtName" maxlength="50" size="40" value="<%= clientInfo.getFirstName() %>" /> 
					<label for="LastName">Apellidos:</label>
					<input type="text" name="txtLastName" id="txtLastName" maxlength="50" size="40" value="<%= clientInfo.getLastName() %>" /> <br><br>
					<label for="cedId">C&eacute;dula de identidad:</label>
					<input type="text" name="txtLastName" id="txtLastName" maxlength="50" size="28" value="<%= clientInfo.getIdentityCard() %>" /> 
					<label for="date">Fecha de Nacimiento:</label>
					<input  type="text" name="txtDateIni" id="txtDateIni" maxlength="50" size="10" value="<%= clientInfo.getBirthdate() %>" /><br><br>
					<span id="txtGenLabel"> Sexo: </span>
					<input  type="text" name="txtDateIni" id="txtDateIni" maxlength="50" size="44" value="<%= clientInfo.getSex() %>" />
					<label for="email">Correo Electrónico:</label>
					<input type="text" name="txtEmail" id="txtEmail" maxlength="50" size="40" value="<%= clientInfo.getEmail() %>" /> <br><br>
					<%
						ArrayList<String> phones = clientInfo.getPhones();	
						for( int i = 0; i<phones.size(); i++){
							String [] phone = phones.get(i).split("-");
							String type = phone[0];
							String number = phone[1];
							int j = 0;
							for (domain.PhoneType p: phoneType){
								if (p.getId() == Integer.valueOf(type)){
									String typeName = p.getName();
					%>		
								<label for="phone"><%= typeName %>:</label>
								<input type="text" name="txtPhone<%=i%>" id="txtPhone<%=i %>" maxlength="50" size="38" value="<%= number %>" /> 
					
					<%					
								}
							}
						}
					%>
						</fieldset>
			
				<fieldset style="border:none;">	
				<label for="email">	Estado: </label>
				<input id="txtCity" class="good_input" name="txtCity" type="text"  size="42" value="<%= clientInfo.getAddress().getState() %>"/>
				<label for="email">Ciudad: </label>
			    <input id="txtCity" class="good_input" name="txtCity" type="text"  value="<%= clientInfo.getAddress().getCity() %>"/><br><br>
				<label for="email">Municipio:</label>
			    <input id="txtMunicipality" class="good_input" name="txtMunicipality" size="39" type="text"  value="<%= clientInfo.getAddress().getMunicipality() %>"/>
				<label for="email">Urbanizacion:</label>
			    <input id="txtUrbanization" class="good_input" name="txtUrbanization" type="text"  value="<%= clientInfo.getAddress().getUrbanization() %>"/><br><br>
				<label for="email">Calle:</label>
			    <input id="txtStreet" class="good_input" name="txtStreet" type="text" size="44" value="<%= clientInfo.getAddress().getStreet() %>"/>
			    <label for="email">Nombre Propiedad:</label>
			    <input id="txtPropetyName" class="good_input" name="txtPropetyName" type="text"  value="<%= clientInfo.getAddress().getPropertyName() %>"/><br><br>
				    <%
			    	if (clientInfo.getAddress().getPropertyTypeId() == 1 || clientInfo.getAddress().getPropertyTypeId() == 3 
			    	||  clientInfo.getAddress().getPropertyTypeId() == 5 )
			    	{
				    %>
				 <label for="email">Torre:</label>
				    <input id="txtTower" class="good_input" size="42" name="txtTower" type="text"  value="<%= clientInfo.getAddress().getTower() %>"/>
				    <span id="txtFloorLabel" style="margin-left: 10px;">Piso:</span>
				    <input id="txtFloor" class="good_input" name="txtFloor" type="text" size="5" value="<%= clientInfo.getAddress().getFloor() %>"/>
				    <span id="txtAPLabel">Apartamento:</span>
				    <input id="txtApartment" class="good_input" name="txtApartment" type="text" size="5"  value="<%= clientInfo.getAddress().getApartment() %>"/>
					
				</fieldset>	
					
					
				<%
			    		}
				}else{
				 %>		
				 
				 <fieldset style="border:none;">
					<label for="name">Nombres:</label>
					<input type="text" name="txtName" id="txtName" maxlength="50" size="40" value="<%= clientInfo.getFirstName() %>" /> 
					<label for="LastName">Apellidos:</label>
					<input type="text" name="txtLastName" id="txtLastName" maxlength="50" size="40" value="<%= clientInfo.getLastName() %>" /> <br><br>
					<label for="cedId">C&eacute;dula de identidad:</label>
					<input type="text" name="txtLastName" id="txtLastName" maxlength="50" size="28" value="<%= clientInfo.getIdentityCard() %>" /> 
					<label for="date">Fecha de Nacimiento:</label>
					<input  type="text" name="txtDateIni" id="txtDateIni" maxlength="50" size="10" value="<%= clientInfo.getBirthdate() %>" /><br><br>
					<span id="txtGenLabel"> Sexo: </span>
					<input  type="text" name="txtDateIni" id="txtDateIni" maxlength="50" size="44" value="<%= clientInfo.getSex() %>" />
					<label for="email">Correo Electrónico:</label>
					<input type="text" name="txtEmail" id="txtEmail" maxlength="50" size="40" value="<%= clientInfo.getEmail() %>" /> <br><br>
					<%
						ArrayList<String> phones = clientInfo.getPhones();
						if (phones != null){
							for( int i = 0; i<phones.size(); i++){
								String [] phone = phones.get(i).split("-");
								String type = phone[0];
								String number = phone[1];
								int j = 0;
								for (domain.PhoneType p: phoneType){
									if (p.getId() == Integer.valueOf(type)){
										String typeName = p.getName();
						%>		
									<label for="phone"><%= typeName %>:</label>
									<input type="text" name="txtPhone<%=i%>" id="txtPhone<%=i %>" maxlength="50" size="38" value="<%= number %>" /> 
						
						<%					
									}
								}
							}
						}
					%>
						</fieldset>
						<%					
								}
							%>
				<br><br>
			
			<div id="botonera">
				<form onsubmit="printPageContentB()">
				<div id="botonP">
							<input type="submit"  class="button"  name="sbmtButton" value="Imprimir" style="margin-left:200px;" />
				</div>	
				</form>
				<form action="/segaDental/ListSearchsServlet"  method="post">
					<div id="botonV" style="position:relative; margin-left: 450px; top: -20px;">
						<input type="submit"  class="button"  name="sbmtButton" value="Volver"  />
					</div>	
				</form>
			</div>	
		
	</body>
</html>