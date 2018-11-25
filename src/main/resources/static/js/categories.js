window.onload = function() {
    updateTable();

    let categoryForm = document.getElementById("category-form");
    categoryForm.onsubmit = handleSubmit;
    categoryForm.onreset = handleReset;
    let newCategoryButton = document.getElementById("new-category-button");
    newCategoryButton.onclick = handleNewCategoryButton;
}

function updateTable() {
    fetch("api/categories")
        .then(function (response) {
            return response.json();
        })
        .then(function(jsonData) {
            fillTable(jsonData);
        });
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
        idTd.className = "column1";
        tr.appendChild(idTd);

        let nameTd = document.createElement("td");
        nameTd.className = "column1";
        nameTd.innerHTML = category.name;
        tr.appendChild(nameTd);

        let positionTd = document.createElement("td");
        positionTd.className = "column1";
        positionTd.innerHTML = category.positionNumber;
        tr.appendChild(positionTd);

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

let editedCategory = null;

function handleEditButtonOnClick() {
    let messageSpan = document.getElementById("message-1");
    messageSpan.innerHTML = "";

    let h2 = document.getElementById("edit-new");
    h2.innerHTML = "Kategória szerkesztése";
    location.href = "#openModal";

    let category = this.parentElement.parentElement["raw-data"];
    editedCategory = category;

    let nameInput = document.getElementById("name-input");
    nameInput.value = category.name;

    let positionInput = document.getElementById("position-input");
    positionInput.value = category.positionNumber;

    let submitButton = document.getElementById("submit-button");
    submitButton.value = "Mentés";
}

function handleReset() {
    editedCategory = null;
    let submitButton = document.getElementById("submit-button");
    submitButton.value = "Kategória létrehozása";
}

function handleSubmit() {
    let nameInput = document.getElementById("name-input");
    let positionInput = document.getElementById("position-input");
    let messageSpan = document.getElementById("message-1");

    let name = nameInput.value;
    let position = positionInput.value;

    let category = {"name": name,
                    "positionNumber": position
                    }

    let url = "api/categories";
    if (editedCategory !== null) {
        category.id = editedCategory.id;
        url += "/update";
    }

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
                },
        body: JSON.stringify(category)
    }).then(function(response) {
        return response.json()
    }).then(function(response) {
        messageSpan.innerHTML = response.message;
        updateTable();
        document.getElementById("category-form").reset();

    });
    return false;
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
            document.getElementById("category-form").reset();
        })
    }
}

function handleNewCategoryButton() {
   let messageSpan = document.getElementById("message-1");
   messageSpan.innerHTML = "";

   let h2 = document.getElementById("edit-new");
   h2.innerHTML = "Új kategória létrehozása";

   location.href = "#openModal";

   let nameInput = document.getElementById("name-input");
   nameInput.value = "";

   let positionInput = document.getElementById("position-input");
   addressInput.value = "";

}
