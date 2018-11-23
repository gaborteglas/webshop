window.onload = function () {
    getUserDatas();
};

function getUserDatas() {
    fetch("api/user")
        .then(function (response) {
            return response.json();
        })
        .then(function (userData) {
            let userForm = document.querySelector("#user-form");
            userForm.onsubmit = function () { updateDatas(userData); return false;}
        });

}

function updateDatas(userData){
    let nameInput = document.querySelector("#fullname-input");
    let passwordInput = document.querySelector("#password-input");
    let secondPasswordInput = document.querySelector("#password-input-again");
    name = nameInput.value;
    password = passwordInput.value;
    secondPassword = secondPasswordInput.value;
    if(password !== secondPassword){
        secondPasswordInput.value = "";
        passwordInput.value = "";
        alert("A két jelszó nem egyezik meg.");
        return;
    }

    let user = {"id": userData.id,
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
            alert(response.message);
            setUserName();
            document.getElementById("user-form").reset();
        });

}
