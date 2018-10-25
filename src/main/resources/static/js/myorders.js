window.onload = function() {
    updateTable();
    }

function updateTable() {
    let sajt = document.querySelector("#id-hidden-input");
    let userId = document.querySelector("#id-hidden-input").innerText;
    let field = document.querySelector("#id-hidden-input");
    console.log(sajt);
    console.log(userId);

    fetch("api/myorders/" + userId)
        .then(function (response) {
            return response.json();
        })
        .then(function(jsonData) {
            console.log(jsonData);
        });
}