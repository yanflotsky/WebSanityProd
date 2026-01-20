# ExcelFileGenerator - Генератор Excel файлов для LFF тестов

## 📋 Описание

Utility класс для генерации Excel (.xlsx) файлов с данными пользователей для тестов Load From File (LFF).

## 🎯 Назначение

Автоматическое создание Excel файлов с правильной структурой колонок для импорта пользователей через LFF функциональность.

## 📝 Структура Excel файла

### Header (1-я строка):
```
Mobile Phone | Email Address | First Name | Last Name | Login | Password | 
Product | Assign To Plan | Send welcome message to Mobile | 
Send welcome message to Email | Language | Billing Type | Billing Reoccurring | 
Company | Time Zone | Country | App Text Support | Voice Call Support | 
Enterprise Setting | Enterprise Number | WhatsApp API | 
Add To Global Address Book | Unique Customer Code | UDID | 
Forward Inbox To | Send Outgoing Messages Via Provider
```

### Data rows:
Каждая строка содержит данные одного пользователя из объекта `UserParams`.

## 💡 Использование

### Пример 1: Создать файл с несколькими пользователями

```java
import com.websanity.utils.ExcelFileGenerator;
import com.websanity.models.UserParams;
import com.websanity.enums.*;

import java.util.ArrayList;
import java.util.List;

@Test
void generateLFFFile() {
   // Создать список пользователей
   List<UserParams> users = new ArrayList<>();

   for (int i = 1; i <= 5; i++) {
      UserParams user = UserParams.builder()
              .firstName("TestFN" + i)
              .lastName("TestLN" + i)
              .username("testuser" + i)
              .password("Aa123123123")
              .email("test" + i + "@example.com")
              .mobileCountryCode(Country.ISRAEL)
              .mobileArea("58")
              .mobilePhone("123456" + i)
              .userType(UserTypes.PROUSER)
              .language(Language.ENGLISH_US)
              .timeZone(TimeZone.EST)
              .country(Country.UNITED_STATES)
              .company("Test Company")
              .build();

      users.add(user);
   }

   // Сгенерировать Excel файл
   String filename = ExcelFileGenerator.generateExcelFileForLFF(users);
   log.info("Generated file: {}", filename);
   // Output: websandff20260120_143022.xlsx

   // Использовать файл в тесте
   lffPage.uploadFile(ExcelFileGenerator.getFullPath(filename));
}
```

### Пример 2: Создать файл для delete users

```java
@Test
void generateDeleteUsersFile() {
    List<UserParams> users = new ArrayList<>();
    
    // Добавить пользователей которых нужно удалить
    List<String> usernamesForDelete = TestUsers.LFFUsers.usernamesForLFFList();
    
    for (String username : usernamesForDelete) {
        UserParams user = UserParams.builder()
                .username(username)
                .build();
        users.add(user);
    }
    
    // Сгенерировать файл
    String filename = ExcelFileGenerator.generateExcelFile(users);
    
    // Удалить пользователей
    dffPage.uploadFile(ExcelFileGenerator.getFullPath(filename))
           .clickDeleteBtn();
}
```

### Пример 3: Использовать в тесте Sign Up From File

```java
@Test
@Order(1)
void signUpFromFile() {
    log.info("Test: Sign Up From File");
    
    // Создать тестовых пользователей
    List<UserParams> users = createTestUsers(5);
    
    // Сгенерировать Excel файл
    String filename = ExcelFileGenerator.generateExcelFile(users);
    String filePath = ExcelFileGenerator.getFullPath(filename);
    
    // Загрузить файл и импортировать пользователей
    lffPage = menuPage.clickSignUpFromFileButton()
            .selectUserType(UserTypes.PROUSER)
            .selectExclusiveAdmin(manager.getUserID())
            .uploadFile(filePath)
            .clickAddBtn()
            .waitForImportResultText();
    
    // Проверить результат
    Assertions.assertTrue(
        lffPage.getImportResultText().contains("users were imported")
    );
}

// Helper method
private List<UserParams> createTestUsers(int count) {
    List<UserParams> users = new ArrayList<>();
    String uniqueNum = String.format("%07d", System.currentTimeMillis() % 10000000);
    
    for (int i = 1; i <= count; i++) {
        UserParams user = UserParams.builder()
                .firstName("TestFN" + uniqueNum + i)
                .lastName("TestLN" + uniqueNum + i)
                .username("testuser" + uniqueNum + i)
                .password("Aa123123123")
                .email("test" + uniqueNum + i + "@example.com")
                .mobileCountryCode(Country.ISRAEL)
                .mobileArea("58")
                .mobilePhone(uniqueNum + i)
                .userType(UserTypes.PROUSER)
                .language(Language.ENGLISH_US)
                .timeZone(TimeZone.EST)
                .country(Country.UNITED_STATES)
                .company("Test Company " + uniqueNum)
                .build();
        users.add(user);
    }
    
    return users;
}
```

