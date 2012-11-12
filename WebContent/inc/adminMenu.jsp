<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<ul>
<%
	domain.User user = (domain.User) session.getAttribute("user");

	if (user == null){
		response.sendRedirect("/segaDental/");
	} else {
		int roleId = user.getRoleId();
		switch(roleId){
			case 1:
%>
    <li><a href="/segaDental/ListUsersServlet">Usuarios</a></li>
    <li><a href="/segaDental/ListProductsServlet">Productos</a></li>
	<li><a href="/segaDental/ListReportsServlet">Reportes</a></li>
<%
			break;
			case 2:
%>
    <li><a href="#">Llamadas</a></li>
    <li><a href="#">Clientes</a></li>
<%
			break;
			case 3:
%>
    <li><a href="/segaDental/ListRequestsServlet">Solicitudes</a></li>
	<li><a href="/segaDental/ListSearchsServlet">Búsquedas</a></li>
<%		
			break;
			case 7:
%>
	<li><a href="/segaDental/ListSearchsServlet">Búsquedas</a></li>
<%		
			break;
			case 8:
%>
    <li><a href="/segaDental/ListUsersServlet">Usuarios</a></li>
    <li><a href="/segaDental/ListProductsServlet">Productos</a></li>
	<li><a href="/segaDental/ListReportsServlet">Reportes</a></li>
    <li><a href="/segaDental/ListRequestsServlet">Solicitudes</a></li>
	<li><a href="/segaDental/ListSearchsServlet">Búsquedas</a></li>
    
<%
			break;
		}
	}
%>	    
</ul>