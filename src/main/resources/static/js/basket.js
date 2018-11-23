window.addEventListener('load', updateTable());
window.addEventListener('load', getAddressesForUser());
window.addEventListener('load', activateCartResetButton());
window.addEventListener('load', activateCartOrderButton());
window.addEventListener('load', addressValidatorStarter);

function activateCartOrderButton() {
    let orderButton = document.querySelector("#order-button");
    orderButton.onclick = handleOrderButton;
}

function addressValidatorStarter() {
    let zipCodeField = document.querySelector("#zip-code-field");
    zipCodeField.addEventListener("focusout", zipCodeValidator);
    let cityField = document.querySelector("#city-field");
    cityField.addEventListener("focusout", cityValidator);
    let streetField = document.querySelector("#street-field");
    streetField.addEventListener("focusout", streetValidator);
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
        tr.className = "table-row";

        let imageTd = document.createElement("td");
        imageTd.className = "column-1";
        let imageDiv = document.createElement("div");
        imageDiv.className = "cart-img-product b-rad-4 o-f-hidden";
        imageDiv.setAttribute("name", "deleteButton" + "Row" + k);
        imageDiv.setAttribute("id", "deleteButton" + "Id" + products[k].id);
        imageDiv.addEventListener("click", clickingOnResetProductButtons);
        let image = document.createElement("img");
        image.src = "data:image/png;base64, " + products[k].image;
        image.alt = "IMG-PRODUCT";
        imageDiv.appendChild(image);
        imageTd.appendChild(imageDiv);
        tr.appendChild(imageTd);

        let idTd = document.createElement("td");
        idTd.className = "column-2";
        idTd.innerHTML = products[k].id;
        tr.appendChild(idTd);

        let nameTd = document.createElement("td");
        nameTd.className = "column-3";
        nameTd.innerHTML = products[k].name;
        tr.appendChild(nameTd);

        let producerTd = document.createElement("td");
        producerTd.className = "column-4";
        producerTd.innerHTML = products[k].producer;
        tr.appendChild(producerTd);

        let currentPriceTd = document.createElement("td");
        currentPriceTd.className = "column-5";
        currentPriceTd.innerHTML = products[k].currentPrice.toLocaleString() + " Ft";
        tr.appendChild(currentPriceTd);

        let quantityTd = document.createElement("td");
        quantityTd.className = "column-6";
        let quantityDiv = document.createElement("div");
        quantityDiv.className = "flex-w bo5 of-hidden w-size17";

        let minusButton = document.createElement("button");
        minusButton.className = "btn-num-product-down color1 flex-c-m size7 bg8 eff2";
        let minusIcon = document.createElement("i");
        minusIcon.className = "fs-12 fa fa-minus";
        minusButton.id = "subButton" + "Row" + k;
        minusButton.addEventListener("click",
            function () {
                decreaseQuantityByOne(k, products[k].id, products[k].quantity);
            });
        minusButton.appendChild(minusIcon);

        let quantityInput = document.createElement("input");
        quantityInput.className = "size8 m-text18 t-center num-product";
        quantityInput.type = "number";
        quantityInput.name = "num-product" + k;
        quantityInput.value = products[k].quantity;
        quantityInput.addEventListener("keydown", function (event) {
            modifyQuantityInInput(event.key, products[k].quantity, products[k].id, this);
        });
        quantityInput.addEventListener("focusout", function (event) {
            modifyQuantityInInput("Enter", products[k].quantity, products[k].id, this);
        }
        );

        let plusButton = document.createElement("button");
        plusButton.className = "btn-num-product-up color1 flex-c-m size7 bg8 eff2";
        let plusIcon = document.createElement("i");
        plusIcon.className = "fs-12 fa fa-plus";
        plusButton.id = "sumButton" + "Row" + k;
        plusButton.addEventListener("click", function () {
            increaseQuantityByOne(k, products[k].id, products[k].quantity);
        });
        plusButton.appendChild(plusIcon);

        quantityDiv.appendChild(minusButton);
        quantityDiv.appendChild(quantityInput);
        quantityDiv.appendChild(plusButton);
        quantityTd.appendChild(quantityDiv);
        tr.appendChild(quantityTd);

        let totalPriceTd = document.createElement("td");
        totalPriceTd.className = "column-7";
        totalPriceTd.innerHTML = (products[k].currentPrice * products[k].quantity).toLocaleString() + " Ft";
        tr.appendChild(totalPriceTd);

        totalPrice += products[k].currentPrice * products[k].quantity;
        tbody.appendChild(tr);
    }
    document.querySelector("#total-price-span").innerHTML = totalPrice.toLocaleString() + " Ft";

    let orderButton = document.querySelector("#order-button");
    orderButton.disabled = products.length === 0;
}

