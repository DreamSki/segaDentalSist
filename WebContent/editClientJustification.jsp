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
	<script type="text/javascript" language="javascript" src="/segaDental/js/messages.js"></script>
	
	<title>Editar Cliente</title>
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
        		<h2>Justificación de la modificación:</h2><br><br>
        			 <form name="form" class="formClient" action="/segaDental/EditClientJustifServlet"  method="get">
      					 <jsp:useBean id="clientId" type="java.lang.Integer" scope="request"/> 
							<input type="hidden" name="txtClientId" value="<%= clientId %>" />
							<input type="hidden" name="type" value="<%= request.getParameter("type") %>" />
							Por favor introduzca el motivo de la modificación: <br><br>
							<textarea name="txtJustifEdit" rows="5" cols="84"></textarea> <br><br><br><br>
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