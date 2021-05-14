package com.gadgetbadget.marketplace.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MarketplaceAPI
 */
@WebServlet("/MarketplaceAPI")
public class MarketplaceAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Product productObj = new Product();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MarketplaceAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject res = productObj.readProducts(null);
		JsonObject table = getTable(res);
		System.out.println(table.toString());
		response.getWriter().append(table.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject res = null;

		try {
			String res_id = request.getParameter("researcherId").toString();
			String product_name = request.getParameter("productName").toString();
			String product_desc = request.getParameter("productDesc").toString();
			String cat_id = request.getParameter("catId").toString();
			int items = Integer.parseInt(request.getParameter("items").toString());
			float price = Float.parseFloat(request.getParameter("price").toString());
			System.out.println(res_id+" "+product_name+" "+product_desc+" "+cat_id+" "+items+" "+price);

			JsonObject insertRes = productObj.insertProduct(res_id, product_name, product_desc, cat_id, items, price);
			System.out.println("res= " + insertRes.toString());

			if (! insertRes.get("STATUS").getAsString().equalsIgnoreCase("SUCCESSFUL")) {
				res = new JsonObject();
				res.addProperty("status", "error");
				res.addProperty("data", "error...");
				response.getWriter().append(res.toString());
				return;
			}

			JsonObject table = getTable(productObj.readProducts(null));
			res = new JsonObject();
			res.addProperty("status", "success");
			res.addProperty("data", table.get("data").getAsString());

		} catch (Exception ex) {
			ex.printStackTrace();
			res = new JsonObject();
			res.addProperty("status", "error");
			res.addProperty("data", "error...");
		}
		response.getWriter().append(res.toString());
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject res = null;

		try {
			Map<String, String> map = getMap(request);
			String prod_id = map.get("hiddenProductId").toString();
			String res_id = map.get("researcherId").toString();
			String product_name = map.get("productName").toString();
			String product_desc = map.get("productDesc").toString();
			String cat_id = map.get("catId").toString();
			int items = Integer.parseInt(map.get("items").toString());
			float price = Float.parseFloat(map.get("price").toString());
			System.out.println(prod_id+" "+res_id+" "+product_name+" "+product_desc+" "+cat_id+" "+items+" "+price);

			JsonObject updateRes = productObj.updateProduct(prod_id, res_id, product_name, product_desc, cat_id, items, price);
			System.out.println("res= " + updateRes.toString());

			if (! updateRes.get("STATUS").getAsString().equalsIgnoreCase("SUCCESSFUL")) {
				res = new JsonObject();
				res.addProperty("status", "error");
				res.addProperty("data", "error...");
				response.getWriter().append(res.toString());
				return;
			}

			JsonObject table = getTable(productObj.readProducts(null));
			res = new JsonObject();
			res.addProperty("status", "success");
			res.addProperty("data", table.get("data").getAsString());

		} catch (Exception ex) {
			ex.printStackTrace();
			res = new JsonObject();
			res.addProperty("status", "error");
			res.addProperty("data", "error...");
		}
		response.getWriter().append(res.toString());
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject res = null;

		try {
			Map<String, String> map = getMap(request);
			String prod_id = map.get("productId").toString();
			System.out.println(prod_id);

			JsonObject deleteRes = productObj.deleteProduct(null, prod_id);
			System.out.println("res= " + deleteRes.toString());

			if (! deleteRes.get("STATUS").getAsString().equalsIgnoreCase("SUCCESSFUL")) {
				res = new JsonObject();
				res.addProperty("status", "error");
				res.addProperty("data", "error...");
				response.getWriter().append(res.toString());
				return;
			}

			JsonObject table = getTable(productObj.readProducts(null));
			res = new JsonObject();
			res.addProperty("status", "success");
			res.addProperty("data", table.get("data").getAsString());

		} catch (Exception ex) {
			ex.printStackTrace();
			res = new JsonObject();
			res.addProperty("status", "error");
			res.addProperty("data", "error...");
		}
		response.getWriter().append(res.toString());
	}

	private JsonObject getTable(JsonObject productList) {
		//generating products table
		JsonObject res;		
		String tableString = "<table class='table table-sm table-striped'>"
				+ "<thead><tr>"
				+ "<th>product ID</th>"
				+ "<th>Researcher ID</th>"
				+ "<th>Product Name</th>"
				+ "<th>Description</th>"
				+ "<th>Category ID</th>"
				+ "<th>Available Items</th>"
				+ "<th>Price</th>"
				+ "<th>Date Added</th>"
				+ "<th>Update</th>"
				+ "<th>Delete</th>"
				+ "</tr><thead><tbody>";

		if(! productList.has("products")) {
			res = new JsonObject();
			res.addProperty("status", "error");
			res.addProperty("data", "error...");
			return res;
		}

		for(JsonElement elem : productList.get("products").getAsJsonArray()) {
			JsonObject product = elem.getAsJsonObject();
			tableString += "<tr><td>"+ product.get("product_id").getAsString() +"</td>"
					+ "<td>"+ product.get("researcher_id").getAsString() +"</td>"
					+ "<td>"+ product.get("product_name").getAsString() +"</td>"
					+ "<td>"+ product.get("product_description").getAsString() +"</td>"
					+ "<td>"+ product.get("category_id").getAsString() +"</td>"
					+ "<td>"+ product.get("available_items").getAsString() +"</td>"
					+ "<td>"+ product.get("price").getAsString() +"</td>"
					+ "<td>"+ product.get("date_added").getAsString() +"</td>"
					+ "<td><input type='button' class='btn btn-primary btnUpdate' id='btnUpdate' data-productid='"+product.get("product_id").getAsString()+"' value='Update'></td>"
					+ "<td><input type='button' class='btn btn-primary btnDelete' id='btnDelete' data-productid='"+product.get("product_id").getAsString()+"' value='Delete'</td></tr>";
		}

		tableString += "</tbody></table>";

		res = new JsonObject();
		res.addProperty("status", "success");
		res.addProperty("data", tableString);

		return res;
	}

	// Convert request parameters to a Map
	private static Map<String,String> getMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params)
			{
				String[] p = param.split("=");

				//decoding the string before putting into the map to avoid undesired strings
				map.put(p[0], java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return map;
	}

}
