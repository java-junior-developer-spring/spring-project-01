// при загрузке скрипта отправляем запрос на получение списка задач
// когда ответ придет, отрисовываем их в html в теге с id tasks

const tasksSection = document.getElementById("tasks");

// отправляем get запрос
fetch("http://localhost:8080/front/tasks/ajax/all")
    .then(response => response.json())
    .then(data => {
        console.log(data)
        console.log(data.length)
        if (data.length === 0) {
            tasksSection.innerHTML = "<p>У Вас нет задач</p>";
            return;
        }
        tasksSection.innerHTML = "<p>Ваши задачи</p>";
        data.forEach(task => {
            console.log(task)
            // для каждой задачи из списка создаем элемент
            const taskDiv = document.createElement("div");
            // наполняем элемент
            taskDiv.innerHTML = `<p>${task.title}</p>
                                 <p>Завершить к ${task.closeTo}</p>
                                 <p>Статус ${task.isClosed ? "Закрыта" : "Не завершена"}</p>   `;

            // если есть участники, отображаем их аналогичным способом
            if (task.participants !== undefined && task.participants !== null && task.participants.length > 0) {
                const participantsDiv = document.createElement("div");
                participantsDiv.innerHTML = "<p>Участники: </p>";
                task.participants.split(";").forEach(participant => {
                    let participantsP = document.createElement("p");
                    participantsP.innerText = participant;
                    participantsDiv.appendChild(participantsP);
                });
                taskDiv.appendChild(participantsDiv);
            }

            // созданные элементы добавляем в html
            tasksSection.appendChild(taskDiv);
        });
    })
    .catch(resultError => {
        tasksSection.innerHTML = "<p>Задачи не удалось отобразить</p>";
    });