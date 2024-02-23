console.log("вывод информации в консоль браузера");

// получение формы
const addTaskForm = document.forms[0];
// получение элемента формы с name=title
const titleInput = addTaskForm.elements.title;
// получение элемента формы с name=closeTo
const closeToInput = addTaskForm.elements.closeTo;
// получение элемента формы с name=add-participant
const addParticipantButton = addTaskForm.elements["add-participant"];
// получение элемента формы с name=doc
const docInput = addTaskForm.elements.doc;

// получение элемента в html документе с id=participants
const participantsElement = document.getElementById("participants");

// добавление обработчика события:
// когда произойдёт событие click у кнопки "Добавить участника",
// будет вызвана функция-обработчик
addParticipantButton.addEventListener("click", () => {
    // создаем элемент div
    const participantDiv = document.createElement("div");
    // наполняем его необходимыми html тегами
    // name="participants[]" знак массива указывает на то,
    // что данные инпутов с одинаковыми name (name="participants[]" в данном случае)
    // будут отправлены на сервер массивом
    participantDiv.innerHTML = `<label>Участник: <input type="text" name="participants[]"></label>`;
    // добавляем созданный элемент в существующий элемент html
    participantsElement.appendChild(participantDiv);
});


// получение элемента в html документе с id=result-info
const resultSpan = document.getElementById("result-info");

// добавление обработчика события:
// когда произойдёт событие submit у формы,
// будет вызвана функция-обработчик

addTaskForm.addEventListener("submit", (event) => {
    event.preventDefault(); // отменяем отправку данных на сервер,
    // чтобы выполнить предварительную проверку данных, введенных пользователем

    // получаем значение поля ввода названия задачи
    const title = titleInput.value;
    // получаем значение поля ввода даты завершения задачи
    const closeTo = closeToInput.value;

    // если размер title меньше 3,
    // выводим ошибку в html, завершаем выполнение функции обработчика
    if (title.length < 3) {
        resultSpan.innerHTML = "Название задачи: минимум 3 символа";
        return;
    }
    // если дата в прошлом,
    // выводим ошибку в html, завершаем выполнение функции обработчика
    if (new Date() > new Date(closeTo)) {
        resultSpan.innerHTML = "Дата завершения задачи должна быть в будущем";
        return;
    }

    // получаем существующие инпуты добавления участников
    const participantsInputs = addTaskForm.elements["participants[]"];
    let participants;
    // если элементы существуют и их можно перебрать
    if (participantsInputs !== undefined && typeof participantsInputs[Symbol.iterator] === 'function') {
        // получаем строку из их непустых значений
        participants = [...participantsInputs]
            .filter(input => input.value.length > 1)
            .reduce((joinStr, input) => {
                if (joinStr !== "") return joinStr + ";" + input.value;
                else return joinStr + input.value;
            });
    // если элементы существуют, но инпут один
    } else if (participantsInputs !== undefined){
        // получаем его значение
        participants = participantsInputs.value;
    }

    console.log(participants)

    // если форма заполнена корректно,
    // собираем данные задачи в объект
    const task = {
        title: title,
        closeTo: closeTo,
        participants: participants
    };
    // собираем данные о задаче в json строку
    const taskJson = JSON.stringify(task);


    // получаем файл для отправки
    const fileDoc = docInput.files[0];

    // собираем объект formdata для удобной отправки json строки и файла на сервер
    const taskFormData = new FormData();
    taskFormData.append("task", taskJson);
    if (fileDoc !== undefined) taskFormData.append("doc", fileDoc);

    // отправляем данные на аякс запросом без перезагрузки страницы
    // ждем ответа сервера
    fetch("http://localhost:8080/front/addtask/ajax", {
        method: "POST", // http метод
        headers: { // заголовки
            contentType: "multipart/form-data"
        },
        body: taskFormData // тело сообщения
    })
        .then(response => response.json())
        .then(data => {
            if (data.taskId !== undefined) {
                resultSpan.innerHTML = `Задача добавлена. Номер задачи ${data.taskId}`;
            } else {
                resultSpan.innerHTML = `Задачу не удалось сформировать`;
            }
        })
        .catch(errorResponse => {
            console.log(errorResponse);
            resultSpan.innerHTML = `Задачу не удалось сформировать`;
        });
});