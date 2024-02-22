console.log("консольный вывод");

// let, const
// var
const example = [
    {
        id: 1,
        text: "ex #1",
        color: "red"
    },
    {
        id: 2,
        text: "ex #2",
        color: "green"
    },
    {
        id: 3,
        text: "ex #3",
        color: "red"
    },
    {
        id: 4,
        text: "ex #4",
        color: "blue"
    }
];
// {} for in
// [] for of - .forEach
example.forEach(elem => {

});

const pictures = document.getElementById("pictures");

for (let elem of example){
    const div = document.createElement("div");
    div.innerHTML = `
                          <p>${elem.text}</p>
                          <p id="${elem.id}">${elem.id}</p>
                       `;
    pictures.appendChild(div);
}

const elements = document.getElementById("elements");

document.getElementById("add-button")
    .addEventListener("click", () => {
        const data = example[0];

        const div = document.createElement("div");
        div.innerHTML = `
                           <p>${data.text}</p>
                           <p id="${data.id}">${data.id}</p>
                       `;
        elements.appendChild(div);
    });


