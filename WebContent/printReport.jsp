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
	<jsp:useBean id="report" type="java.util.ArrayList<domain.ReportItem>" scope="request"/>  	
	<br>
	
	<div id="title" style="font-size:16px; font-weight: bold;"> Reporte de Venta Diaria </div>
			 <div id="dt_example" >
				<div id="container">
				<div id="demo">
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" >
					<thead>
						<tr>
							<th style="border-left:1px solid black;">Nombre Cliente</th>
							<th style="border-left:1px solid black;">C.I</th>
							<th style="border-left:1px solid black;">Número de Beneficiarios</th>
							<th style="border-left:1px solid black;">Monto</th>
							<th style="border-left:1px solid black;">Sala</th>
							<th style="border-left:1px solid black;">Nombre Ejecutivo</th>
							<th style="border-left:1px solid black;">Turno</th>
							<th style="border-left:1px solid black;">Tipo de TDC</th>
							<th style="border-left:1px solid black;">Número transacción</th>
							<th style="border-left:1px solid black;">Tipo Banco</th>
							<th style="border-left:1px solid black; border-right:1px solid black;">Dirección</th>
						</tr>
					</thead>
					<tbody>
				<%
				boolean firstC = true;
				boolean first = true;
				for(domain.ReportItem ri : report) { 	
					if (ri.getType() == 5){
						if(firstC){
				%>
				<tr><td colspan="11" style="border-top:1px solid black; border-bottom:1px solid black;">Clientes Nuevos </td></tr>
				<%
					firstC = false;
				}
				%>
					<tr>
						<td><%= ri.getFirstName() + " " + ri.getLastName() %></td>
						<td style="border-left:1px solid black;"><%= ri.getIdentityCard()%></td>
						<td style="border-left:1px solid black;"><%= ri.getNumBenef() %></td>
						<td style="border-left:1px solid black;"><%= ri.getAmount()%></td>
						<td style="border-left:1px solid black;"><%= ri.getRoom()%></td>
						<td style="border-left:1px solid black;"><%= ri.getSeller()%></td>
						<td style="border-left:1px solid black;"><%= ri.getTurn()%></td>
						<td style="border-left:1px solid black;"><%= ri.getCardType()%></td>
						<td style="border-left:1px solid black;"><%= ri.getVoucher()%></td>
						<td style="border-left:1px solid black;"><%= ri.getBank()%></td>
						<td style="border-left:1px solid black;"><%= ri.getCity()+"-"+ri.getState() %></td>
						
						
					</tr>
				<% 
						}else{
							if (first){
				%>
								<tr><td colspan="11" style="border-top:1px solid black; border-bottom:1px solid black;">Renovaciones </td></tr>
				
				<%
								first = false;
							}
				%>			
				
					<tr>
						<td><%= ri.getFirstName() + " " + ri.getLastName() %></td>
						<td style="border-left:1px solid black;"><%= ri.getIdentityCard()%></td>
						<td style="border-left:1px solid black;"><%= ri.getNumBenef() %></td>
						<td style="border-left:1px solid black;"><%= ri.getAmount()%></td>
						<td style="border-left:1px solid black;"><%= ri.getRoom()%></td>
						<td style="border-left:1px solid black;"><%= ri.getSeller()%></td>
						<td style="border-left:1px solid black;"><%= ri.getTurn()%></td>
						<td style="border-left:1px solid black;"><%= ri.getCardType()%></td>
						<td style="border-left:1px solid black;"><%= ri.getVoucher()%></td>
						<td style="border-left:1px solid black;"><%= ri.getBank()%></td>
						<td style="border-left:1px solid black;"><%= ri.getCity()+"-"+ri.getState() %></td>
						
						
					</tr>
				
							
				<% 			
						}
					}
				%>
				
				</tbody>
				</table>
				</div>
			</div><br><br>
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
		</div>	
		
	</body>
</html>