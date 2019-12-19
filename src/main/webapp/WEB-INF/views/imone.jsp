<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${imonesIrasai.get(0).name}</title>
</head>
<body>
<a href="./">Grižti į paiešką</a>
	<table>
		<tr>
			<th>Draudejo kodas</th>
			<th>Įmonės kodas</th>
			<th>Pavadinimas</th>
			<th>Trumpas pavadinimas</th>
			<th>Mėnuo</th>
			<th>Vidutinis atlyginimas</th>
			<th>Vidutinis atlyginimas dirbančių ne pagal darbo sutartį</th>
			<th>Apdraustūjų skaičius</th>
			<th>Dirbančių ne pagal sutartį skaičius</th>
			<th>valstybinio socialinio draudimo įmokų suma</th>
			<th>Veikla</th>
			<th>Veiklos numeris</th>
			<th>Apylinkė</th>
			

		</tr>
		<c:forEach items="${imonesIrasai}" var="irasas">
			<tr>
				<td>${irasas.code}</td>
				<td>${irasas.jarCode}</td>
				<td>${irasas.name}</td>
				<td>${irasas.shortname}</td>
				<td>${irasas.month}</td>
				<td>${irasas.avgWage}</td>
				<td>${irasas.avgWage2}</td>
				<td>${irasas.numInsured}</td>
				<td>${irasas.numInsured2}</td>
				<td>${irasas.tax}</td>
				<td>${irasas.ecoActName}</td>
				<td>${irasas.ecoActCode}</td>
				<td>${irasas.municipality}</td>

			</tr>

		</c:forEach>
	</table>
	<div>
	<a href="download?li=${imonesIrasai.get(0).lookupId}">Parsisiųsti CSV formatu.</a>
	</div>
</body>
<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>
</html>