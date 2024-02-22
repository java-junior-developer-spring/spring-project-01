const commentForm = document.forms[0];
const textArea = commentForm.elements.text;
const errorSpan = document.getElementById("error");

commentForm.addEventListener("submit", (event)=>{
    event.preventDefault();
    let value = textArea.value;
    if (value.length < 3) {
        errorSpan.innerText = "От 3 символов";
        return;
    }
    // @RequestBody
    // POST - fetch("/comments", {
    //  method: "POST",
    //  body: json
    // });
    // @RequestParam int id
    // GET - fetch("/comments?id=2345");
    // POST - fetch("/comments", {
    //  method: "POST",
    //  body: "id=" + json
    // });
    // @ModelAttribute
    // POST - fetch("/comments", {
    //  method: "POST",
    //  body: FormData [key: данные]
    // });
    // @PathVariable int id - fetch("/comments/2345");

    // @RequestPart

    // аякс запрос
    // @RequestBody Comment comment
    const data = {
        text: value,
        added_at: new Date()
    };
    const jsonData = JSON.stringify(data);
    fetch("/comments", {
        method: "POST",
        body: jsonData
    }).then(response => response.json()) // text, blob
        .then(data => {
            // {id: 4, color: "red"}
            // [{id: 5, color: "red"}, {id: 8, color: "green"}]
            // data
        });

});

let variable = 7;
variable = true;
variable = "false";
variable = -7.8;
variable = null;
variable = undefined;

const arr = [];
arr[0] = 100; // 0
arr[20] = -233; // 20

const object = {
    id: 1,
    title: "картина 1",
    price: 2300
};

const arr01 = [{id: 4, title: "1"}, {}];









