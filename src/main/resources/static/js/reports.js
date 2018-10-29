window.onload = function() {
   updateTable();
}

function updateTableByStatus(orders){
       let statusSelector = document.querySelector("#status-select");
       switch(statusSelector.value) {
           case "ACTIVE":
               fillTableForActive(orders);
               console.log(orders)
               break;
           case "DELIVERED":
               fillTableForDelivered(orders);
               console.log(orders)
               break;
           case "DELETED":
               fillTableForDeleted(orders);
               console.log(orders)
       }
    }

function updateTable() {
    fetch("api/reports/orders")
        .then(function (response) {
            return response.json();
        })
        .then(function (orders) {
            console.log(orders)
            let statusSelector = document.querySelector("#status-select");
            statusSelector.addEventListener("change", function () { updateTableByStatus(orders)})
        });
}

function fillTable(orders){
    let tbody = document.querySelector("#orders-tbody");
        for(i in orders){
            let tr = document.createElement("tr");
            tbody.appendChild(tr);
            }
}

function fillTableForActive(orders){
    let tbody = document.querySelector("#orders-tbody");
    tbody.innerHTML = "";
            for(i in orders){
                if(orders[i].status == "ACTIVE"){
                    let tr = document.createElement("tr");
                    let monthTd = document.createElement("td");
                    monthTd.innerHTML = orders[i].date;
                    tr.appendChild(monthTd);
                    let sumTd = document.createElement("td");
                    sumTd.innerHTML = orders[i].totalPrice;
                    tr.appendChild(sumTd);
                    tbody.appendChild(tr);
                    }
            }
}

function fillTableForDelivered(orders){
    let tbody = document.querySelector("#orders-tbody");
    tbody.innerHTML = "";
            for(i in orders){
                if(orders[i].status == "DELIVERED"){
                    let tr = document.createElement("tr");
                    let monthTd = document.createElement("td");
                    monthTd.innerHTML = orders[i].date;
                    tr.appendChild(monthTd);
                    let sumTd = document.createElement("td");
                    sumTd.innerHTML = orders[i].totalPrice;
                    tr.appendChild(sumTd);
                    tbody.appendChild(tr);
                    }
            }
}

function fillTableForDeleted(orders){
    let tbody = document.querySelector("#orders-tbody");
    tbody.innerHTML = "";
            for(i in orders){
                if(orders[i].status == "DELETED"){
                    let tr = document.createElement("tr");
                    let monthTd = document.createElement("td");
                    monthTd.innerHTML = orders[i].date;
                    tr.appendChild(monthTd);
                    let sumTd = document.createElement("td");
                    sumTd.innerHTML = orders[i].totalPrice;
                    tr.appendChild(sumTd);
                    tbody.appendChild(tr);
                    }
            }
}