## 🔧 Методы класса

### `generateExcelFile(List<UserParams> users)`
Генерирует Excel файл с данными пользователей.

**Параметры:**
- `users` - список объектов UserParams

**Возвращает:**
- `String` - имя сгенерированного файла (например: `websandff20260120_143022.xlsx`)

**Файл сохраняется в:** `src/test/resources/lff/`

### `getFullPath(String filename)`
Получить полный путь к файлу.

**Параметры:**
- `filename` - имя файла

**Возвращает:**
- `String` - полный путь (например: `src/test/resources/lff/websandff20260120_143022.xlsx`)

## 📊 Формат имени файла

```
websandff[timestamp].xlsx
```

**Где:**
- `websandff` - префикс
- `[timestamp]` - временная метка в формате `yyyyMMdd_HHmmss`
- `.xlsx` - расширение (Excel 2007+)

**Примеры:**
- `websandff20260120_143022.xlsx`
- `websandff20260120_150345.xlsx`

## 📝 Маппинг полей UserParams → Excel

| Excel Column | UserParams Field | Формат |
|--------------|------------------|--------|
| Mobile Phone | mobileCountryCode + mobileArea + mobilePhone | +972-58-1234567 |
| Email Address | email | test@example.com |
| First Name | firstName | TestFN1 |
| Last Name | lastName | TestLN1 |
| Login | username | testuser1 |
| Password | password | Aa123123123 |
| Product | userType.displayName | Pro User |
| Language | language.name() | ENGLISH_US |
| Company | company | Test Company |
| Time Zone | timeZone.name() | EST |
| Country | country.displayName | United States |

*Остальные колонки заполняются пустыми значениями по умолчанию.*

## ⚠️ Важные замечания

1. **Директория создаётся автоматически** - если `src/test/resources/lff/` не существует, она будет создана
2. **Файлы не удаляются автоматически** - нужно периодически чистить старые файлы
3. **Timestamp уникален** - каждый запуск создаёт новый файл
4. **Формат .xlsx** - Excel 2007+ (не .xls)
5. **Обязательные поля:**
   - `username` (Login)
   - `email` (Email Address)
   - Остальные опциональны

## 🧹 Очистка старых файлов

```java
// Удалить все файлы старше 7 дней
public static void cleanupOldFiles() {
    Path dir = Paths.get("src/test/resources/lff");
    try (Stream<Path> files = Files.walk(dir)) {
        files.filter(Files::isRegularFile)
             .filter(f -> f.getFileName().toString().startsWith("websandff"))
             .filter(f -> {
                 try {
                     FileTime fileTime = Files.getLastModifiedTime(f);
                     return fileTime.toInstant()
                         .isBefore(Instant.now().minus(7, ChronoUnit.DAYS));
                 } catch (IOException e) {
                     return false;
                 }
             })
             .forEach(f -> {
                 try {
                     Files.delete(f);
                     log.info("Deleted old file: {}", f.getFileName());
                 } catch (IOException e) {
                     log.error("Failed to delete file: {}", f, e);
                 }
             });
    } catch (IOException e) {
        log.error("Error cleaning up old files", e);
    }
}
```

## 📚 См. также

- `UserParams.java` - модель данных пользователя
- `TestUsers.java` - хардкодед тестовые пользователи
- `TestDataFileUtils.java` - работа с путями к файлам
- `LFFPage.java` - Page Object для LFF функциональности

---

**Готово!** Генератор Excel файлов для LFF тестов полностью настроен и готов к использованию. 📊
