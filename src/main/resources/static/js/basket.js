window.onload = function () {
    updateTable();
    let resetButton = document.querySelector("#reset-button");
    resetButton.onclick = handleResetButton;
    let orderButton = document.querySelector("#order-button");
    orderButton.onclick = handleOrderButton;
};

function updateTable() {
    fetch("api/basket")
        .then(function (response) {
            return response.json();
        })
        .then(function (products) {
            fillTable(products);
        });
}

function fillTable(products) {
    let totalPrice = 0
    let tbody = document.querySelector("#basket-tbody");
    tbody.innerHTML = "";
    for (let k = 0; k < products.length; k++) {
        let tr = document.createElement("tr");

        let idTd = document.createElement("td");
        idTd.innerHTML = products[k].id;
        tr.appendChild(idTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = products[k].name;
        tr.appendChild(nameTd);

        let producerTd = document.createElement("td");
        producerTd.innerHTML = products[k].producer;
        tr.appendChild(producerTd);

        let currentPriceTd = document.createElement("td");
        currentPriceTd.innerHTML = products[k].currentPrice + " Ft";
        tr.appendChild(currentPriceTd);

        let quantityTd = document.createElement("td");
        quantityTd.id = "quantityField" + "Row" + k;
        quantityTd.innerHTML = products[k].quantity + " db";
        quantityTd.value = products[k].quantity;
        tr.appendChild(quantityTd);

        let sumButton = document.createElement("button");
        sumButton.className = "sumSubButtons";
        sumButton.id = "sumButton" + "Row" + k;
        sumButton.innerHTML = "+";
        sumButton.addEventListener("click", function () {
            increaseQuantityByOne(k, products[k].id, products[k].quantity);
        });
        tr.appendChild(sumButton);

        let subButton = document.createElement("button");
        subButton.className = "sumSubButtons";
        subButton.id = "subButton" + "Row" + k;
        subButton.innerHTML = "-";
        subButton.addEventListener("click", function () {
            decreaseQuantityByOne(k, products[k].id, products[k].quantity);
        });
        tr.appendChild(subButton);

        deleteButton = document.createElement("input");
        deleteButton.setAttribute("type", "reset");
        deleteButton.setAttribute("id", "deleteButton" + "Id" + products[k].id);
        deleteButton.setAttribute("name", "deleteButton" + "Row" + k);
        deleteButton.setAttribute("class", "btn btn-danger resetProductButtons")
        deleteButton.setAttribute("value", "Törlés");
        deleteButton.onclick = clickingOnResetProductButtons;

        totalPrice += products[k].currentPrice * products[k].quantity;
        tr.appendChild(deleteButton);
        tbody.appendChild(tr);
    }
    let sumParagraph = document.querySelector("#totalPrice");
    sumParagraph.innerHTML = "A kosár tartalmának ára összesen : " + totalPrice + " Ft";

    let orderButton = document.querySelector("#order-button");
    orderButton.disabled = products.length === 0;

    let resetButton = document.querySelector("#reset-button");
    resetButton.disabled = products.length === 0;
}

function clickingOnResetProductButtons(clickEvent) {
    let deleteButtonId = this.getAttribute("id");
    let productId = parseInt(deleteButtonId.substring(deleteButtonId.indexOf("Id") + 2));
    console.log(productId);
    let url = "api/basket/" + productId;
    if (confirm("Biztos szeretné törölni ezt az elemet a kosárból?")) {
        fetch(url, {
            method: "DELETE"
        }).then(function (response) {
            return response.json()
        }).then(responseJson => updateTable())
    }
}

function increaseQuantityInSQL(productId, quantity) {
    let url = "api/basket/" + productId + "/" + quantity + "/increase";
    fetch(url, {
        method: "POST"
    }).then(function (response) {
        return response.json();
    }).then(responseJson => updateTable())
}

function handleResetButton() {
    if (confirm("Biztos hogy üríteni szeretné a kosár tartalmát?")) {
        fetch("api/basket/", {
            method: "DELETE"
        }).then(function (response) {
            return response.json()
        }).then(responseJson => updateTable())
    }
}

function increaseQuantityByOne(rowNumber, productId, quantity) {
    increaseQuantityInSQL(productId, quantity);
    let quantityElement = document.querySelector("#quantityFieldRow" + rowNumber);
    let newQuantity = quantityElement.value += 1;
    quantityElement.innerHTML = newQuantity + " db";
}

function decreaseQuantityByOne(rowNumber, productId, quantity) {
    if (newQuantity < 1) {
        let inputName = "deleteButtonRow" + rowNumber;
        document.querySelector(`input[name=${inputName}]`).click();
        return;
    }
    let quantityElement = document.querySelector("#quantityFieldRow" + rowNumber);
    let newQuantity = quantityElement.value -= 1;
    quantityElement.innerHTML = newQuantity + " db";
}

function handleOrderButton() {
    if (confirm("Megrendeli a termékeket?")) {
        fetch("/api/myorders", {
            method: "POST"
        }).then(function (response) {
            window.location = "/myorders.html"
        });
    }
}
