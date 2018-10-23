window.onload = function() {
    updateTable();
    showBasketButton();

    let putIntoBasketButton = document.querySelector("#puttobasket");
    putIntoBasketButton.addEventListener("click", handlePutIntoBasket);
}

function handlePutIntoBasket(){
    let productNameFromUrl = new URL(window.location).searchParams.get("address");
    let productToFetch = "api/products/" + productNameFromUrl;
    let userName = document.querySelector("#username").innerHTML;
    let productId = 5;
    let basket = {"userId" : 5,
                  "productId" : productId};
    fetch("api/basket", {
                    method: "POST",
                    headers: {
                                "Content-Type": "application/json; charset=utf-8"
                            },
                    body: JSON.stringify(basket)
    })
}

function updateTable() {
    let productNameFromUrl = new URL(window.location).searchParams.get("address");
    let productToFetch = "api/products/" + productNameFromUrl;
    fetch(productToFetch).then(function(request) {
         return request.json();
    }).then(function(jsonData) {
        fillTable(jsonData);
    }).catch(error => creatingHeaderForName("Nincs ilyen termék"));
 }

function fillTable(jsonData){
    let name = jsonData.name;
    let id = jsonData.id;
    let producer = jsonData.producer;
    let currentPrice = jsonData.currentPrice + " Ft";
    creatingHeaderForName(name);
    creatingTableRowForData(id,producer,currentPrice)
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

function showBasketButton() {
      fetch("api/user")
              .then(function (request) {
                  return request.json();
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
}

function hideBasketButton() {
   let button1 = document.getElementById("puttobasket");
   button1.style.display = "none";
}