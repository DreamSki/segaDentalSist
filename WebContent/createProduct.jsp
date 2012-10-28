<%@page import="domain.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<script type="text/javascript" src="/segaDental/js/messages.js"></script>
<title>Crear Usuario</title>
</head>
<body>
	<div id="container">
		<div id="header">
        	<img alt="logo" src="/segaDental/images/loguito.png"/>
        </div>           
        <div id="menu">
			<div class="menuitemHome"><a href="UserLoginServlet">Home</a></div>	
			<ul>      
			<li class="menuitem"><a href="ListProductsServlet">Ver Productos</a></li>
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
				<h2>Agregar Producto:</h2>
	        	<p>&nbsp;</p>
           		<p>&nbsp;</p>
				<form name="form" action="/segaDental/CreateProductServlet" onsubmit="return validateCreateProduct(this)" method="post">
					<fieldset>
						<label for="nameProduct">Nombre del Producto:</label>
						<input type="text" name="txtNameProduct" id="txtNameProduct" maxlength="50" size="40" /> <br><br>
						<label for="description">Descripci&oacute;n:</label>
						<textarea name="txtDescription" rows="4" cols="31"></textarea> <br><br>
						<label for="price">Precio Base:</label>
						<input type="text" name="txtPrice" id="txtPrice" maxlength="50" size="10" /> <br><br><br>
						<input type="checkbox" name="txtIsActive" class="check" id="txtIsActive" maxlength="50" size="40" value="isActive" />&nbsp; 
							&nbsp;&nbsp; Se ofrece actualmente <br><br>
						
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
					<br><br>
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