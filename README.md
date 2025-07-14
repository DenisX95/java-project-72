## Анализатор страниц

Приложение на Java для анализа веб-сайтов и отслеживания изменений мета-данных.

[![Actions Status](https://github.com/DenisX95/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/DenisX95/java-project-72/actions)
[![Actions Status](https://github.com/DenisX95/java-project-72/actions/workflows/sonar-check.yml/badge.svg)](https://github.com/DenisX95/java-project-72/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=DenisX95_java-project-72&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=DenisX95_java-project-72)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=DenisX95_java-project-72&metric=bugs)](https://sonarcloud.io/summary/new_code?id=DenisX95_java-project-72)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=DenisX95_java-project-72&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=DenisX95_java-project-72)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=DenisX95_java-project-72&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=DenisX95_java-project-72)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=DenisX95_java-project-72&metric=coverage)](https://sonarcloud.io/summary/new_code?id=DenisX95_java-project-72)

**Веб-адрес**: https://java-project-72-phub.onrender.com

**Анализатор страниц** — это веб-приложение, позволяющее добавлять сайты для проверки и получать информацию о:

- статусе HTTP-ответа;
- содержимом тега `<title>`;
- первом заголовке `<h1>`;
- мета-теге `description`.

Каждая проверка сохраняется в базе данных и отображается на странице сайта в виде истории.

## Функциональность

- ✅ Добавление сайтов по URL.
- ✅ Отображение списка всех сайтов с датой последней проверки и статусом ответа.
- ✅ Переход на страницу конкретного сайта.
- ✅ Проверка сайта по нажатию кнопки (скачивание и парсинг HTML).
- ✅ Отображение истории всех проверок с деталями.
- ✅ Flash-сообщения об успешных и ошибочных действиях.

## Технологии

- **Java 17**
- **Javalin** — лёгкий веб-фреймворк
- **H2** (встроенная БД для тестов) / PostgreSQL (в проде)
- **JTE** — шаблонизатор HTML
- **Jsoup** — парсинг HTML-страниц
- **Unirest** — HTTP-клиент
- **Gradle** — сборка проекта
- **JUnit + MockWebServer** — тестирование

## Установка и запуск

```bash
make install
make start
```
