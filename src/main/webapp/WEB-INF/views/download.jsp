<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Parisiųsti CSV</title>
</head>
<body>
<div align="center" class="pav"><font size="6">Failo parsiuntimas</font></div>
<div class="atsiusti" align="center">
<div>
<form method="get" action="../csvfiles/${filename}" download>
   <button type="submit">Atsiųsti</button>
</form>
</div>
<div>
<a href="./">Grįžti į paiešką</a>
</div>
</div>
</body>
<style>
.pav {
margin-top: 100px;

}
.atsiusti {
margin-top: 30px;

}
button[type=submit] {
  width: 30%;
  background-color: #c9c9c9;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button[type=submit]:hover {
  background-color: #dbdbdb;
}
</style>

</html>