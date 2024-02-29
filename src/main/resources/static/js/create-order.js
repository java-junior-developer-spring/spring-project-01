const addToOrderButton = document.getElementsByName("add-to-order");
const removeFromOrderButton = document.getElementsByName("remove-from-order");

let ids = localStorage.getItem("pictures_id");
if (!ids || ids.length < 3) ids = [];
else ids = JSON.parse(ids);

addToOrderButton.forEach(button => {
    const pictureId = button.dataset.pictureId;
    if (ids.length === 0 || ids.indexOf(pictureId) < 0) {
        button.style.display = "block";
        button.nextElementSibling.style.display = "none";
        return;
    }
    button.style.display = "none";
    button.nextElementSibling.style.display = "block";
});


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

removeFromOrderButton.forEach(button => {
    button.addEventListener("click", ()=>{
        const pictureId = button.dataset.pictureId;
        if (ids.indexOf(pictureId) >= 0) {
            ids.splice(ids.indexOf(pictureId), 1);
            localStorage.setItem("pictures_id", JSON.stringify(ids));
        }
        button.style.display = "none";
        button.previousElementSibling.style.display = "block";
    });
});



