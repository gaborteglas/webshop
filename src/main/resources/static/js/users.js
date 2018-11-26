window.addEventListener('load', updateTable);

function updateTable() {
    fetch("api/users")
        .then(function (response) {
            return response.json();
        })
        .then(function(jsonData) {
            fillTable(jsonData);
            let userForm = document.getElementById("user-form");
            userForm.onsubmit = modifyUser;
            let newUserButton = document.getElementById("new-user-button");
            newUserButton.onclick = handleNewUserButton;
        });
}

function fillTable(users) {
    let tbody = document.getElementById("users-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < users.length; i++) {
        let user  = users[i];
        let tr = document.createElement("tr");
        tr["raw-data"] = user;

        let idTd = document.createElement("td");
        idTd.innerHTML = user.id;
        idTd.className = "column1";
        tr.appendChild(idTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = user.loginName;
        nameTd.className = "column1";
        tr.appendChild(nameTd);

        let fullNameTd = document.createElement("td");
        fullNameTd.innerHTML = user.fullName;
        fullNameTd.className = "column1";
        tr.appendChild(fullNameTd);

        let editButtonTd = document.createElement("td");
        editButtonTd.className = "column1";
        let editButton = document.createElement("img");
        editButton.setAttribute("alt", "edit-icon");
        editButton.setAttribute("src", "img/edit-icon.svg");
        editButton.setAttribute("id", "edit-icon");
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

        tbody.appendChild(tr);
 }
    }

let editedUser = null;

function handleEditButtonOnClick() {
    let messageSpan = document.getElementById("message-1");
    messageSpan.innerHTML = "";
    location.href = "#openModal";

    let user = this.parentElement.parentElement["raw-data"];
    editedUser = user;

    let idInput = document.getElementById("id-input");
    idInput.value = user.id;
    idInput.readOnly = true;

    let nameInput = document.getElementById("fullname-input");
    nameInput.value= user.fullName;

    let submitButton = document.getElementById("submit-button");
    submitButton.value = "Mentés";
}

function handleReset() {
    editedUser = null;
    let idInput = document.getElementById("id-input");
    idInput.value = "";
    let nameInput = document.getElementById("fullname-input");
    nameInput.value = "";
    let passwordInput = document.getElementById("password-input");
    passwordInput.value = "";
}

function modifyUser() {
    if (editedUser == null) {
        return;
    }
    let messageSpan = document.getElementById("message-1");
    let idInput = document.getElementById("id-input");
    let nameInput = document.getElementById("fullname-input");
    let passwordInput = document.getElementById("password-input");

    let id = idInput.value;
    let name = nameInput.value;
    let password = passwordInput.value;

    let user = {"id": id,
               };

    if (name.length != 0) {
        user.fullName = name;
    }
    if (password.length != 0) {
        user.password = password;
    }

    fetch("api/users/update", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
                },
        body: JSON.stringify(user)
    }).then(function(response) {
        return response.json()
    }).then(function(response) {
        messageSpan.innerHTML = response.message;
        updateTable();
        handleReset();
    });
        return false;
}

function handleDeleteButtonOnClick() {
    var result = confirm("Biztosan törli a kijelölt felhasználót?");
    if (result) {
        let user = this.parentElement.parentElement["raw-data"];

        fetch("api/users/" + user.id, {
            method: "DELETE",
        })
        .then(function(response) {
            updateTable();
        });
    }
}

function handleNewUserButton() {
    let submitButton = document.getElementById("submit-button");
    submitButton.value = "Új felhasználó hozzáadása";

    let idInput = document.getElementById("id-input");
    idInput.removeAttribute('readonly');
    idInput.value = "";

    let nameInput = document.getElementById("fullname-input");
    nameInput.value = "";

    let passwordInput = document.getElementById("password-input");
    passwordInput.value = "";

    location.href = "#openModal";
}