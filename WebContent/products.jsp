<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="domain.User"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
<title>Administrador Productos</title>
<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.js"></script>
<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/segaDental/js/jconfirmaction.jquery.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#example').dataTable( {
			"iDisplayLength": 8,
			"bLengthChange": false,
			"aoColumns": [
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
	            "sSearch": "Buscar:",
	            "oPaginate": {
	                "sNext": "Siguiente",
	                "sPrevious": "Anterior"
	              }
        	}
		} );
	} );
</script>
<script type="text/javascript">
			var producto;
			$(document).ready(function() {
					$('.ask').jConfirmAction({title : "Eliminar Producto", question : "¿Desea eliminar el producto {0} ?", yesAnswer : "Aceptar", cancelAnswer : "Cancelar"});
			});
			
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
            	<li class="menuitem"><a href="CreateProductServlet">Agregar Producto</a></li>
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
        		<jsp:useBean id="products" type="java.util.ArrayList<domain.Product>" scope="request"/>  	
        		<h2>Productos Registrados:</h2>
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
					if (products.size() == 0) {
				%>	
					<p>&nbsp;</p> 
					<p>En estos momentos no hay productos registrados.</p>  
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
								<th>Descripci&oacute;n</th>
								<th>Precio</th>
								<th>Activo</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
							<%
							for(domain.Product p : products) { 											
							%>
								<tr class="gradeA">
									<td><p><%= p.getId() %></p></td>
									<td><p><%= p.getName() %></p></td>
									<td><p><%= p.getDescription() %></p></td>
									<td><p><%= p.getPrice() %></p></td>
									<td><p><%= (p.getStatus()==1)?"Si":"No" %></p></td>
									<td><p>
										<a href="/segaDental/EditProductServlet?productId=<%= p.getId() %>" style="color: transparent" >
											<img alt="logo" src="/segaDental/images/edit.png"  height="16" width="16" />
										</a> 
										<a href="/segaDental/DeleteProductServlet?productId=<%= p.getId() %>" name="<%= p.getName() %>" class="ask">
											<img alt="logo" src="/segaDental/images/delete.png" height="16" width="16" style="padding-left: 15px;"/>
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
			<div class="spacer"></div>
			<% 
			}
			%>
		</div>
 	</div>
</body>
</html>