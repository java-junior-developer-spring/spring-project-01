/*const commentForm = document.forms[0];
const textArea = commentForm.elements.text;
const errorSpan = document.getElementById("error");

commentForm.addEventListener("submit", (event) => {
    event.preventDefault();
    let value = textArea.value;
    if (value.length < 3) {
        errorSpan.innerText = "От 3 символов";
        return;
    }
    const data = {
        text: value,
        added_at: new Date()
    };
    const jsonData = JSON.stringify(data);

    fetch("/comments", {
        method: "POST",
        body: jsonData
    }).then(response => response.json())
        .then(data => {
            // отобразить в html
        });

});*/









