window.onload = function () {
    updateTable();
    getAddressesForUser();
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
        quantityTd.className = "quantity-field";
        quantityTd.id = "quantityField" + "Row" + k;
        quantityTd.innerHTML = products[k].quantity + " db";
        quantityTd.value = products[k].quantity;
        quantityTd.addEventListener("click", function () {
            modifyQuantity(k, products[k].id, products[k].quantity);
        });
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
        subButton.addEventListener("click",
            function () {
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
    orderButton.disabled = products.length === 0;;

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

function getAddressesForUser() {
    fetch("/api/orders/addresses")
        .then(function (response) {
            return response.json();
        })
        .then(function (addresses) {
            fillSelectWithAddresses(addresses);
        });
}

function fillSelectWithAddresses(addresses) {
    let select = document.querySelector("#address-selector");
    for (let i = 0; i < addresses.length; i++) {
        let option = document.createElement("option");
        option.innerHTML = addresses[i];
        option.setAttribute("value", addresses[i])
        select.appendChild(option);
    }
    select.addEventListener("change", function () { fillDeliveryAddress(this.value) })
}

function fillDeliveryAddress(address) {

    let splittedAddressArray = address.split(" ");
    let splittedAddress = splittedAddressArray.splice(0, 2);
    splittedAddress.push(splittedAddressArray.join(" "));

    let zipCode = document.querySelector("#zip-code-field");
    zipCode.value = splittedAddress[0];

    let city = document.querySelector("#city-field");
    city.value = splittedAddress[1].substring(0, splittedAddress[1].length - 1);

    let street = document.querySelector("#street-field");
    street.value = splittedAddress[2];
}

function increaseQuantityInSQL(productId, quantity) {
    let url = "api/basket/" + productId + "/" + quantity + "/increase";
    fetch(url, {
        method: "POST"
    }).then(function (response) {
        return response.json();
    }).then(responseJson => updateTable())
}

function decreaseQuantityInSQL(productId, quantity) {
    let url = "api/basket/" + productId + "/" + quantity + "/decrease";
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
}

function decreaseQuantityByOne(rowNumber, productId, quantity) {
    if (quantity - 1 < 1) {
        let inputName = "deleteButtonRow" + rowNumber;
        document.querySelector(`input[name=${inputName}]`).click();
        return;
    } else {
        decreaseQuantityInSQL(productId, quantity);
    }
}


function modifyQuantity(rowNumber, productId, quantity) {
    var newQuantity = prompt("Adja meg a kívánt mennyiséget", quantity);
    let url = "api/basket/" + productId + "/" + quantity + "/" + newQuantity;
    fetch(url, {
        method: "POST"
    }).then(function (response) {
        return response.json();
    }).then(responseJson => updateTable())
}

function handleOrderButton() {

    let zipCode = document.querySelector("#zip-code-field").value.trim();
    let city = document.querySelector("#city-field").value.trim();
    let street = document.querySelector("#street-field").value.trim();
    let address = zipCode + " " + city + ", " + street;

    if (confirm("Megrendeli a termékeket?")) {
        fetch("/api/myorders", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=utf-8"
            },
            body: address
        }).then(function (response) {
            window.location = "/myorders.html"
        });
    }
}
