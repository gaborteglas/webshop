window.onload = function() {
    getCategories();
    updateTable();

    let productForm = document.getElementById("product-form");
    productForm.onsubmit = handleSubmit;
    productForm.onreset = handleReset;
    let imageForm = document.getElementById("picture-form");
    imageForm.onsubmit = uploadImage;
    let newProductButton = document.getElementById("new-product-button");
    newProductButton.onclick = handleNewProductButton;
}

function updateTable() {
    fetch("api/products")
             .then(function (response) {
                 return response.json();
             })
             .then(function(jsonData) {
                 fillTable(jsonData);
             });
}

function fillTable(products) {
    let tbody = document.getElementById("products-admin-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < products.length; i++) {
        let product  = products[i];
        let tr = document.createElement("tr");
        tr["raw-data"] = product;

        let imageTd = document.createElement("td");
        imageTd.className = "column-1";
        let imageDiv = document.createElement("div");
        imageDiv.className = "cart-img-product b-rad-4 o-f-hidden";
        let image = document.createElement("img");
        image.src = "data:image/png;base64, " + product.image;
        image.alt = "IMG-PRODUCT";
        imageDiv.appendChild(image);
        imageTd.appendChild(imageDiv);
        tr.appendChild(imageTd);

        let idTd = document.createElement("td");
        idTd.innerHTML = product.id;
        idTd.className = "column1";
        tr.appendChild(idTd);

        let producerTd = document.createElement("td");
        producerTd.innerHTML = product.producer;
        producerTd.className = "column1";
        tr.appendChild(producerTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = product.name;
        nameTd.className = "column1";
        tr.appendChild(nameTd);

        let addressTd = document.createElement("td");
        addressTd.innerHTML = product.address;
        addressTd.className = "column1";
        tr.appendChild(addressTd);

        let priceTd = document.createElement("td");
        priceTd.innerHTML = product.currentPrice + " Ft";
        priceTd.className = "column1";
        tr.appendChild(priceTd);

        let categoryTd = document.createElement("td");
        categoryTd.innerHTML = product.category.name;
        categoryTd.className = "column1";
        tr.appendChild(categoryTd);

        let editButtonTd = document.createElement("td");
        editButtonTd.className = "column1";
        let editButton = document.createElement("img");
        editButton.setAttribute("alt", "edit-icon");
        editButton.setAttribute("src", "img/edit-icon.png");
        editButton.setAttribute("id", "trash-icon");
        editButton.onclick = handleEditButtonOnClick;
        editButtonTd.appendChild(editButton);
        tr.appendChild(editButtonTd);

        let deleteButtonTd = document.createElement("td");
        deleteButtonTd.className = "column1";
        let deleteButton = document.createElement("img");
        deleteButton.setAttribute("src", "img/trash-solid.svg");
        deleteButton.setAttribute("alt", "trash-icon");
        deleteButton.setAttribute("id", "trash-icon")
        deleteButton.onclick = handleDeleteButtonOnClick;
        deleteButtonTd.appendChild(deleteButton);
        tr.appendChild(deleteButtonTd);

        let pictureButtonTd = document.createElement("td");
        pictureButtonTd.className = "column1";
        let pictureButton = document.createElement("img");
        pictureButton.setAttribute("src", "img/upload-icon.png");
        pictureButton.setAttribute("alt", "picture-icon");
        pictureButton.setAttribute("id", "trash-icon")
        pictureButton.onclick = handleUploadButtonOnClick;
        pictureButtonTd.appendChild(pictureButton);
        tr.appendChild(pictureButtonTd);

        tbody.appendChild(tr);
    }
}

let editedProduct = null;

function handleSubmit() {
    let messageSpan = document.getElementById("message-1");
    let idInput = document.getElementById("id-input");
    let producerInput = document.getElementById("producer-input");
    let nameInput = document.getElementById("name-input");
    let addressInput = document.getElementById("address-input");
    let priceInput = document.getElementById("price-input");
    let categorySelect = document.querySelector("#category-select");

    let id = idInput.value;
    let producer = producerInput.value;
    let name = nameInput.value;
    let address = addressInput.value;
    let price = priceInput.value;
    let categoryId = categorySelect.value;
    if (categoryId == 0) {
        categoryId = 1;
    }

    let parsedId = Number(id);
    if (isNaN(parsedId) || !Number.isInteger(parsedId)) {
        messageSpan.innerHTML = "Az id megadása kötelező és csak egész szám lehet.";
        return false;
    }
    if (name.length === 0 || producer.length === 0) {
        messageSpan.innerHTML = "Minden mező kitöltése kötelező!";
        return false;
    }
    let parsedPrice = Number(price);
    if (isNaN(parsedPrice) || !Number.isInteger(parsedPrice) || parsedPrice <= 0 || parsedPrice > 2000000) {
        messageSpan.innerHTML = "Az ár megadása kötelező, csak egész szám lehet és nem haladhatja meg a 2.000.000 Ft-ot.";
        return false;
    }

    let product = {"id": id,
                   "producer": producer,
                   "name": name,
                   "address": address.length > 0 ? address : null,
                   "currentPrice": price,
                   "category": {
                                "id": categoryId
                               }
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
        }).then(response => response.json())
        .then(function(response) {

            if (editedProduct === null) {
                messageSpan.innerHTML = "Hozzáadva.";
            } else {
                messageSpan.innerHTML = "Módosítva."
            }
            updateTable();
            document.getElementById("product-form").reset();
        });

    return false;
}

