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
			"aoColumns": [
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
        		<h2>Búsquedas de Clientes:</h2>
		<br>
		<div style="float:right;">  
			<form action="/segaDental/SearchClientServlet" method="post">
				<span style="font-weight: bold;"> Ingrese la cédula del cliente: </span>
				<input type="text" name="txtCedClient" id="txtCedClient" maxlength="9" size="20" /> &nbsp;
				<input type="image" src="./images/search.png" name="search" width="24" height="24" name="sbmtButton" class="buttonSearch" />
			</form>
		</div>  

    <div id="dt_example" style="display:none;">
	<div id="container">
	<div id="demo">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
		<thead>
			<tr>
				<th>Tipo</th>
				<th>Nombre Cliente</th>
				<th>Fecha Vencimiento</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<tr class="gradeA">
				<td><p>Solicitud</p></td>
				<td><p>Pedro Perez</p></td>
				<td><p>01/12/2013</p></td>
				<td><p>
				<a href="/segaDental/EditUserServlet?userId=" style="color: transparent" >
					<img alt="logo" src="/segaDental/images/edit.png"  height="16" width="16" />
				</a> 
				<a href="/segaDental/DeleteUserServlet?userId=" name="" class="ask">
					<img alt="logo" src="/segaDental/images/delete.png" height="16" width="16" style="padding-left: 15px;"/>
				</a>
				</p>
				</td>
			</tr>
		</tbody>
		</table>
		</div>
		</div>
		</div>
		<div class="spacer"></div>
        	</div>
            <div id="footer"></div>
	</div>
</body>
</html>