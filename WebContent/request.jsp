<%@page import="domain.ClientRequest"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="domain.User"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<title>Administrador Solicitudes</title>
	<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="/segaDental/js/jconfirmaction.jquery.js"></script>
	<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.leanModal.min.js"></script>
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
	            "sZeroRecords": "No hay ning�n registro que coincida con su b�squeda",
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
			$(document).ready(function() {
					$('.ask').jConfirmAction({title : "Eliminar Solicitud", question : "�Est� seguro que el cliente {0} no desea continuar con la compra?", yesAnswer : "Aceptar", cancelAnswer : "Cancelar"});
			});
			
	</script>
	<script type="text/javascript">
			$(function() {
    			$('a[rel*=leanModal]').leanModal({ top : 200, closeButton: ".close_x" });		
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
            	<li class="menuitem">
            		<a href="#">
            			<img alt="imprimir" src="/segaDental/images/imprimir_reporte.png"/>
            	
            		</a>
            		<a href="#">
            			<img alt="enviar" src="/segaDental/images/enviar_reporte.png"/>
            	
            		</a>
            	</li>
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
				<jsp:useBean id="listClientRequests" type="java.util.ArrayList<domain.ClientRequest>" scope="request"/>  	
        		<jsp:useBean id="statusJustification" type="java.util.ArrayList<domain.StatusJustification>" scope="request"/>  	
        		<h2>Solicitudes y Renovaciones por evaluar:</h2>
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
					if (listClientRequests.size() == 0) {
				%>	
					<p>&nbsp;</p> 
					<p>En estos momentos no hay solicitudes ni renovaciones pendientes.</p>  
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
				<th>Tipo</th>
				<th>Nombre Cliente</th>
				<th>Producto</th>
				<th>Fecha Vencimiento</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(ClientRequest cr : listClientRequests) { 											
			%>
		
			<tr class="gradeA">
			
			<td><p><%= cr.getId() %></p></td>
			<td><p><%= cr.getTypeName() %></p></td>
			<td><p><%= cr.getName() %></p></td>
			<td><p><%= cr.getProduct().getName() %></p></td>
			<td><p><%= cr.getExpirationDate() %></p></td>
			<td><p>
			<a href="/segaDental/EditRequestServlet?clientId=<%=cr.getClientId()%>&type=<%=cr.getTypeName()%>&id=<%=cr.getId()%>" style="color: transparent" >
				<img alt="logo" src="/segaDental/images/edit.png"  height="16" width="16" />
			</a> 
			<a href="/segaDental/DeleteRequestServlet?clientId=<%=cr.getClientId()%>" name="<%= cr.getName() %>" class="ask">
				<img alt="logo" src="/segaDental/images/delete.png" height="16" width="16" style="padding-left: 15px;"/>
			</a>
			
        	
        	<a id="go" rel="leanModal" href="#signupCed" style="color: #f7941e; font-weight: bold;">
        		<img alt="logo" src="/segaDental/images/sendBack.png" height="16" width="16" style="padding-left: 15px;"/>
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
		<% 
		}
		%>
        </div>
  		<div id="footer"></div>
	</div>
 			
	<% 
	/** texto a mostrar cuando se intente devolver una solicitud**/
	
	String tipo = "Solicitud"; 
	%>
	<div id="signupCed">
		<div id="signup-ct">
			<h3 id="see_id" class="sprited" >Devoluci�n de <%= tipo %></h3><br><br>
			<span>�Est� seguro que desea devolver la <%= tipo %> del cliente Pedro Perez? </span> <br><br>
			<div id="signup-header">
				<a class="close_x" id="close_x"  href="#"></a>
			</div>
			<form action="/segaDental/ReturnRequestServlet" method="get">
			  <div class="txt-fld">
   			  	<%
				for(domain.StatusJustification sj : statusJustification) { 	
					if (sj.getId() != 5){
				%>
				<input type="radio" name="justif" value="<%= sj.getId() %>"><%= sj.getName() %><br>
				<%
					}
				}
				%>
				<input type="radio" name="justif" value="5"> 
					Otro: <input name="OtherJustif" />
			  </div><br>
			  <div class="btn-fld">
				  <input type="submit"  class="buttonPopUp"  name="sbmtButton" value="Aceptar" style="margin-left:20px;" />
			  </div>
		 </form>
		</div>
	</div>
	
	
</body>
</html>