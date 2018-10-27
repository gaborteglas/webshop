window.onload = function() {
    updateTable();
    showBasketButton();
    let orderButton = document.getElementById("puttobasket");
}

function handlePutIntoBasket(userId){
    let productNameFromUrl = new URL(window.location).searchParams.get("address");
    let productToFetch = "api/products/" + productNameFromUrl;
    let userName = document.querySelector("#username").innerHTML;
    let productId = document.querySelector("#productId").innerHTML;
    let productName = document.querySelector("#product-name")
    let basket = {"userId" : userId,
                  "productId" : productId};

    fetch("api/basket", {
        method: "POST",
        headers: {
                    "Content-Type": "application/json; charset=utf-8"
                },
        body: JSON.stringify(basket)
    }).then(function(response) {
        if (response.status === 409) {
            alert("A termék már szerepel a kosárban.");
        } else {
            alert("Az alábbi hozzáadva a kosárhoz : " + productName.innerHTML );
        }
    });
}

function updateTable() {
    let productNameFromUrl = new URL(window.location).searchParams.get("address");
    let productToFetch = "api/products/" + productNameFromUrl;
    fetch(productToFetch).then(function(response) {
         return response.json();
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
    idTd.setAttribute("id","productId")
    producerTd.innerHTML = producer;
    currentPriceTd.innerHTML = currentPrice;
    tr.appendChild(idTd);
    tr.appendChild(producerTd);
    tr.appendChild(currentPriceTd);
    tbody.appendChild(tr);
}

function showBasketButton() {
      fetch("api/user")
              .then(function (response) {
                  return response.json();
              })
              .then(function(jsonData) {
                  if (jsonData.role == "ROLE_USER") {
                      switchBasketButton(jsonData.id);
                  }
                  if (jsonData.role == "ROLE_ADMIN") {
                      hideBasketButton();
                  }
              })
              .catch(error => hideBasketButton());
}

function switchBasketButton(userId) {
    let button = document.getElementById("puttobasket");
    button.style.display = "block";
    button.setAttribute("style","display:block");
    button.setAttribute("onclick","handlePutIntoBasket(" + userId + ")");
}

function hideBasketButton() {
   let button1 = document.getElementById("puttobasket");
   button1.style.display = "none";
}