function handleReset() {
    editedProduct = null;
    /* let idInput = document.getElementById("id-input");
    idInput.value = "";
    let producerInput = document.getElementById("producer-input");
    producerInput.value = "";
    let nameInput = document.getElementById("name-input");
    nameInput.value = "";
    let addressInput = document.getElementById("address-input");
    addressInput.value = "";
    let priceInput = document.getElementById("price-input");
    priceInput.value = "";
    let categorySelect = document.querySelector("#category-select");
    categorySelect.value = 0; */


    let submitButton = document.getElementById("submit-button");
    submitButton.value = "Új termék hozzáadása";
}

function handleEditButtonOnClick() {
    let messageSpan = document.getElementById("message-1");
    messageSpan.innerHTML = "";

    let h2 = document.getElementById("edit-new");
    h2.innerHTML = "Termék szerkesztése";
    location.href = "#openModal";

    let product = this.parentElement.parentElement["raw-data"];
    editedProduct = product;

    let idInput = document.getElementById("id-input");
    idInput.value = product.id;

    let producerInput = document.getElementById("producer-input");
    producerInput.value = product.producer;

    let nameInput = document.getElementById("name-input");
    nameInput.value = product.name;

    let addressInput = document.getElementById("address-input");
    addressInput.value = product.address;

    let priceInput = document.getElementById("price-input");
    priceInput.value = product.currentPrice;

    let categorySelect = document.querySelector("#category-select");
    categorySelect.value = product.category.id;

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
            document.getElementById("product-form").reset();
        });
    }
}

function getCategories() {
    fetch("/api/categories")
          .then(function (response) {
              return response.json();
          })
          .then(function(categories) {
              fillSelectOptions(categories);
          });
}

function fillSelectOptions(categories) {
    let categorySelect = document.querySelector("#category-select");
    for (let i = 0; i < categories.length; i++) {
        let option = document.createElement("option");
        option["raw-data"] = categories[i];
        option.value = categories[i].id;
        option.innerHTML = categories[i].name;
        categorySelect.appendChild(option);
    }
}

function uploadImage() {
  let messageSpan = document.getElementById("message-2");
  let idInput = document.getElementById("id-input-picture");
  let id = idInput.value;
  let formData = new FormData();
  let fileInput = document.getElementById("picture-select");
  formData.append('file', fileInput.files[0]);
  let xhr = new XMLHttpRequest();
  let url = '/api/upload/' + id;
  xhr.open('POST', url);
  xhr.onreadystatechange = function () {
    var DONE = 4;
    var OK = 200;
    if (xhr.readyState === DONE) {
      if (xhr.status === OK) {
        updateTable();
        messageSpan.innerHTML = "Sikeresen módosította a képet!";
      } else {
        messageSpan.innerHTML = "Hiba, próbálja újra!";
      }
    }
  };
  xhr.send(formData);
}

function handleNewProductButton() {
   let messageSpan = document.getElementById("message-1");
   messageSpan.innerHTML = "";

   let h2 = document.getElementById("edit-new");
   h2.innerHTML = "Új termék létrehozása";

   location.href = "#openModal";

   let idInput = document.getElementById("id-input");
   idInput.value = "";

   let producerInput = document.getElementById("producer-input");
   producerInput.value = "";

   let nameInput = document.getElementById("name-input");
   nameInput.value = "";

   let addressInput = document.getElementById("address-input");
   addressInput.value = "";

   let priceInput = document.getElementById("price-input");
   priceInput.value = "";

   let categorySelect = document.querySelector("#category-select");
   categorySelect.value = "";

}

function handleUploadButtonOnClick() {
   let messageSpan = document.getElementById("message-2");
   messageSpan.innerHTML = "";
   location.href = "#openModal-picture";
   let product = this.parentElement.parentElement["raw-data"];

   let idInput = document.getElementById("id-input-picture");
   idInput.value = product.id;

   let nameInput = document.getElementById("name-input-picture");
   nameInput.value = product.name;
  }
