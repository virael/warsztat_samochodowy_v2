<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">	
	<link type="text/css" rel="stylesheet" href="css/add-customer-style.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Uaktualnij dane klienta</title>
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Warsztat</h2>
		</div>
	</div>
	
	<%@ include file="../parts/header.jsp" %>
	
	<div id="container">
		<h3>Uaktualnij dane klienta</h3>
		
		<form action="CustomerControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />
			
			<input type="hidden" name="customerId" value="${THE_CUSTOMER.id}" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Imię:</label></td>
						<td><input type="text" name="firstName" value="${THE_CUSTOMER.firstName}"/></td>
					</tr>

					<tr>
						<td><label>Nazwisko:</label></td>
						<td><input type="text" name="lastName" value="${THE_CUSTOMER.lastName}"/></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" value="${THE_CUSTOMER.email}"/></td>
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
			<a href="CustomerControllerServlet">Wróć</a>
		</p>
	</div>
	
	<%@ include file="../parts/footer.jsp" %>  
	
</body>

</html>










