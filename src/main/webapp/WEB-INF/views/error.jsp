<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Klaida</title>
</head>
<body>
<!-- Sunkiai man su frontu sekasi.. :) -->
<div class="error_message">
<div>
<font size="6">
Klaida!
</font>
</div>
<div>
<font size="6">
${klaida}
</font>
</div>
<div>
<form method="get" action="./">
   <button type="submit">Grižti į paiešką</button>
</form>
</div>
</div>
</body>
<style>
.error_message {
text-align: center;
margin-top: 100px;
}
</style>
</html>