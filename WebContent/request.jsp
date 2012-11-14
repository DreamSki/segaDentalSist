<%@page import="domain.ClientRequest"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="domain.User"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<title>Administrador Solicitudes y Renovaciones</title>
	<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="/segaDental/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="/segaDental/js/messages.js"></script>
	<script type="text/javascript" src="/segaDental/js/jconfirmaction.jquery.js"></script>
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
			var tipo;
			var idClientRequest;
			
			$(function() {
    			$('a[rel*=leanModal]').leanModal({ top : 200, closeButton: ".close_x" });		
			});
			
			function loadVars(var1, var2, var3) {
				tipo = var1;
				idClientRequest = var2;
				$('.tipo').text(var1);
				$('.cliente').text(var3);
			};
			
			function setV(f){
				f.elements['clientProductId'].value = idClientRequest;
				var radioButtons = document.getElementsByName("justif");
				for (var x = 0; x < radioButtons.length; x ++) {
					if (radioButtons[x].checked) {
						if (radioButtons[x].value == 5){
							return validateJustification(f);
						}
					}
				}
				return true;
			}
			
			function setVDelete(f){
				f.elements['id'].value = idClientRequest;
				f.elements['type'].value =tipo;
				return true;
			}
			
			function cambiarMensaje(){
				$("#mensaje").text("Cargando el reporte de venta diaria. Por favor espere...");
				div2 = document.getElementById('mensaje');
				div2.style.fontSize="20px";
				div2.style.color="gray";
				div = document.getElementById('botonesInfo');
				div.style.display = "none";
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
            	<li class="menuitem">
            		<a href="/segaDental/PrintReportServlet">
            			<img alt="imprimir" src="/segaDental/images/imprimir_reporte.png"/>
            		</a>
            		<a id="go" rel="leanModal" href="#acceptSendEmail" style="color: #f7941e; font-weight: bold;" >
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
						System.out.println(info.equalsIgnoreCase("El correo fue envíado sastifactoriamente"));
						if (!info.equalsIgnoreCase("El correo fue envíado sastifactoriamente")
								|| !info.equalsIgnoreCase("Actualmente no hay ventas para mostrar. Intente más tarde")){
				%>	
				<p>&nbsp;</p> 
				<p class="info-msg"><%= info %></p> 
				<%	
						}
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
					<p class="noReg">En estos momentos no hay solicitudes ni renovaciones pendientes.</p>  
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
			
			<td><%= cr.getId() %></td>
			<td><%= cr.getTypeName() %></td>
			<td><%= cr.getName() %></td>
			<td><%= cr.getProduct().getName() %></td>
			<td><%= (cr.getExpirationDate()!=null)?cr.getExpirationDate(): "N/A" %></td>
			<td>
			<a href="/segaDental/EditRequestServlet?clientId=<%=cr.getClientId()%>&type=<%=cr.getTypeName()%>&id=<%=cr.getId()%>" style="color: transparent" >
				<img alt="logo" src="/segaDental/images/edit.png"  height="16" width="16" />
			</a> 
			<a id="go" rel="leanModal" href="#deleteRequest" style="color: #f7941e; font-weight: bold;" 
			onclick="return loadVars('<%=cr.getTypeName()%>',<%=cr.getId()%>,'<%=cr.getName()%>' )" >
        		<img alt="logo" src="/segaDental/images/delete.png" height="16" width="16" style="padding-left: 15px;"/>
        	</a>
			
        	<a id="go" rel="leanModal" href="#signupCed" style="color: #f7941e; font-weight: bold;" 
			onclick="return loadVars('<%=cr.getTypeName()%>',<%=cr.getId()%>,'<%=cr.getName()%>' )" >
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
 			
 			
	<div id="signupCed">
		<div id="signup-ct">
			<h3 id="see_id" class="sprited" >Devolución de <span class="tipo"></span></h3>
			<br><br>
			<span>¿Está seguro que desea devolver la <span class="tipo"></span> del cliente <span class="cliente"></span>? </span> <br><br>
			<div id="signup-header">
				<a class="close_x" id="close_x"  href="#"></a>
			</div>
			<form action="/segaDental/SendBackRequestServlet" method="get"  onsubmit="return setV(this)">
				<input type="hidden" id="clientProductId" class="good_input" name="clientProductId"  value=""/>
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
				<input type="radio" name="justif" value="5" checked> 
					Otro: <input name="otherJustif" id="otherJustif" />
			  </div><br>
			  <div class="btn-fld">
				  <input type="submit"  class="buttonPopUp"  name="sbmtButton" value="Aceptar" style="margin-left:20px;" />
			  </div>
		 </form>
		</div>
	</div>
	
	<div id="deleteRequest">
		<div id="signup-ct">
			<h3 id="see_id" class="sprited" >Eliminar Solicitud</h3>
			<br><br>
			<span>¿Está seguro que el cliente <span class="cliente"></span> no desea continuar con la compra?</span> <br><br>
			<div id="signup-header">
				<a class="close_x" id="close_x"  href="#"></a>
			</div>
			<form action="/segaDental/DeleteRequestServlet" method="post"  onsubmit="return setVDelete(this)">
				<input type="hidden" id="id" class="good_input" name="id"  value=""/>
				<input type="hidden" id="type" class="good_input" name="type"  value=""/>
				
				<div class="btn-fld">
					<input type="submit"  class="buttonPopUpDelete"  name="sbmtButton" value="Aceptar"  />
				</div>
			</form>
		</div>
	</div>
	
	<div id="acceptSendEmail">
		<div id="signup-ct">
			<h3 id="see_id" class="sprited" >Aceptar Envío de Reporte</h3>
			<br><br><br>
			<span id="mensaje" style="font-size:16px;">¿Está seguro que desea enviar por correo electrónico el reporte de ventas diario?</span> <br><br>
			<div id="signup-header">
				<a class="close_x" id="close_x"  href="#"></a>
			</div>
			<form action="/segaDental/SendReportRequestServlet" method="get"  onsubmit="cambiarMensaje()">
				<div class="btn-fld" id="botonesInfo">
					<input type="submit"  class="buttonPopUpDelete"  name="sbmtButton" value="Aceptar"  />
				</div>
			</form>
		</div>
	</div>
	
</body>
</html>