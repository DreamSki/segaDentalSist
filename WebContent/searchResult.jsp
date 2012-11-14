<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="domain.User"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
<title>Búsqueda de Clientes</title>
<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.js"></script>
<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#example').dataTable( {
			"iDisplayLength": 8,
			"bLengthChange": false,
			"bPaginate": false,
			"bFilter": false,
			"bSort": false,
			"oLanguage": {
	            "sLengthMenu": "Mostrar _MENU_ registros",
	            "sZeroRecords": "No hay ningún registro que coincida con su búsqueda",
	            "sInfo": "Mostrando _START_ a _END_ de _TOTAL_ registros",
	            "sInfoEmpty": "Mostrando 0 a 0 de 0 registros",
	            "sInfoFiltered": "(filtrando de _MAX_ registros totales)",
	            "sEmptyTable": "No hay datos disponibles en la tabla",
	            "sLoadingRecords": "Por favor, espere - cargando...",
	            "sSearch": "Buscar:",
	            "oPaginate": {
	                "sNext": "Siguiente",
	                "sPrevious": "Anterior"
	              }
        	}
		} );
	} );
</script>
</head>
<body>
	<div id="container">
		<div id="header">
        	<img alt="logo" src="/segaDental/images/loguito.png"/>
        </div>         
        <div id="menu">
			<div class="menuitemHome" ><a href="UserLoginServlet">Home</a></div>	
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
				<jsp:useBean id="clients" type="java.util.ArrayList<domain.Client>" scope="request"/>  	
        		<h2>Búsquedas de Clientes:</h2>
     			<br>
				<div style="float:right;">  
					<form action="/segaDental/SearchClientServlet" method="post">
						<span style="font-weight: bold;"> Ingrese la cédula del cliente: </span>
						<select name="txtCedId" id="txtCedId">
							<option value="V-">V</option>
							<option value="E-">E</option>
						</select>
						<input type="text" name="txtCedClient" id="txtCedClient" maxlength="9" size="20" /> &nbsp;
						<input type="image" src="./images/search.png" name="search" width="24" height="24" name="sbmtButton" class="buttonSearch" />
					</form>
				</div>  
				<br><br>	
        		<%
					String info = (String)request.getAttribute("info");
        			String error = (String)request.getAttribute("error");
					if(!info.equalsIgnoreCase("")){
				%>	
					<p>&nbsp;</p> 
					<p class="info-msg"><%= info %></p> 
				<%	
					}
					if(!error.equalsIgnoreCase("") ){
					%>	
           			<p>&nbsp;</p>    
					<p class="error-msg"><%= error %></p>      
           			<%	
					}
					if (clients.size() == 0) {
					%>	
						<p>&nbsp;</p><br><br><br>
						<p style="font-weight: bold; text-align:center; font-size: 14px; color:red;"> No hubo resultados que coincidan con su búsqueda.</p>  
					<%
					} else {
					%>	
					<div id="search">
				    <div id="dt_example">
					<div id="container">
					<div id="demo">
						<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
						<thead>
							<tr>
								<th>Nombre Cliente</th>
								<th>Cédula</th>
								<th>Correo</th>
								<th>Tipo</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
						<%
							for(domain.Client client : clients) { 											
						%>	<tr class="gradeA" >
								<td ><p><%= client.getFirstName() + " " + client.getLastName() %></p></td>
								<td><p><%= client.getIdentityCard() %></p></td>
								<td><p><%= (client.getEmail()!=null)?client.getEmail():"No posee"  %></p></td>
								<td><p><%= client.getType() %></p></td>
								<td><p>
								<a href="/segaDental/EditClientServlet?clientId=<%=client.getClientId() %>&type=<%=client.getType() %>&print=0" style="color: transparent" >
									<img alt="logo" src="/segaDental/images/edit.png"  height="16" width="16" />
								</a> 
								<a href="/segaDental/EditClientServlet?clientId=<%=client.getClientId() %>&type=<%=client.getType() %>&print=1">
									<img alt="logo" src="/segaDental/images/print.png" height="16" width="16" style="padding-left: 15px;"/>
								</a>
								</p>
								</td>
							</tr>
						<% 
							}
						%>
						</tbody>
						</table>
						</div>
					</div>
				</div>
			</div>
			<div class="spacer"></div>
        	</div>
            <div id="footer"></div>
			<% 
			}
			%>
		</div>
				
</body>
</html>