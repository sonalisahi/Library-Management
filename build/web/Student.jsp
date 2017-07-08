<%-- 
    Document   : Student
    Created on : Jul 8, 2017, 7:09:41 PM
    Author     : sunny
--%>

<!DOCTYPE html>

<html>
    <head>
   <div style="position: absolute; height:80px; width: 1350px;background-image:url(IMAGES/sd1.jpg)">
       </div>
</head>
<body bgcolor=" lightskyblue" onload="startTime()">
<% 
String sess=(String)session.getAttribute("name");
session.setAttribute("uname",sess);
%>
<div style="position:absolute;top:85px;height:60px;width:1350px;background-color: lightskyblue">
<b>Welcome<%=" "+sess+" "%></b>&nbsp&nbsp&nbsp&nbsp;<a href="Logout.jsp">Logout</a>
<table align="center" cellpadding=15>
<tr><td><a href="BookDetails">BOOKS DETAILS</a></td> 
    <td><a href="EachBookDetails.jsp">BOOKS SUMMARY AND NO.OF COPIES</a></td> 
    <td> <a href="search.html">SEARCH BOOKS</a></td>
    <td> <a href="contactus.html">CONTACT US!</a></td>
</tr>
</table>
</div><br><br>
      <br><br>
      <br><br>
      <br><br>
      <br><br>

     <div align="center"><img src="IMAGES/ADMN1.jpg"></div>
     <br><br>
     <br><br>
     
     
     <div align="center"><h1><i><b>!!!WELCOME STUDENT!!!</i></b></h1></div>
 </body>
</html>


