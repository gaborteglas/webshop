window.onload = function() {
    updateTable();

    let productForm = document.getElementById("product-form");
    productForm.onsubmit = handleSubmit;
    productForm.onreset = handleReset;
}

function updateTable() {
     fetch("api/allproducts")
             .then(function (response) {
                 return response.json();
             })
             .then(function(jsonData) {
                 fillTable(jsonData);
             });
}

function fillTable(products) {
    let tbody = document.getElementById("products-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < products.length; i++) {
        let product  = products[i];
        let tr = document.createElement("tr");
        tr["raw-data"] = product;

        let idTd = document.createElement("td");
        idTd.innerHTML = product.id;
        tr.appendChild(idTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = product.name;
        tr.appendChild(nameTd);

        let addressTd = document.createElement("td");
        addressTd.innerHTML = product.address;
        tr.appendChild(addressTd);

        let producerTd = document.createElement("td");
        producerTd.innerHTML = product.producer;
        tr.appendChild(producerTd);

        let priceTd = document.createElement("td");
        priceTd.innerHTML = product.currentPrice;
        tr.appendChild(priceTd);

        let statusTd = document.createElement("td");
        if(product.status === "active") {
            statusTd.innerHTML = "aktív";
        } else {
            statusTd.innerHTML = "inaktív";
        }
        tr.appendChild(statusTd);

        let buttonsTd = document.createElement("td");
        let editButton = document.createElement("button");
        let deleteButton = document.createElement("button");
        editButton.setAttribute("class", "btn btn-primary");
        deleteButton.setAttribute("class", "btn btn-danger");
        editButton.innerHTML = "Szerkesztés";
        deleteButton.innerHTML = "Törlés";
        editButton.onclick = handleEditButtonOnClick;
        deleteButton.onclick = handleDeleteButtonOnClick;
        buttonsTd.appendChild(editButton);
        buttonsTd.appendChild(deleteButton);
        tr.appendChild(buttonsTd);

        tbody.appendChild(tr);
    }
}

let editedProduct = null;

function handleSubmit() {

    let idInput = document.getElementById("id-input");
    let nameInput = document.getElementById("name-input");
    let addressInput = document.getElementById("address-input");
    let producerInput = document.getElementById("producer-input");
    let priceInput = document.getElementById("price-input");

    let id = idInput.value;
    let name = nameInput.value;
    let address = addressInput.value;
    let producer = producerInput.value;
    let price = priceInput.value;

    let product = {"id": id,
                   "name": name,
                   "address": address,
                   "producer": producer,
                   "currentPrice": price
                  };

    let url = "api/products";
    if (editedProduct !== null) {
        url += "/" + editedProduct.id;
    }

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
                },
        body: JSON.stringify(product)
    })
        .then(function(response) {

        if (response.status === 500) {
            alert("Az árnak léteznie kell, és nem lehet több, mint 2.000.000.");
        } else if (response.status === 409) {
            alert("A megadott id vagy cím már foglalt. ");
        } else {
                alert("Hozzáadva.");
                updateTable();
                handleReset();
            }
        });
        return false;
}

function handleReset() {
    editedProduct = null;
    let idInput = document.getElementById("id-input");
    idInput.value = "";
    let nameInput = document.getElementById("name-input");
    nameInput.value = "";
    let addressInput = document.getElementById("address-input");
    addressInput.value = "";
    let producerInput = document.getElementById("producer-input");
    producerInput.value = "";
    let priceInput = document.getElementById("price-input");
    priceInput.value = "";


    let submitButton = document.getElementById("submit-button");
    submitButton.value = "Új termék hozzáadása";
}

function handleEditButtonOnClick() {
    let product = this.parentElement.parentElement["raw-data"];
    editedProduct = product;

    let idInput = document.getElementById("id-input");
    idInput.value = product.id;

    let nameInput = document.getElementById("name-input");
    nameInput.value = product.name;

    let addressInput = document.getElementById("address-input");
    addressInput.value = product.address;

    let producerInput = document.getElementById("producer-input");
    producerInput.value = product.producer;

    let priceInput = document.getElementById("price-input");
    priceInput.value = product.currentPrice;

    let submitButton = document.getElementById("submit-button");
    submitButton.value = "Mentés";
}

function handleDeleteButtonOnClick() {
    var result = confirm("Biztosan törli a kijelölt terméket?");
    if (result) {
        let product = this.parentElement.parentElement["raw-data"];

        fetch("api/products/" + product.id, {
            method: "DELETE",
        })
        .then(function(response) {
            updateTable();
        });
    }
}
