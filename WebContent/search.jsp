<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="domain.User"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
<title>Búsqueda de Clientes</title>
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
						<select name="txtCedId" id="txtCedId">
							<option value="V-">V</option>
							<option value="E-">E</option>
						</select>
						<input type="text" name="txtCedClient" id="txtCedClient" maxlength="9" size="20" /> &nbsp;
						<input type="image" src="./images/search.png" name="search" width="24" height="24" name="sbmtButton" class="buttonSearch" />
					</form>
				</div>  <br><br><br><br>
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
					%>
		</div>
		
	</div>
</body>
</html>