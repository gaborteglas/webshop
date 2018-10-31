window.onload = function() {
    updateTable();
    showBasketButton();
    let putIntoBasketButton = document.getElementById("puttobasket");
    putIntoBasketButton.onclick = handlePutIntoBasket;

}

function handlePutIntoBasket(){
    let productId = document.querySelector("#productId").innerHTML;
    let quantity = document.querySelector("#quantity").value;

    fetch("api/basket/" + productId + "/" + quantity, {
        method: "POST"
    }).then(function(response) {
        return response.json()
    }).then(function(jsonData) {
        alert(jsonData.message);
    });
    return false;
}

function updateTable() {
    let productNameFromUrl = new URL(window.location).searchParams.get("address");
    let productToFetch = "api/products/" + productNameFromUrl;
    fetch(productToFetch, {
        method: "GET"
    }).then(function(response) {
         return response.json();
    }).then(function(jsonData) {
        fillTable(jsonData);
    }).catch(error => creatingHeaderForName("Nincs ilyen termék"));
 }

function fillTable(jsonData){
    let name = jsonData.name;
    let id = jsonData.id;
    let producer = jsonData.producer;
    let currentPrice = jsonData.currentPrice;
    let categoryName = jsonData.category.name;
    creatingHeaderForName(name);
    creatingTableRowForData(id,producer,currentPrice, categoryName);

}

function creatingHeaderForName(name){
    let productName = document.querySelector("#product-name");
    productName.innerHTML = name;
}

function creatingTableRowForData(id,producer,currentPrice, categoryName){
    let tbody = document.querySelector("#product-tbody");
    let tr = document.createElement("tr");
    tr["raw-data"] = id;
    let idTd = document.createElement("td");
    let producerTd = document.createElement("td");
    let currentPriceTd = document.createElement("td");
    let categoryTd = document.createElement("td");
    idTd.innerHTML = id;
    idTd.setAttribute("id","productId")
    producerTd.innerHTML = producer;
    currentPriceTd.innerHTML = currentPrice + " Ft";
    categoryTd.innerHTML = categoryName;
    tr.appendChild(idTd);
    tr.appendChild(producerTd);
    tr.appendChild(currentPriceTd);
    tr.appendChild(categoryTd);
    tbody.appendChild(tr);
}

function showBasketButton() {
      fetch("api/user")
              .then(function (response) {
                  return response.json();
              })
              .then(function(jsonData) {
                  if (jsonData.role == "ROLE_USER") {
                      switchBasketButton();
                  }
                  if (jsonData.role == "ROLE_ADMIN") {
                      hideBasketButton();
                  }
              })
              .catch(error => hideBasketButton());
}

function switchBasketButton() {
    let button = document.getElementById("puttobasket");
    button.style.display = "block";
    button.setAttribute("style","display:block");

    let input = document.getElementById("quantity");
    input.style.display = "block";
    input.setAttribute("style","display:block");
}

function hideBasketButton() {
   let button1 = document.getElementById("puttobasket");
   button1.style.display = "none";

   let input = document.getElementById("quantity");
   input.style.display = "none";
}