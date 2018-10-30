window.onload = function() {
    let statusButton = document.querySelector("#bystatus");
    let productButton = document.querySelector("#byproduct");
    statusButton.addEventListener("click",updateTableForFirstReport)
    productButton.addEventListener("click",updateTableForSecondReport)
}

function updateTableForFirstReport() {
    fetch("api/reports/orders")
        .then(function (response) {
            return response.json();
        })
        .then(function (orders) {
            createStatusSelectorForFirstReport(orders);
        });
}

function updateTableForSecondReport(){
        fetch("api/reports/products")
            .then(function (response) {
                return response.json();
            })
            .then(function (orders) {
                console.log(orders);
                createStatusSelectorForSecondReport(orders);
            });
}

function createStatusSelectorForSecondReport(orders){
    let tbody = document.querySelector("#orders-tbody");
    tbody.innerHTML = "";
    let thead = document.querySelector("#orders-thead");
    thead.innerHTML = "";
    let monthList = ["január","február","március","április","május","június","július","augusztus","szeptember",
    "október","november","december"];
    let choiceSelector = document.querySelector("#choice-selector");
    choiceSelector.innerHTML = "";
    createDefaultOption();
    for(i in monthList){
        let option = document.createElement("option");
        option.innerHTML = monthList[i];
        choiceSelector.appendChild(option);
        }
    choiceSelector.addEventListener("change", function () { updateTableByStatusForSecondReport(orders)})
    createHeadForSecondTable()
    }

function createStatusSelectorForFirstReport(orders){
        let tbody = document.querySelector("#orders-tbody");
        tbody.innerHTML = "";
        let thead = document.querySelector("#orders-thead");
        thead.innerHTML = "";
        let choiceSelector = document.querySelector("#choice-selector");
        choiceSelector.innerHTML = "";
        let defaultOption = createDefaultOption();

        choiceSelector.appendChild(defaultOption);

        let activeOption = document.createElement("option");
        activeOption.innerHTML = "ACTIVE";
        choiceSelector.appendChild(activeOption);

        let deliveredOption = document.createElement("option");
        deliveredOption.innerHTML = "DELIVERED";
        choiceSelector.appendChild(deliveredOption);

        let deletedOption = document.createElement("option");
        deletedOption.innerHTML = "DELETED";
        choiceSelector.appendChild(deletedOption);
        choiceSelector.addEventListener("change", function () { updateTableByStatusForFirstReport(orders)})
        createHeadForFirstTable();
}

function updateTableByStatusForSecondReport(orders){
    let statusSelector = document.querySelector("#choice-selector");
    let selected = statusSelector.value;
    fillTableByStatusForSecondReport(orders,selected);
}

function updateTableByStatusForFirstReport(orders){
       let statusSelector = document.querySelector("#choice-selector");
       let selected = statusSelector.value;
       fillTableByStatusForFirstReport(orders,selected);
}

function fillTableByStatusForSecondReport(orders,selected){
    let tbody = document.querySelector("#orders-tbody");
    tbody.innerHTML = "";
    for(i in orders){
        if(orders[i].date== selected){
            let tr = document.createElement("tr");
                    let prductNameTd = document.createElement("td");
                    prductNameTd.innerHTML = orders[i].productName;
                    tr.appendChild(prductNameTd);
                    let productCountTd = document.createElement("td");
                    productCountTd.innerHTML = orders[i].productCount;
                    tr.appendChild(productCountTd);
                    tbody.appendChild(tr);
            }
        }

}

function fillTableByStatusForFirstReport(orders,status){
        console.log(orders);
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

function createHeadForFirstTable(){
            let thead = document.querySelector("#orders-thead");
            let headTr = document.createElement("tr");

            let monthTh = document.createElement("th");
            monthTh.innerHTML = "Hónap";
            headTr.appendChild(monthTh);

            let sumTh = document.createElement("th");
            sumTh.innerHTML = "Összeg";
            headTr.appendChild(sumTh);

            thead.appendChild(headTr);
}

function createHeadForSecondTable(){
        let thead = document.querySelector("#orders-thead");
            let headTr = document.createElement("tr");

            let productNameTh = document.createElement("th");
            productNameTh.innerHTML = "Termék neve";
            headTr.appendChild(productNameTh);

            let prouctCountTh = document.createElement("th");
            prouctCountTh.innerHTML = "Darabszám";
            headTr.appendChild(prouctCountTh);

            thead.appendChild(headTr);
    }

function createDefaultOption(){
            let choiceSelector = document.querySelector("#choice-selector");
            choiceSelector.innerHTML = "";
            let defaultOption = document.createElement("option");
            defaultOption.setAttribute("value","");
            defaultOption.setAttribute("disabled","")
            defaultOption.setAttribute("selected","");
            defaultOption.innerHTML = "SELECT";
            choiceSelector.appendChild(defaultOption);
            return defaultOption;
    }


