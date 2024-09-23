<%@page import="com.internship.URL"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="short" method="post">

<h1>Enter the URL that needed to be Short</h1>
<br>
<input type="text" placeholder="Enter the url" name="url">
<br>
<input type="submit">
</form>
<%
List<URL> urls=(List<URL>)request.getAttribute("urls");
%>
<p>
<%
for(URL url:urls)  
{
%>
<br>
The Actual Url is :  <%= url.getActualUrl()%>
<br>
The Shortend Url is  :<%="http://localhost:8080/ShortenURL/redirect/"+url.getShortUrl() %>
<br>
<br>
<%
}
%>


</p>

</body>
</html>