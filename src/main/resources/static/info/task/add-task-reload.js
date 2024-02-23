console.log("вывод информации в консоль браузера");

// получение формы
const addTaskForm = document.forms[0];
// получение элемента формы с name=title
const titleInput = addTaskForm.elements.title;
// получение элемента формы с name=closeTo
const closeToInput = addTaskForm.elements.closeTo;
// получение элемента формы с name=add-participant
const addParticipantButton = addTaskForm.elements["add-participant"];

// получение элемента в html документе с id=participants
const participantsElement = document.getElementById("participants");

// добавление обработчика события:
// когда произойдёт событие click у кнопки "Добавить участника",
// будет вызвана функция-обработчик
addParticipantButton.addEventListener("click", ()=>{
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


// получение элемента в html документе с id=error
const errorSpan = document.getElementById("error");

// добавление обработчика события:
// когда произойдёт событие submit у формы,
// будет вызвана функция-обработчик

addTaskForm.addEventListener("submit", (event)=>{
    event.preventDefault(); // отменяем отправку данных на сервер,
    // чтобы выполнить предварительную проверку данных, введенных пользователем

    // получаем значение поля ввода названия задачи
    const title = titleInput.value;
    // получаем значение поля ввода даты завершения задачи
    const closeTo = closeToInput.value;

    // если размер title меньше 3,
    // выводим ошибку в html, завершаем выполнение функции обработчика
    if (title.length < 3) {
        errorSpan.innerHTML = "Название задачи: минимум 3 символа";
        return;
    }
    // если дата в прошлом,
    // выводим ошибку в html, завершаем выполнение функции обработчика
    if (new Date() > new Date(closeTo)) {
        errorSpan.innerHTML = "Дата завершения задачи должна быть в будущем";
        return;
    }

    // если форма заполнена корректно,
    // отправляем данные обычным запросом с перезагрузкой страницы
    // методом POST
    addTaskForm.submit();
});

