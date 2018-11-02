window.addEventListener('load', updateTable);

function updateTable() {
    fetch("api/categories")
        .then(function (response) {
            return response.json();
        })
        .then(function(jsonData) {
            fillTable(jsonData);
        });
        handleModifyButton();
        handleCreateButton();
        let idInput = document.getElementById("id-input");
        idInput.setAttribute("style","display:none");
        idInput.value = 0;
}

function fillTable(categories) {
    let tbody = document.getElementById("categories-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < categories.length; i++) {
        let category  = categories[i];
        let tr = document.createElement("tr");
        tr["raw-data"] = category;

        let idTd = document.createElement("td");
        idTd.innerHTML = category.id;
        tr.appendChild(idTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = category.name;
        tr.appendChild(nameTd);

        let positionTd = document.createElement("td");
        positionTd.innerHTML = category.positionNumber;
        tr.appendChild(positionTd);

        let buttonsTd = document.createElement("td");
                let editButton = document.createElement("button");
                let deleteButton = document.createElement("button");
                editButton.setAttribute("class", "btn btn-primary");
                deleteButton.setAttribute("class", "btn btn-danger");
                editButton.innerHTML = "Szerkesztés";
                deleteButton.innerHTML = "Törlés";
                editButton.onclick = editButtonClick;
                deleteButton.onclick = handleDeleteButtonOnClick;
                buttonsTd.appendChild(editButton);
                buttonsTd.appendChild(deleteButton);
                tr.appendChild(buttonsTd);

        tbody.appendChild(tr);
 }
    }

function editButtonClick() {
    let category = this.parentElement.parentElement["raw-data"];

    let idInput = document.getElementById("id-input");
    idInput.value = 0;
    idInput.value = category.id;
    let nameInput = document.getElementById("name-input");
    nameInput.value = category.name;
    let positionInput = document.getElementById("position-input");
    positionInput.value = category.positionNumber;
}

function handleReset() {
    let idInput = document.getElementById("id-input");
    idInput.value = "";
    let nameInput = document.getElementById("name-input");
    nameInput.value = "";
    let positionInput = document.getElementById("position-input");
    positionInput.value = "";
}

function modifyCategory() {
    let idInput = document.getElementById("id-input");
        let nameInput = document.getElementById("name-input");
        let positionInput = document.getElementById("position-input");

    let id = idInput.value;
    let name = nameInput.value;
    let position = positionInput.value;

    let category = {"id": id,
               };

    if (name.length != 0) {
        category.name = name;
    }

    category.positionNumber = position;

    fetch("api/categories/" + id, {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
                },
        body: JSON.stringify(category)
    }).then(function(response) {
        return response.json()
    }).then(function(response) {
        alert(response.message);
        updateTable();
        handleReset();
        idInput.value = 0;
    });
        return false;
}

function createCategory() {
    let idInput = document.getElementById("id-input");
        let nameInput = document.getElementById("name-input");
        let positionInput = document.getElementById("position-input");

    let id = idInput.value;
    let name = nameInput.value;
    let position = positionInput.value;

    let category = {"id": id,
               };

    if (name.length != 0) {
        category.name = name;
    }

    category.positionNumber = position;

    fetch("api/categories/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
                },
        body: JSON.stringify(category)
    }).then(function(response) {
        return response.json()
    }).then(function(response) {
        alert(response.message);
        updateTable();
        idInput.value = 0;
    });
}

function handleDeleteButtonOnClick() {
    var result = confirm("Biztosan törli a kijelölt kategóriát?");
    if (result) {
        let category = this.parentElement.parentElement["raw-data"];

        fetch("api/categories/" + category.id, {
            method: "DELETE",
        })
        .then(function(response) {
            updateTable();
        })
    }
}

function handleModifyButton() {
    let modifyButton = document.getElementById("submit-button");
    modifyButton.onclick = modifyCategory;
}

function handleCreateButton() {
    let createButton = document.getElementById("create-button");
    createButton.onclick = createCategory;
}