window.onload = function() {
   updateTable();
}

function updateTableByStatus(orders){
       let statusSelector = document.querySelector("#status-select");
       switch(statusSelector.value) {
           case "ACTIVE":
               fillTableByStatus(orders,"ACTIVE");
               console.log(orders)
               break;
           case "DELIVERED":
               fillTableByStatus(orders,"DELIVERED");
               console.log(orders)
               break;
           case "DELETED":
               fillTableByStatus(orders,"DELETED");
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

function fillTableByStatus(orders,status){
        let tbody = document.querySelector("#orders-tbody");
        tbody.innerHTML = "";
                for(i in orders){
                    if(orders[i].status == status){
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