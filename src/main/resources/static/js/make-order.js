const makeOrderButton = document.getElementById("make-order");

let ids = localStorage.getItem("pictures_id");
if (!ids || ids.length < 3) makeOrderButton.style.display = "none";
else makeOrderButton.style.display = "block";

makeOrderButton.addEventListener("click", ()=>{
    ids = JSON.parse(ids).join(","); // [3, 6, 8, 10] order/3,6,8,10
    fetch("http://localhost:8080/order/" + ids)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            makeOrderButton.style.display = "none";
            localStorage.setItem("pictures_id", JSON.stringify([]));
        });

});