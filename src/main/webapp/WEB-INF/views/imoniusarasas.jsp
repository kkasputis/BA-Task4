<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Įmonių sąrašas</title>
</head>
<body>
<div align="center"><font size="6">
Įmonių sąrašas pagal įvestus paieškos kriterijus:
</font>
</div>
<a href="./" class="grizti">Grižti į paiešką</a>
	<table>
		<tr>

			<th>Pavadinimas</th>
			<th>Vidutinis atlyginimas</th>
			<th>Apdraustūjų skaičius</th>
			<th>Veikla</th>
			<th>Pačiau apie įmonė</th>
		</tr>
		<c:forEach items="${imonesIrasai}" var="irasas">
			<tr>
				<td>${irasas.name}</td>
				<td>${irasas.avgWage}</td>
				<td>${irasas.numInsured}</td>
				<td>${irasas.ecoActName}</td>
			<c:choose>
					<c:when test="${irasas.jarCode == '0'}">
						<td><a href="./imone?li=${irasas.lookupId }">Plačiau..</a></td>
						
					</c:when>
					<c:otherwise>
						<td><a href="./imone?ik=${irasas.jarCode }">Plačiau..</a></td>
					
					</c:otherwise>
		</c:choose>
	
			</tr>

		</c:forEach>
	</table>
</body>
<style>
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 90%;
	margin: auto;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}
.grizti {
margin-left: 5%;
}
</style>
</html>