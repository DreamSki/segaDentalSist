<%@page import="domain.PhoneType"%>
<%@page import="domain.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js" type="text/javascript"></script>
	<script src="/segaDental/js/jquery.bgiframe.min.js" type="text/javascript"></script>
	<script src="/segaDental/js/jquery.multiSelect.js" type="text/javascript"></script>
	<link href="/segaDental/css/jquery.multiSelect.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/segaDental/css/styleAdmin.css" />
	<title>SegaDental Admin</title>
	
<script type="text/javascript">

function printPageContentB() {
	window.print();
	history.go(-2);
};

</script>
<style type="text/css">
@media print{@page {size: landscape}}
</style>
</head>
<body >
	<jsp:useBean id="report" type="java.util.ArrayList<domain.ReportItem>" scope="request"/>  	
	<br>
	
	<div id="title"> Reporte de venta diaria </div>
			 <div id="dt_example" style="width: 1000px;">
				<div id="container">
				<div id="demo">
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" >
					<thead>
						<tr>
							<th style="border-left:1px solid black;">Nombre Cliente</th>
							<th style="border-left:1px solid black;">C.I</th>
							<th style="border-left:1px solid black;">Cant</th>
							<th style="border-left:1px solid black;">Monto</th>
							<th style="border-left:1px solid black;">Sala</th>
							<th style="border-left:1px solid black;">Nombre Ejecutivo</th>
							<th style="border-left:1px solid black;">Turno</th>
							<th style="border-left:1px solid black;">Tipo de TDC</th>
							<th style="border-left:1px solid black;">Número trans</th>
							<th style="border-left:1px solid black;">Tipo Banco</th>
							<th style="border-left:1px solid black; border-right:1px solid black;">Direccion</th>
						</tr>
					</thead>
					<tbody>
			<%
				for(domain.ReportItem ri : report) { 											
				%>
					<tr>
						<td><%= ri.getFirstName() + " " + ri.getLastName() %></td>
						<td style="border-left:1px solid black;"><%= ri.getIdentityCard()%></td>
						<td style="border-left:1px solid black;">a</td>
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
				%>
		
	
				</tbody>
			</table>
		</div>
		</div>
			
			
					 	
					 	
 	</div>	
	
	
	<a href="#null" id="printT" onclick="printPageContentB()" style="position: absolute; left: 800px; top: 200px: display:none;"> 
	</a>
	 <script type="text/javascript">
			document.getElementById('printT').click();
	 </script>
	
</body>
</html>