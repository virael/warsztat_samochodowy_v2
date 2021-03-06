<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link type="text/css" rel="stylesheet" href="../css/style.css">
	<link type="text/css" rel="stylesheet" href="../css/add-customer-style.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Warsztat</title>
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Warsztat</h2>
		</div>
	</div>
	
	<%@ include file="../parts/header.jsp" %>
	
	<div id="container">
		<h3>Dodaj pojazd</h3>
		
		<form action="../VehicleControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Marka:</label></td>
						<td><input type="text" name="brand" /></td>
					</tr>

					<tr>
						<td><label>Model:</label></td>
						<td><input type="text" name="model" /></td>
					</tr>

					<tr>
						<td><label>Rok produkcji:</label></td>
						<td><input type="text" name="yearOfProduction" /></td>
					</tr>
					
					<tr>
						<td><label>Rejestracja:</label></td>
						<td><input type="text" name="registration" /></td>
					</tr>
					
					<tr>
						<td><label>Następny serwis:</label></td>
						<td><input type="text" name="nextService" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Zapisz" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="../CustomerControllerServlet">Wróć</a>
		</p>
	</div>
	
	<%@ include file="../parts/footer.jsp" %>  
	
</body>

</html>










