# Структура проекта

```plaintext
src
├── main
│   ├── java
│   │   └── org
│   │       └── example
│   │           ├── auth
│   │           │   └── AuthHelper
│   │           ├── config
│   │           │   └── ApiUrl
│   │           ├── dto
│   │           │   ├── MessageDto
│   │           │   ├── SubscriptionDto
│   │           │   ├── TimeDto
│   │           │   └── ValueDto
│   │           └── util
│   │               └── JsonUtil
│   └── resources
├── test
│   └── java
│       └── spec
│           └── RequestSpec
│       └── tests
│           ├── AuthTests
│           ├── RestApiTests
│           └── WebSocketTests
```

# Инструкция по запуску

Для запуска автотеста необходимо:

1. Склонируйте репозиторий на свой компьютер с помощью команды:

 ```
 git clone https://github.com/graw1t/api-tests1.git
```

2. Запустите тесты с помощью команды:

```
mvn clean test
```

# Основные тест-кейсы REST API

1. Успешный логин

Отправить POST /login с валидными данными
Проверить наличие cookie в ответе

2. Невалидный логин

POST /login с неправильным паролем

3. Logout с авторизацией


4. Logout без авторизации


5. Получение времени (/time)

WebSocket API

1. Проверка получения сообщений по подписке

    - подписка / проверка получения сообщений
    - отписка /проверка отсутствия сообщений


2. Негативный тест - проверка подключения

# Баг репорты
## Название: GET /TIME

Описание: Тест предназначен для проверки получения времени с API по эндпоинту /time. В тесте ожидается статус-код 200, однако при выполнении API возвращает статус-код 201.

Шаги воспроизведения:

1. Выполнить запрос GET /time.
2. Проверить статус-код ответа.
3. Проверить содержимое тела ответа.

Фактический результат: Ответ возвращает статус-код 201 вместо ожидаемого 200.

Ожидаемый результат: Статус-код ответа должен быть 200, что соответствует успешному получению данных.

Дополнительные сведения:

1. Возможно, API по каким-то причинам возвращает 201 (Created), что не соответствует документации или ожиданиям теста.

2. Необходимо уточнить у разработчиков API, почему возвращается 201 и скорректировать тест или API в соответствии с требованиями.

## Название: Валидный логин

Описание: Тест предназначен для проверки успешной авторизации пользователя с валидными данными. В тесте ожидается статус-код 200, однако при выполнении API возвращается статус-код 400, что указывает на ошибку или неправильную обработку запроса.

Шаги воспроизведения:

1. Выполнить запрос POST /login
2. Проверить статус-код ответа.
3. Получить токен доступа.
4. Проверить, что токен не равен null.

Фактический результат: Ответ API возвращает статус-код 400 вместо ожидаемого 200.

Ожидаемый результат: Статус-код ответа должен быть 200, что свидетельствует об успешной авторизации.

