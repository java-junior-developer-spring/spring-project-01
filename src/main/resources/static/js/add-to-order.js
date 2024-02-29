const addToOrderButton = document.getElementsByName("add-to-order");

let ids = localStorage.getItem("pictures_id");
if (!ids || ids.length < 3) ids = [];
else ids = JSON.parse(ids);

addToOrderButton.forEach(button => {
    button.addEventListener("click", ()=>{
        const pictureId = button.dataset.pictureId;
        if (ids.indexOf(pictureId) < 0) {
            ids.push(pictureId);
            localStorage.setItem("pictures_id", JSON.stringify(ids));
        }
        button.style.display = "none";
        button.nextElementSibling.style.display = "block";
    });
});

