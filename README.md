# News
RESTful web-service реализующей функционал для работы с системой управления новостями. В приложении реализовано кеширование.

Основные сущности:
- news (новость) содержит поля: id, date, title, text
- comment (коментарий) содержит поля: id, date, text, user_id, news_id
- user (пользователь) содержит поля: id, username, firstname, lastname, role

## NEWS
### POST
* ### добавить новость
```
http://localhost:8080/api/v1/news
```
### PUT
* ### обновить новость
```
http://localhost:8080/api/v1/news/{id}
```
### DELETE
* ### удалить новость
```
http://localhost:8080/api/v1/news/{id}
```
### GET
* ### получить новость по id
```
http://localhost:8080/api/v1/news/{id}
```
* ### получить все новости
```
http://localhost:8080/api/v1/news
```
## COMMENT
### POST
* ### добавить комментарий
```
http://localhost:8080/api/v1/comments
```
### PUT
* ### обновить комментарий
```
http://localhost:8080/api/v1/comments/{id}
```
### DELETE
* ### удалить комментарий
```
http://localhost:8080/api/v1/comments/{id}
```
### GET
* ### получить комментарий по id
```
http://localhost:8080/api/v1/comments/{id}
```
* ### получить все комментарии
```
http://localhost:8080/api/v1/comments
```
## USER
### POST
* ### добавить пользователя
```
http://localhost:8080/api/v1/users
```
### PUT
* ### обновить пользователя
```
http://localhost:8080/api/v1/users/{id}
```
### DELETE
* ### удалить пользователя
```
http://localhost:8080/api/v1/users/{id}
```
### GET
* ### получить пользователя по id
```
http://localhost:8080/api/v1/users/{id}
```
* ### получить пользователя по username
```
http://localhost:8080/api/v1/users/user/{username}
```
* ### получить всех пользователей
```
http://localhost:8080/api/v1/users
```

### Spring Security:
 * Администратор (role ADMIN) может производить CRUD-операции со всеми сущностями
 * Журналист (role JOUR) может добавлять и изменять/удалять только свои новости и комментарии
 * Подписчик (role USER) может добавлять и изменять/удалять только свои комментарии
 * Незарегистрированные пользователи могут только просматривать новости и комментарии