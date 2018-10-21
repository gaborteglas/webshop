window.onload = function() {
    updateTable();
    }

    function updateTable() {
    let productNameFromUrl = getAllUrlParams(window.location.href);
    let productToFetch = "api/products/" + productNameFromUrl;
     fetch(productToFetch)
         .then(function(request) {
             return request.json();
         })
         .then(function(jsonData) {
         fillTable(jsonData);
         })
         .catch(error => creatingHeaderForName("Nincs ilyen termék"));
     }

 function fillTable(jsonData){
    let name = jsonData.name;
    let id = jsonData.id;
    let producer = jsonData.producer;
    let currentPrice = jsonData.currentPrice + " Ft";
    creatingHeaderForName(name);
    creatingTableRowForData(id,producer,currentPrice)
    console.log(jsonData)
 }

 function creatingHeaderForName(name){
    let productName = document.querySelector("#product-name");
    productName.innerHTML = name;
 }

 function creatingTableRowForData(id,producer,currentPrice){
    let tbody = document.querySelector("#product-tbody");
    let tr = document.createElement("tr");
    tr["raw-data"] = id;
    let idTd = document.createElement("td");
    let producerTd = document.createElement("td");
    let currentPriceTd = document.createElement("td");
    idTd.innerHTML = id;
    producerTd.innerHTML = producer;
    currentPriceTd.innerHTML = currentPrice;
    tr.appendChild(idTd);
    tr.appendChild(producerTd);
    tr.appendChild(currentPriceTd);
    tbody.appendChild(tr);
 }

 function getAllUrlParams(url) {
    let queryString = url ? url.split('=')[1] : window.location.search.slice(1);
    return queryString;
    }