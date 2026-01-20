# TeleadminLFFSanityTest

## 📋 Описание

Тест-сьют для проверки функциональности Load From File (LFF) в Teleadmin портале.

## 🎯 Покрытие

Этот тест-сьют проверяет следующие функции:

### 1. Sign Up From File
- Регистрация пользователей из файла
- Валидация формата файла
- Проверка успешности регистрации

### 2. Update Users From File
- Обновление данных пользователей из файла
- Валидация изменений
- Проверка корректности обновления

### 3. Delete Users From File
- Удаление пользователей из файла
- Подтверждение удаления
- Проверка что пользователи удалены

## 🚀 Запуск

### Локально
```bash
mvn test -Dtest=TeleadminLFFSanityTest -DisLocalRun=true
```

### В Docker (отдельный контейнер)
```bash
docker compose up teleadmin-lff-tests
```

### В Docker (все контейнеры параллельно)
```bash
docker compose up --build
```

## 📊 Allure Report

После запуска тестов, результаты сохраняются в:
- **Локально**: `target/allure-results/`
- **Docker**: `target/allure-results-teleadmin-lff/`

Просмотр отчета:
```bash
mvn allure:serve
```

## 🔧 Структура теста

```java
@BeforeAll
- Логин в Teleadmin
- Инициализация Page Objects
- Подготовка тестовых данных

@Test (Order 1) - signUpFromFile()
- Тест регистрации пользователей из файла

@Test (Order 2) - updateUsersFromFile()
- Тест обновления пользователей из файла

@Test (Order 3) - deleteUsersFromFile()
- Тест удаления пользователей из файла

@AfterAll
- Cleanup и закрытие сессии
```

## 📝 Добавление новых тестов

1. Добавьте новый тест-метод в `TeleadminLFFSanityTest.java`
2. Используйте `@Test` и `@Order` аннотации
3. Добавьте Allure аннотации: `@Story`, `@Severity`, `@Description`
4. Реализуйте логику теста используя Page Objects

Пример:
```java
@Test
@Order(4)
@Story("New LFF Feature")
@Severity(SeverityLevel.CRITICAL)
@Description("Test new LFF feature")
void newLFFTest() {
    log.info("Test: New LFF Feature");
    
    // Your test implementation
    
    // Assertions
    Assertions.assertEquals(expected, actual, "Error message");
}
```

## 🐳 Docker Configuration

Контейнер `teleadmin-lff-tests` настроен в `docker-compose.yml`:

```yaml
teleadmin-lff-tests:
  container_name: teleadmin-lff-tests
  resources:
    limits:
      memory: 2g
      cpus: '2'
  volumes:
    - ./target/allure-results-teleadmin-lff:/app/target/allure-results
```

## 📌 TODO

- [ ] Реализовать тест Sign Up From File
- [ ] Реализовать тест Update Users From File  
- [ ] Реализовать тест Delete Users From File
- [ ] Добавить Page Objects для LFF страниц
- [ ] Создать тестовые CSV файлы
- [ ] Добавить валидацию ошибок

## 🔗 Связанные файлы

- `src/test/java/com/websanity/tests/TeleadminLFFSanityTest.java` - Тест класс
- `src/main/java/com/websanity/teleadminPages/MenuPage.java` - Page Object с LFF кнопками
- `docker-compose.yml` - Конфигурация Docker контейнера
- `src/test/java/com/websanity/tests/TestsRunner.java` - Runner для параллельного запуска

## ⚠️ Важные замечания

- Тесты используют общую сессию браузера (установленную в `@BeforeAll`)
- Каждый тест должен быть идемпотентным
- Используйте уникальные значения для тестовых данных
- Очищайте тестовые данные после выполнения тестов
