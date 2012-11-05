<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="domain.User"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
<title>Administrador Usuarios</title>
<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.js"></script>
<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.leanModal.min.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#example').dataTable( {
			"iDisplayLength": 8,
			"bLengthChange": false,
			"sScrollY": "250px",
			"bPaginate": false,
			"aoColumns": [
				null,
				null,
				null,
				null,
				null,
				null,
				{ "bSearchable": false, "asSorting": false }
			],
			"oLanguage": {
	            "sLengthMenu": "Mostrar _MENU_ registros",
	            "sZeroRecords": "No hay ningún registro que coincida con su búsqueda",
	            "sInfo": "Mostrando _START_ a _END_ de _TOTAL_ registros",
	            "sInfoEmpty": "Mostrando 0 a 0 de 0 registros",
	            "sInfoFiltered": "(filtrando de _MAX_ registros totales)",
	            "sEmptyTable": "No hay datos disponibles en la tabla",
	            "sLoadingRecords": "Por favor, espere - cargando...",
	            "sSearch": "Buscar:"
        	}
		} );
	} );
</script>
<script type="text/javascript">
	var idUser;
			
	$(function() {
		$('a[rel*=leanModal]').leanModal({ top : 200, closeButton: ".close_x" });		
	});
	
	function loadVars(var1, var2) {
		idUser = var1;
		$('.cliente').text(var2);
		
	};
	
	function setV(f){
		f.elements['userId'].value = idUser;
		return true;
	}
</script>
</head>
<body>
	<div id="container">
		<div id="header">
        	<img alt="logo" src="/segaDental/images/loguito.png"/>
        </div>         
        <div id="menu">
			<div class="menuitemHome" ><a href="UserLoginServlet">Home</a></div>	
	    	<ul>
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
        		<jsp:useBean id="users" type="java.util.ArrayList<domain.User>" scope="request"/>  	
        		<h2>Usuarios Registrados:</h2>
        		<%
        			String info = (String)request.getAttribute("info");
        			String error = (String)request.getAttribute("error");
					if(!info.equalsIgnoreCase("")){
				%>	
				<p>&nbsp;</p> 
				<p class="info-msg"><%= info %></p> 
				<%	
					}
					if(!error.equalsIgnoreCase("")){
				%>	
           		<p>&nbsp;</p>    
				<p class="error-msg"><%= error %></p>      
           		<%	
					}
					if (users.size() == 0) {
				%>	
					<p>&nbsp;</p> 
					<p class="noReg">En estos momentos no hay usuarios registrados.</p>  
				<%
				} else {
				%>		
			    <div id="dt_example">
				<div id="container">
				<div id="demo">
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nombre</th>
							<th>Usuario</th>
							<th>Rol</th>
							<th>Sala</th>
							<th>Producto</th>
							<th>Acciones</th>
						</tr>
					</thead>
					<tbody>
			<%
				for(domain.User u : users) { 											
				%>
					<tr class="gradeA">
						<td><p><%= u.getId() %></p></td>
						<td><p><%= u.getName() %></p></td>
						<td><p><%= u.getUserName() %></p></td>
						<td><p><%= u.getRoleName() %></p></td>
						<td><p><%= (u.getRoomName()!=null)?u.getRoomName():"N/A" %></p></td>
						<td><p><%= u.getProductName() %></p></td>
						<td><p>
							<a href="/segaDental/EditUserServlet?userId=<%= u.getId() %>" style="color: transparent" >
								<img alt="logo" src="/segaDental/images/edit.png"  height="16" width="16" />
							</a> 
							<a id="go" rel="leanModal" href="#deleteUser" style="color: #f7941e; font-weight: bold;" 
								onclick="return loadVars(<%=u.getId()%>,'<%=u.getName()%>' )" >
								<img alt="logo" src="/segaDental/images/delete.png" height="16" width="16" style="padding-left: 15px;"/>
							</a><br>
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
		<div class="spacer"></div>
        </div>
        <% 
			}
		%>
	</div>
	
	<div id="deleteUser">
		<div id="signup-ct">
			<h3 id="see_id" class="sprited" > Eliminar Usuario</h3>
			<br><br>
			<span>¿Está seguro que desea eliminar al usuario <span class="cliente"></span>? </span> <br><br>
			<div id="signup-header">
				<a class="close_x" id="close_x"  href="#"></a>
			</div>
			<form action="/segaDental/DeleteUserServlet" method="post"  onsubmit="return setV(this)">
				<input type="hidden" id="userId" class="good_input" name="userId"  value=""/>
				<div class="btn-fld">
					<input type="submit"  class="buttonPopUpDelete"  name="sbmtButton" value="Aceptar"  />
				</div>
		 </form>
		</div>
	</div>
</body>
</html>