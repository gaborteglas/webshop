window.onload = function() {
    updateTable();
    }

function updateTable() {
 fetch("api/products/A Java ura: A classok szövetsége")
     .then(function(request) {
         return request.json();
     })
     .then(function(jsonData) {
     fillTable(jsonData);
     });
 }

 function fillTable(jsonData){
    let productName = document.querySelector("#product-name");
    let name = jsonData.name
    productName.innerHTML = name;
 }