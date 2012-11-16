<%@page import="domain.PhoneType"%>
<%@page import="domain.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<title>SegaDental Admin</title>
	
<script type="text/javascript">

function printPageContentB() {
	div = document.getElementById('botonera');
	div.style.display = "none";
	window.print();
};

</script>
<style type="text/css">
@media print{@page {size: landscape}}
#especial{
	background-image:url(null);
	margin-left: 20%;
}
</style>
</head>
<body id="especial">
		<div id="header">
        	<img alt="logo" src="/segaDental/images/loguito.png"/>
        </div>  
		<jsp:useBean id="clientInfo" type="domain.Client" scope="request"/> 
		<jsp:useBean id="phoneType" type="java.util.ArrayList<domain.PhoneType>" scope="request"/>  	
					
	<br>
	
	<div id="title" style="font-size:16px; font-weight: bold;"> Informacion del cliente <%= clientInfo.getFirstName() + " " + clientInfo.getLastName()  %> </div>
			
			
			<div id="botonera">
				<form onsubmit="printPageContentB()">
				<div id="botonP" style="text-align:center;">
							<input type="submit"  class="button"  name="sbmtButton" value="Imprimir" style="margin-left:20px;" />
				</div>	
				</form>
				<form action="/segaDental/ListRequestsServlet"  method="post">
					<div id="botonV" style="position:relative; margin-left: 500px; top: -20px;">
						<input type="submit"  class="button"  name="sbmtButton" value="Volver" style="margin-left:20px;" />
					</div>	
				</form>
			</div>	
		
	</body>
</html>