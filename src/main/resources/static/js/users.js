window.addEventListener('load', updateTable);

function updateTable() {
    fetch("api/users")
        .then(function (request) {
            return request.json();
        })
        .then(function(jsonData) {
            fillTable(jsonData);
        });
}

function fillTable(users) {
    let tbody = document.getElementById("users-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < users.length; i++) {
        let user  = users[i];
        let tr = document.createElement("tr");
        tr["raw-data"] = user;
        console.log(tr["raw-data"])

        let idTd = document.createElement("td");
        idTd.innerHTML = user.id;
        tr.appendChild(idTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = user.loginName;
        tr.appendChild(nameTd);

        let fullNameTd = document.createElement("td");
        fullNameTd.innerHTML = user.fullName;
        tr.appendChild(fullNameTd);

        let buttonsTd = document.createElement("td");
                let editButton = document.createElement("button");
                let deleteButton = document.createElement("button");
                editButton.setAttribute("class", "btn btn-primary");
                deleteButton.setAttribute("class", "btn btn-danger");
                editButton.innerHTML = "Szerkesztés";
                deleteButton.innerHTML = "Törlés";
                editButton.onclick = editButtonClick;
//                deleteButton.onclick = handleDeleteButtonOnClick;
                buttonsTd.appendChild(editButton);
                buttonsTd.appendChild(deleteButton);
                tr.appendChild(buttonsTd);

        tbody.appendChild(tr);
 }
    }

let editedUser = null;

function editButtonClick() {
    let user = this.parentElement.parentElement["raw-data"];
    editedUser = user

    let idInput = document.getElementById("id-input");
    idInput.value = user.id;
}

function handleReset() {
    editedUser = null;
    let idInput = document.getElementById("id-input");
    idInput.value = "";
    let nameInput = document.getElementById("fullname-input");
    nameInput.value = "";
    let passwordInput = document.getElementById("password-input");
    producerInput.value = "";
}

function modifyUser() {

    let idInput = document.getElementById("id-input");
    let nameInput = document.getElementById("fullname-input");
    let passwordInput = document.getElementById("password-input");

    let id = idInput.value;
    let name = nameInput.value;
    let password = passwordInput.value;

    let parsedId = Number(id);
    if (isNaN(parsedId) || !Number.isInteger(parsedId)) {
        alert("Az id megadása kötelező és csak egész szám lehet.");
        return false;
    }
    if (name.length === 0 || producer.length === 0) {
        alert("Minden mező kitöltése kötelező!");
        return false;
    }
    let parsedPrice = Number(price);
    if (isNaN(parsedPrice) || !Number.isInteger(parsedPrice) || parsedPrice <= 0 || parsedPrice > 2000000) {
        alert("Az ár megadása kötelező, csak egész szám lehet és nem haladhatja meg a 2.000.000 Ft-ot.");
        return false;
    }

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

        if (response.status === 409) {
            alert("A megadott id vagy cím már foglalt. ");
        } else {
                alert("Hozzáadva.");
                updateTable();
                handleReset();
            }
        });
        return false;
}