# LFF Test Data Files

Эта папка содержит файлы для тестирования функциональности Load From File (LFF).

## 📋 Структура

```
src/test/resources/lff/
├── README.md                    # Этот файл
├── signup-users.xlsx            # Файл для регистрации пользователей
├── update-users.xlsx            # Файл для обновления пользователей
└── delete-users.xlsx            # Файл для удаления пользователей
```

## 📝 Формат файлов

### signup-users.xlsx (пример структуры)

| firstName | lastName | username | email | mobile | userType |
|-----------|----------|----------|-------|--------|----------|
| TestFN1 | TestLN1 | testun1 | test1@test.com | 972581234567 | ProUser |
| TestFN2 | TestLN2 | testun2 | test2@test.com | 972581234568 | ProUser |

### update-users.xlsx (пример структуры)

| username | firstName | lastName | email | mobile |
|----------|-----------|----------|-------|--------|
| existinguser1 | NewFirstName | NewLastName | newemail@test.com | 972589999999 |

### delete-users.xlsx (пример структуры)

| username |
|----------|
| userToDelete1 |
| userToDelete2 |

## 🔧 Использование в тестах

### Способ 1: Относительный путь (рекомендуется)

```java
String filePath = "src/test/resources/lff/signup-users.xlsx";
signUpPage.uploadFile(filePath);
```

### Способ 2: Через ClassLoader (работает в JAR)

```java
String filePath = getClass().getClassLoader()
    .getResource("lff/signup-users.xlsx")
    .getPath();
signUpPage.uploadFile(filePath);
```

### Способ 3: Абсолютный путь от корня проекта

```java
String filePath = System.getProperty("user.dir") + "/src/test/resources/lff/signup-users.xlsx";
signUpPage.uploadFile(filePath);
```

### Способ 4: Через Path API (современный способ)

```java
import java.nio.file.Path;
import java.nio.file.Paths;

Path filePath = Paths.get("src", "test", "resources", "lff", "signup-users.xlsx");
signUpPage.uploadFile(filePath.toString());
```

## 🐳 Работа с Docker

В Docker контейнере файлы автоматически копируются при сборке образа.
Используйте относительный путь - он работает и локально, и в Docker:

```java
String filePath = "src/test/resources/lff/signup-users.xlsx";
```

## 📌 Важные замечания

1. **Формат файлов**: Поддерживаются .xlsx, .xls, .csv
2. **Кодировка**: Используйте UTF-8 для CSV файлов
3. **Размер**: Рекомендуется до 1000 строк на файл
4. **Именование**: Используйте понятные имена файлов
5. **Git**: Файлы автоматически включены в репозиторий

## 🔍 Проверка существования файла

```java
import java.nio.file.Files;
import java.nio.file.Paths;

String filePath = "src/test/resources/lff/signup-users.xlsx";
if (Files.exists(Paths.get(filePath))) {
    log.info("File exists: " + filePath);
} else {
    log.error("File not found: " + filePath);
}
```

## 📦 Пример использования в тесте

```java
@Test
@Order(1)
void signUpFromFile() {
    log.info("Test: Sign Up From File");
    
    // Путь к тестовому файлу
    String filePath = "src/test/resources/lff/signup-users.xlsx";
    
    // Проверка существования файла
    if (!Files.exists(Paths.get(filePath))) {
        throw new FileNotFoundException("Test data file not found: " + filePath);
    }
    
    // Использование файла
    SignUpFromFilePage signUpPage = menuPage.clickSignUpFromFile();
    signUpPage.uploadFile(filePath)
              .clickSubmit()
              .waitForSuccessMessage();
              
    // Проверки
    Assertions.assertTrue(signUpPage.isSuccessMessageVisible());
}
```
