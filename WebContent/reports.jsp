<%@page import="domain.ClientStatus"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="domain.User"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
<link rel="stylesheet" type="text/css" href="/segaDental/css/jquery-ui-1.8.24.custom.css" />
<title>Generación de Reportes</title>
<script type="text/javascript" src="/segaDental/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/segaDental/js/jquery-ui-1.8.24.custom.min.js"></script>
<script type="text/javascript" src="/segaDental/js/jquery.ui.datepicker-es.js"></script>
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
		$('#txtDateEnd').datepicker({
			showOn: "button",
			buttonImage: "images/calendar.png",
			buttonImageOnly: true,
			buttonText: "Seleccione una fecha",
			dateFormat:'dd/mm/yy',
			changeMonth: true,
		    changeYear: true
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
        		<jsp:useBean id="products" type="java.util.ArrayList<domain.Product>" scope="request"/>  	
        		<jsp:useBean id="clientStatus" type="java.util.ArrayList<domain.ClientStatus>" scope="request"/>  	
        		<h2>Escoja el reporte que desea generar:</h2><br><br>
				<form name="form" action="/segaDental/CreateReportServlet"  method="post">
					<fieldset>
						<label for="date">Intervalo de Fechas:</label>
						Inicio: <input  type="text" name="txtDateIni" id="txtDateIni" maxlength="50" size="10" /> &nbsp;&nbsp;
						Fin: <input  type="text" name="txtDateEnd" id="txtDateEnd" maxlength="50" size="10" /> <br><br>
						<label for="state">Estado:</label>
						<select name="txtState">
						  <option value="-1">Seleccionar</option>
						  <option value="AMZ">Amazonas</option>
						  <option value="ANZ">Anzoátegui</option>
						  <option value="APR">Apure</option>
						  <option value="ARG">Aragua</option>
						  <option value="BAR">Barinas</option>
						  <option value="BOL">Bolivar</option>
						  <option value="CAR">Carabobo</option>
						  <option value="COJ">Cojedes</option>
						  <option value="DAM">Delta Amacuro</option>
						  <option value="DTC">Distrito Capital</option>
						  <option value="FLC"> Falcón</option>
						  <option value="GCO">Guarico</option>
						  <option value="LAR">Lara</option>
						  <option value="MER">Mérida</option>
						  <option value="MIR">Miranda</option>
						  <option value="MON">Monagas</option>
						  <option value="NES">Nueva Esparta</option>
						  <option value="POR">Portuguesa</option>
						  <option value="SUC">Sucre</option>
						  <option value="TAC">Táchira</option>
						  <option value="TRJ">Trujillo</option>
						  <option value="YAR">Yaracuy</option>
						  <option value="ZUL">Zulia</option>
						  <option value="VRG">Vargas</option>
						</select><br><br>
						<label for="product">Producto:</label>
						<select name="txtProduct">
							<option value="-1">Seleccionar</option>
						<%
								for(domain.Product p : products) { 											
						%>
						  <option value="<%= p.getId() %>"><%= p.getName() %></option>
						<%
						}										
						%>
						</select><br><br>
						<label for="statusClient">Status Cliente:</label>
						<select name="txtClientStatus">
							<option value="-1">Seleccionar</option>
							<%
								for(domain.ClientStatus c : clientStatus) { 											
							%>
							  <option value="<%= c.getId() %>"><%= c.getName() %></option>
							<%
							}										
							%>
						</select>
						<br><br>
					<div style="text-align:center">
							<input type="submit"  class="button"  name="sbmtButton" value="Generar" style="margin-left:20px;" />
					</div>	
					</fieldset>
				</form>
        </div>  
		<div id="footer"></div>
	</div>
</body>
</html>