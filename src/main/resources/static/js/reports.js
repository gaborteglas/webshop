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
    let monthList = ["JANUÁR","FEBRUÁR","MÁRCIUS","ÁPRILIS","MÁJUS","JÚNIUS","JÚLIUS","AUGUSZTUS","SZEPTERMBER",
    "OKTÓBER","NOVEMBER","DECEMBER"];
    let choiceSelector = document.querySelector("#choice-selector");
    choiceSelector.innerHTML = "";
    createDefaultOption();
    for(i in monthList){
        console.log(monthList[i]);
        let option = document.createElement("option");
        option.innerHTML = monthList[i];
        choiceSelector.appendChild(option);
        }
    choiceSelector.addEventListener("change", function () { updateTableByStatusForSecondReport(orders)})
    createHeadForFirstTable()
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
        createHeadForFirstTable()
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
    }



function fillTableByStatusForFirstReport(orders,status){
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


