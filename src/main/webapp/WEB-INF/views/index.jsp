<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Įmonių duomenų bazė</title>
</head>
<body>
<div class="topic" align="center" >
<font size="5">
Čia yra visos įmonės iš SoDra pateikiamos duomenų bazės.
</font>
</div>
<div class="searchtitle"> <font size="4">
Ieškoti imonės: 
</font>
</div>



<form:form method="POST" modelAttribute="search" class="paieska">
		<section>
		
			<form:errors path="*" element="div" />
			<fieldset>
				<legend>Įveskite vieną iš paieškos kriterijų:</legend>

				<div>
					<label for="ik">Įmonės kodas</label>
					<form:input path="ik" id="ik" type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" onchange="if (this.value==null || this.value==false) { this.value = 0; }"/>
					<form:errors path="ik" />
					</div>
					
					<div>					
					<label for="name">Imonės pavadinimas</label>
					<form:input path="name" id="name" type="text"/>
					<form:errors path="name" />
					</div>
					<div>
					<label for="wage">Vidutinis atlyginimas nuo...</label>
					<form:input path="avgWage" id="wage" type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" onchange="if (this.value==null || this.value==false) { this.value = 0; }"/>
					<form:errors path="avgWage" />
				</div>



				<input type="submit" value="Ieškoti" />

			</fieldset>
		</section>
	</form:form>
</body>
<style>
.paieska {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
  margin-top: 20px;
  width: 80%;
  margin: auto;
}
input[type=text] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
.topic {
margin-top: 50px;
margin-bottom: 50px;
}
input[type=submit] {
  width: 100%;
  background-color: #c9c9c9;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #dbdbdb;
}
.searchtitle {
margin-left: 10%;
}
</style>
</html>