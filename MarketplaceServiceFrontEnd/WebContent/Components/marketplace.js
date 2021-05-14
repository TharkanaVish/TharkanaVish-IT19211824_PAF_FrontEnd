$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
    $("#btncancel").addClass('invisible');
    showProducts();
});


function showProducts() {
	$.ajax(
        {
            url: "MarketplaceAPI",
            type: "GET",
            dataType: "text",
            complete: function (response, status) {
                onGetComplete(response.responseText, status);
            }
        });
}


function onGetComplete(response, status) {
	if (status == "success") {
        var res = JSON.parse(response);
        $("#productsGrid").html(res.data);
    } else if (status == "error") {
        $("#alertError").text("error...");
        $("#alertError").show();
    } else {
        $("#alertError").text("error...");
        $("#alertError").show();
    }
}


$(document).on("click", "#btnSubmit", function (event) {
    // Clear alerts
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    // Form validation
    var status = validateForm();
    if (status != true) {
        $("#alertError").text(status);
        $("#alertError").show();
        return;
    }
    // If valid
    //check if put or post
    var type = ($("#hiddenProductId").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "MarketplaceAPI",
            type: type,
            data: $("#productsForm").serialize(),
            dataType: "text",
            complete: function (response, status) {
                onSaveComplete(response.responseText, status);
            }
        });
});


function onSaveComplete(response, status) {
    if (status == "success") {
        var res = JSON.parse(response);
        if (res.status.trim() == "success") {
            $("#alertSuccess").text("succeeded...");
            $("#alertSuccess").show();
            $("#productsGrid").html(res.data);
        } else if (res.status.trim() == "error") {
            $("#alertError").text(res.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("error...");
        $("#alertError").show();
    } else {
        $("#alertError").text("error...");
        $("#alertError").show();
    }
    $("#hiddenProductId").val("");
    $("#btnSubmit").text("Add Product");
    $("#productsForm")[0].reset();
}


$(document).on("click", "#btnUpdate", function (event) {
    $("#hiddenProductId").val($(this).data("productid"));
    $("#btnSubmit").text("Update Product");
    $("#researcherId").val($(this).closest("tr").find('td:eq(1)').text());
    $("#productName").val($(this).closest("tr").find('td:eq(2)').text());
    $("#productDesc").val($(this).closest("tr").find('td:eq(3)').text());
    $("#catId").val($(this).closest("tr").find('td:eq(4)').text());
    $("#items").val($(this).closest("tr").find('td:eq(5)').text());
    $("#price").val($(this).closest("tr").find('td:eq(6)').text());
});


$(document).on("click", "#btnDelete", function (event) {
    $.ajax(
        {
            url: "MarketplaceAPI",
            type: "DELETE",
            data: "productId=" + $(this).data("productid"),
            dataType: "text",
            complete: function (response, status) {
                onDeleteComplete(response.responseText, status);
            }
        });
});


function onDeleteComplete(response, status) {
    if (status == "success") {
        var res = JSON.parse(response);
        if (res.status.trim() == "success") {
            $("#alertSuccess").text("succeeded...");
            $("#alertSuccess").show();
            $("#productsGrid").html(res.data);
        } else if (res.status.trim() == "error") {
            $("#alertError").text(res.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("error ...");
        $("#alertError").show();
    } else {
        $("#alertError").text("error ...");
        $("#alertError").show();
    }
}


//Validate Form
function validateForm() {
    //validations using form input ids
    if($("#researcherId").val().trim() == "") {
    	return "Researcher ID cannot be empty."
    }
    
    if($("#productName").val().trim() == "") {
    	return "Product Name cannot be empty."
    }
    
    if($("#productDesc").val().trim() == "") {
    	return "Product Description cannot be empty."
    }
    
    if($("#catId").val().trim() == "") {
    	return "Category ID cannot be empty."
    }
    
    var items = $("#items").val().trim();
    if(items == "") {
    	return "Number of items cannot be empty."
    }
    
    if(! $.isNumeric(items)) {
    	return "Invalid number of items."
    }
    
    var price = $("#price").val().trim();
    if(price == "") {
    	return "Price cannot be empty."
    }
    
    if(! $.isNumeric(price)) {
    	return "Invalid price."
    }
    
    return true;
}