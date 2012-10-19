<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<title>Menú Principal</title>
</head>
<body>
	<div id="container">
		<div id="header">
            <img alt="logo" src="/segaDental/images/loguito.png"/>
        </div>  
        <div id="menu">
			<div class="menuitemHome"><a href="UserLoginServlet">Home</a></div>	
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
	        	<h2>Bienvenido al administrador de contenidos de SegaDental </h2>
	        	<p>&nbsp;</p>
	           	<p>&nbsp;</p>
	       	  	<h3>Menú Superior</h3>
	        	<p>En el menú superior encontrarás los enlaces para dirigirte al Home o salir del sistema; así como también las opciones propias de cada sección (del menú lateral izquierdo).</p>
	        	<p>&nbsp;</p>
	        	<h3>Menú Lateral Izquierdo</h3>
	            <p>En el menú lateral izquierdo encontrarás las diversas áreas de SegaDental que tu usuario puede administrar. Ingresa al área de tu interés y realiza los ajustes que desees desde cualquier lugar gracias a las ventajas de la web.</p>
	            <p>&nbsp;</p>
		   <div id="footer"></div>
      	</div>
   </div>	
</body>
</html>