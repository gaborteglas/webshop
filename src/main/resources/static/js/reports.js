window.onload = function() {
   updateTable();

};

function updateTable() {
    fetch("api/reports/orders")
        .then(function (response) {
            return response.json();
        })
        .then(function (orders) {
            fillTable(orders);
            console.log(orders);
        });
}

function fillTable(orders){
    let tbody = document.querySelector("#orders-tbody");
    for(i in orders){
        let tr = document.createElement("tr");


        let blankTd = document.createElement("td");
        blankTd.innerHTML = "";
        tr.appendChild(blankTd);




        tbody.appendChild(tr);
        }
    }