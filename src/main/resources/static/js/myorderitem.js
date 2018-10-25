window.onload = function() {
   updateTable();
}

function updateTable(){
        let orderId = new URL(window.location).searchParams.get("address");
        fetch("/api/myorderitems/"+ orderId)
            .then(function (response) {
                return response.json();
            })
            .then(function(jsonData) {
                console.log(jsonData);
                fillTable(jsonData);
            });
}

function fillTable(jsonData){
    let tbody = document.querySelector("#orderitem-tbody");
    let orderId = new URL(window.location).searchParams.get("address");
    console.log(jsonData);
  }
