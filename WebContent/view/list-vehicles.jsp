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
			<h2>Pojazdy</h2>
		</div>
	</div>
	
	<%@ include file="../parts/header.jsp" %>

	<div id="container">
	
		<div id="content">
			<input type="button" value="Dodaj pojazd" 
			   onclick="window.location.href='view/add-vehicle.jsp'; return false;"
			   class="add-customer-button"
			/>
			
			<table>
			
				<tr>
					<th>Marka</th>
					<th>Model</th>
					<th>Rok produkcji</th>
					<th>Rejestracja</th>
					<th>Następny serwis</th>
					<th>Zmień</th>
				</tr>
				
				<c:forEach var="tempVehicle" items="${VEHICLES_LIST}">
					
					<c:url var="tempLink" value="VehicleControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="vehicleId" value="${tempVehicle.id}"/>
					</c:url>
					
					<c:url var="deleteLink" value="VehicleControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="vehicleId" value="${tempVehicle.id}"/>
					</c:url>
					
					<tr>
						<td> ${tempVehicle.brand} </td>
						<td> ${tempVehicle.model} </td>
						<td> ${tempVehicle.yearOfProduction} </td>
						<td> ${tempVehicle.registration} </td>
						<td> ${tempVehicle.nextService} </td>
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
