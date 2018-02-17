<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Klienci</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Klienci warsztatu</h2>
		</div>
	</div>
	
	<%@ include file="../parts/header.jsp" %>

	<div id="container">
	
		<div id="content">
			<input type="button" value="Dodaj klienta" 
			   onclick="window.location.href='view/add-customer.jsp'; return false;"
			   class="add-customer-button"
			/>
			
			 <form action="CustomerControllerServlet" method="GET">
        
                <input type="hidden" name="command" value="SEARCH" />
            
                Szukaj klienta: <input type="text" name="theSearchName" placeholder="imię lub nazwisko"/>
                
                <input type="submit" value="Szukaj" class="add-customer-button" />
            
            </form>
            
			<table>
			
				<tr>
					<th>Imię</th>
					<th>Nazwisko</th>
					<th>Email</th>
					<th>Pojazdy</th>
					<th>Zmień</th>
				</tr>
				
				<c:forEach var="tempCustomer" items="${CUSTOMER_LIST}">
					
					<c:url var="tempLink" value="CustomerControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="customerId" value="${tempCustomer.id}"/>
					</c:url>
					
					<c:url var="deleteLink" value="CustomerControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="customerId" value="${tempCustomer.id}"/>
					</c:url>
					
					<c:url var="vehicleLink" value="VehicleControllerServlet">
						<c:param name="command" value="VEHICLE" />
						<c:param name="customerId" value="${tempCustomer.id}"/>
					</c:url>
					
					<tr>
						<td> ${tempCustomer.firstName} </td>
						<td> ${tempCustomer.lastName} </td>
						<td> ${tempCustomer.email} </td>
						<td><a href="${vehicleLink}">Lista</a></td>
						<td> 
							<a href="${tempLink}">Uaktualnij</a> 
						 	|
							<a href="${deleteLink}" onclick="if (!(confirm('Czy jesteś pewny/a?'))) return false">Usuń</a>
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
	
	<%@ include file="../parts/footer.jsp" %>  
	
</body>


</html>
