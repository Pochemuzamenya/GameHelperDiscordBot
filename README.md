<div align="center">

![logo](files/dotalogo.png)

# Discord game helper

</div>

## Table of contents
* [Disclaimer](#disclaimer)
* [Introduction](#introduction)
* [Install](#install)
* [Roadmap](#roadmap)

---

## Disclaimer

Проект находится в разработке и годится только что бы поделиться кодом и практиками, пока :)
## Introduction

Game helper разрабатывается что бы помогать определиться с выбором героя и со сборкой предметов на основе данных из профессиональных игр.

Бот написан с помощью [Discord4J - The fastest Discord API wrapper written in Java](https://discord4j.com/)
, [Spring Boot](https://spring.io/) и [OpenDota API](https://docs.opendota.com/).
Он слушает текстовые каналы, и обрабатывает ввод от пользователей (не ботов). 

Синтаксис команд: `!имя_команды параметр1 параметр2 ...`


[//]: # (Я играю в dota2 с перерывами в несколько месяцев, за это время баланс в игре может поменяться. )

[//]: # (Поэтому я часто сталкивался с тем, что я не знаю какого героя выбрать и что ему собрать. )

[//]: # (Раньше я использовал сайт [DOTABUFF - Dota 2 Statistics]&#40;..%2F..%2FAppData%2FLocal%2FTemp%2FDOTABUFF%20-%20Dota%202%20Statistics.url&#41;,)

[//]: # (но во время пиков открывать новую вкладку в браузере, переходить на сайт, там искать таблицу, а таблица на сайте не самая удобная, всё это мне кажется неудобным.)

[//]: # (Поэтому я решил создать своего простого помощника, который бы показывал мне какие герои наиболее успешно играли на турнирах в этом патче и что на них собирали. )

[//]: # (Так как обычно я играю с друзьями и discord запущен постоянно, то решение создать имеено бота напросилось само собой, плюсом мои друзья могли бы его использовать и не спрашивать у меня на ком сыграть и что собрать :&#41; )

## Install

Что бы запустить бота на своей машине выполните:
```
git clone https://github.com/Pochemuzamenya/GameHelperDiscordBot
```
В директории [resources](src/main/resources) нужно создать properties файл, например я использую `application.yml`:
```yaml
spring:
  profiles:
    active: development

---

token: 'ваш токен discord'
steam_id: 'ваш steam id'
guild_id: 'ваш guild id'
opendota: 'https://api.opendota.com/api/'

spring:
  config:
    activate:
      on-profile: development
  r2dbc:
    url: r2dbc:mysql://localhost:3306
    name: discord_bot
    username: username
    password: pwd
```
### Обязательные поля

---
`token` можно получить на [Discord Developer Portal](https://discord.com/developers/applications), без него бот не сможет работать.

`opendota` это адрес API который отвечает на наши запросы, можно использовать любой другой, но придётся изменять класс [HeroStats](src/main/java/org/filatov/api/HeroStats.java).

Без подключения к БД `mvn spring-boot:run` упадёт:) Наверное можно пофиксить, если удалить [MemberRepository](src/main/java/org/filatov/repo/MemberRepository.java). **Обратите внимание что для блокирующих СУБД требуется реактивный драйвер**.

### Необязательные поля

---

`steam_id` нужен для получения личной статистики в запросах.

`guild_id` нужен для создания слэш команд. Что это такое - [Discord4j Docs](https://docs.discord4j.com/interactions/application-commands)

---
### Сборка

---
Для сборки используется [maven](https://maven.apache.org/).

Выполните в корневой папке проекта:
```shell
mvn spring-boot:run
```

Основы Spring Boot можно изучить в руководстве [Getting Started](https://spring.io/guides/gs/spring-boot/).  
## Roadmap

Проект находится в разработке.

Основные функции:
* Показывать список популярных героев;
* Показывать популярную сборку на героя.

### TODO
* Регистрация пользователя для получения персональной статистики;
* Поддержка добавления новых текстовых команд во время выполнения программы, иными словами для добавления новых команд не будет требоваться перезагрузка бота;
* Добавить обработку слэш команд "/...";
* Получение данных напрямую из клиента игры через RPC;


### Refs
Иконка, которую я использовал для аватарки бота - [Source avatar icon](https://za.pinterest.com/pin/406942516316083151/)

Моя почта - filatovprivate@gmail.com