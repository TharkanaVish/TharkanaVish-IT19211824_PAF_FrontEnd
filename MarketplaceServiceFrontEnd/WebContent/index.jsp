<%@ page import = "com.gadgetbadget.marketplace.model.Product" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
if (request.getParameter("action") != null)
{ 
	 Product productObj = new Product();
	 
	 //itemObj.connect();//For testing the connect method
	 
	 //call insert function
	 if ( request.getParameter("action").equalsIgnoreCase("insert"))
	 {
		 String stsMsg = productObj.insertProduct(request.getParameter("researcher_id"),
				 request.getParameter("product_name"), 
				 request.getParameter("product_description"),
				 request.getParameter("category_id") ,
				 Integer.parseInt(request.getParameter("available_items")),
				 Float.parseFloat(request.getParameter("price"))).toString();
 		 session.setAttribute("statusMsg", stsMsg);
 	 }
	 
	 //call update function
	 else if (request.getParameter("action").equalsIgnoreCase("update"))
	 {
		System.out.println("newUpdate");
	    String stsMsg = productObj.updateProduct(request.getParameter("product_id"),
	    		request.getParameter("researcher_id"),
				request.getParameter("product_name"), 
				request.getParameter("product_description"),
				request.getParameter("category_id") ,
				Integer.parseInt(request.getParameter("available_items")),
				Float.parseFloat(request.getParameter("price"))).toString();
	 	session.setAttribute("statusMsg", stsMsg);
	 }
	 
	 //call delete function
	 else if(request.getParameter("action").equalsIgnoreCase("delete"))
	 {
		System.out.println("newDelete");
	 	String stsMsg = productObj.deleteProduct(request.getParameter("researcher_id"), request.getParameter("product_id")).toString();
	    session.setAttribute("statusMsg", stsMsg);
	 }
 

}	
 %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Marketplace</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/marketplace.js"></script>
</head>
<body>
<div class="container">
<div class="row">
<div class="col-8">
 
 <h1 class="m-3">MarketPlace</h1>
 
 <form id="formproduct">
	<!--RESEARCHER ID -->
	<div class="input-group input-group-sm mb-3">
	<div class="input-group-prepend">
				<span class="input-group-text" id="lblName">Researcher ID : </span>
				 </div>
				 <input type="text" id="researcher_id" name="researcher_id">
				 </div>
			 
	<!--PRODUCT NAME-->
	<div class="input-group input-group-sm mb-3">
	<div class="input-group-prepend">
				<span class="input-group-text" id="lblName">Product Name : </span>
				 </div>
				 <input type="text" id="product_name" name="product_name">
				 </div>
				 
	<!--PRODUCT DESCRIPTION-->
	<div class="input-group input-group-sm mb-3">
	<div class="input-group-prepend">
				<span class="input-group-text" id="lblName">Product Description : </span>
				 </div>
				 <input type="text" id="product_description" name="product_description">
				 </div>
		 
	<!-- CATEGORY ID -->
	<div class="input-group input-group-sm mb-3">
	<div class="input-group-prepend">
	 		<span class="input-group-text" id="lblName">
	 			<label for="category_id"> Category ID :</label></span>
				 </div>
				<select name="category_id" id="category_id">
					<option value="PC21000000">---Select Product Category---</option>
				    <option value="PC21000001">PC21000001</option>
				    <option value="PC21000002">PC21000002</option>
				    <option value="PC21000003">PC21000003</option>
				    <option value="PC21000004">PC21000004</option>
				    <option value="PC21000005">PC21000005</option>
				    <option value="PC21000006">PC21000006</option>
				    <option value="PC21000007">PC21000007</option>
				    <option value="PC21000008">PC21000008</option>
				    <option value="PC21000009">PC21000009</option>
				    <option value="PC210000010">PC21000010</option>
				</select>
				 </div>
		 
    <!--AVAILABLE ITEMS-->
	<div class="input-group input-group-sm mb-3">
	<div class="input-group-prepend">
				<span class="input-group-text" id="lblName">Available Items : </span>
				 </div>
				 <input type="text" id="available_items" name="available_items">
				 </div>
				 
	<!--PRICE-->
	<div class="input-group input-group-sm mb-3">
	<div class="input-group-prepend">
				<span class="input-group-text" id="lblName">Price : </span>
				 </div>
				 <input type="text" id="price" name="price">
				 </div>
				 
 <div id="alertSuccess" class="alert alert-success"></div>
 <div id="alertError" class="alert alert-danger"></div>
<input type="button" id="btnSave" value="ADD Product" class="btn btn-primary">
</form>
</div>
</div>
 
<br>
 
<div class="row">
<div class="col-12" id="colProducts">
 
</div>
</div>
</div>
</body>
</html>