function clickingOnResetProductButtons(clickEvent) {
    let deleteButtonId = this.getAttribute("id");
    let productId = parseInt(deleteButtonId.substring(deleteButtonId.indexOf("Id") + 2));
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
    select.onchange = function () {
        fillDeliveryAddress(this.value);
    };
}

function fillDeliveryAddress(address) {
    let splittedAddressArray = address.split(" ");
    let zipCodeValue = splittedAddressArray[0];
    let splittedAddressWithoutZipCode = splittedAddressArray.splice(1).join(" ");
    let splittedAddressCityAndStreet = splittedAddressWithoutZipCode.split(",");
    let cityValue = splittedAddressCityAndStreet[0];
    let streetValue = splittedAddressCityAndStreet[1].trim();

    let zipCode = document.querySelector("#zip-code-field");
    zipCode.value = zipCodeValue;

    let city = document.querySelector("#city-field");
    city.value = cityValue;

    let street = document.querySelector("#street-field");
    street.value = streetValue;
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

function increaseQuantityByOne(rowNumber, productId, quantity) {
    increaseQuantityInSQL(productId, quantity);
}

function decreaseQuantityByOne(rowNumber, productId, quantity) {
    if (quantity - 1 < 1) {
        let inputName = "deleteButtonRow" + rowNumber;
        document.querySelector(`div[name=${inputName}]`).click();
        return;
    } else {
        decreaseQuantityInSQL(productId, quantity);
    }
}

function modifyQuantityInInput(eventKey, oldQuantity, productId, quantityInput) {
    if (eventKey === "Enter") {
        var newQuantity = quantityInput.value;
        if (newQuantity > 0) {
            let url = "api/basket/" + productId + "/" + oldQuantity + "/" + newQuantity;
            fetch(url, {
                method: "POST"
            }).then(function (response) {
                return response.json();
            }).then(responseJson => updateTable())
        }  modifyQuantityInInput("Escape", oldQuantity, productId, quantityInput);
    } else if (eventKey === "Escape") {
        let url = "api/basket/" + productId + "/" + oldQuantity + "/" + newQuantity;
        fetch(url, {
            method: "POST"
        }).then(function (response) {
            return response.json();
        }).then(responseJson => updateTable())
    }
}

function zipCodeValidator() {
    let zipCode = document.querySelector("#zip-code-field").value;
    let zipCodeDiv = document.querySelector("#zip-code-div");
    let zipCodeField = document.querySelector("#zip-code-field");
    if (zipCode === "") {
        zipCodeField.classList.remove("is-valid");
        zipCodeField.classList.add("is-invalid");
        let feedback = document.createElement("div");
        feedback.setAttribute("class", "invalid-feedback");
        feedback.innerHTML = "Az irányítószám megadása kötelező!";
        if (zipCodeDiv.querySelector(".invalid-feedback") == null) {
            zipCodeDiv.appendChild(feedback);
        }
    } else {
        zipCodeField.classList.remove("is-invalid");
        zipCodeField.classList.add("is-valid");
    }
}

function cityValidator() {
    let city = document.querySelector("#city-field").value;
    let cityDiv = document.querySelector("#city-div");
    let cityField = document.querySelector("#city-field");
    if (city === "") {
        cityField.classList.remove("is-valid");
        cityField.classList.add("is-invalid");
        let feedback = document.createElement("div");
        feedback.setAttribute("class", "invalid-feedback");
        feedback.innerHTML = "A város megadása kötelező!";
        if (cityDiv.querySelector(".invalid-feedback") == null) {
            cityDiv.appendChild(feedback);
        }
    } else {
        cityField.classList.remove("is-invalid");
        cityField.classList.add("is-valid");
    }
}

function streetValidator() {
    let street = document.querySelector("#street-field").value.trim();
    let streetDiv = document.querySelector("#street-div");
    let streetField = document.querySelector("#street-field");
    if (street === "") {
        streetField.classList.remove("is-valid");
        streetField.classList.add("is-invalid");
        let feedback = document.createElement("div");
        feedback.setAttribute("class", "invalid-feedback");
        feedback.innerHTML = "Az utca és a házszám megadása kötelező!";
        if (streetDiv.querySelector(".invalid-feedback") == null) {
            streetDiv.appendChild(feedback);
        }
    } else {
        streetField.classList.remove("is-invalid");
        streetField.classList.add("is-valid");
    }
}

function handleOrderButton() {
    let zipCode = document.querySelector("#zip-code-field").value.trim().replace(/ /g, '');
    let city = document.querySelector("#city-field").value.trim();
    let street = document.querySelector("#street-field").value.trim();
    let address = zipCode + " " + city + ", " + street;

    if (zipCode !== "" && city !== "" && street !== "") {
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
    } else {
        zipCodeValidator();
        cityValidator();
        streetValidator();
    }
}
