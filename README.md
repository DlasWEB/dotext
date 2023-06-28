## Dotext (Дотекст)

<p align="center">
      <img src="https://github.com/DlasWEB/dotext/blob/master/img_for_readme/01.png" alt="Дотекст">
</p>

## Описание

Дотекст это REST Api сервис для распространения текста. Построен по аналогии с [Pastebin](https://pastebin.com/). 

В приложении реализована регистрация и авторизация по протоколу OAuth 2.0. Для выполнения большинства запросов к Api необходимо передавать access-токен.

Хранение текста производится в MongoDB, а которкие ссылки для доступа к тесту хранятся в MySQL или PostgreSQL бд.

По умолчанию, текст занесенный в бд живет 1 час, при явном указанаии другого значения, текст будет сохранен в бд на неопредленный срок.

Через простые HTTP запросы к Api (GET, POST, PUT, DELETE), авторизованному пользователю можно выполнить весь набор CRUD операций с текстом.


## Схема

<p align="center">
      <img src="https://github.com/DlasWEB/dotext/blob/master/img_for_readme/scheme.png" alt="Схема приложения">
</p>

## Установка

Докнца не реализовано, скоро будет!

[//]: # (Для развертывания приложения необходим `docker` и два образа: `kubyshka` и `БД &#40;Mysql или PostreSql&#41;`. Приложение можно развернуть локально или на сервере.)

[//]: # (Для разворачивания приложения нужно выполнить следующие шаги &#40;**инструкция на примере Debian подобных систем**&#41;:)

[//]: # (1. Качаем образы с:<br/>)

[//]: # (    - `mysql сервером` &#40;если у вас его нет&#41; командой:)

[//]: # (        ```shell script)

[//]: # (        $ docker pull mysql)

[//]: # (        ```)

[//]: # (    - `kubyshka` командой:)

[//]: # (        ```shell script)

[//]: # (        $ docker pull mrdlas/kubyshka:kubyshka)

[//]: # (        ```)

[//]: # (2. Проверяем)

[//]: # (     ```shell script)

[//]: # (     $ docker images)

[//]: # (     ``` )

[//]: # (   если все ок, то вы увидите образы `mrdlas/kubyshka` и `mysql` &#40;возможно будут и другие, если они есть у вас на машине&#41;)

[//]: # (3. Создаем сеть для контакта между контейнером с БД и контейнером с Kubyshka)

[//]: # (     ```shell script)

[//]: # (     $ docker network create kubyshka-net)

[//]: # (     ```)

[//]: # (4. Поднимаем контейнеры с:)

[//]: # (    - mysql)

[//]: # (        ```shell script)

[//]: # (        $ sudo docker run --restart=unless-stopped -dit --net kubyshka-net -p 3307:3306 --name mysqldb_kubyshka -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=kubyshka_db -v kubyshka_storage:/var/lib/mysql mysql)

[//]: # (        ```)

[//]: # (    - kubyshka)

[//]: # (        ```shell script)

[//]: # (        $ docker run --restart=unless-stopped -dit -p 9090:8080 --name my-kubyshka --net kubyshka-net -e MYSQL_HOST=mysqldb_kubyshka -e MYSQL_USER=root -e MYSQL_PASSWORD=root -e MYSQL_PORT=3306 mrdlas/kubyshka:kubyshka        )

[//]: # (        ```)

[//]: # (      Название бд, название контейнеров, пароль root пользователя в бд и порты можно ставить свои. Главное чтобы все согласовывалось.)

[//]: # (5. Проверяем успешность подключения)

[//]: # (    ```shell script)

[//]: # (   $ docker container inspect mysqldb_kubyshka )

[//]: # (    ```)

[//]: # (   если все ок, то в разделе Networks вы увидите сеть `kubyshka-net`)

[//]: # (    ```shell script)

[//]: # (   $ docker container inspect ID_контейнера_с_кубышкой)

[//]: # (    ``` )

[//]: # (   узнать ID контейнера можно командой)

[//]: # (   ```shell script)

[//]: # (   $ docker ps )

[//]: # (   ```)

[//]: # (   если все ок, то в разделе Networks вы увидите сеть `kubyshka-net`.)

[//]: # ()
[//]: # (   ### Готово!)

[//]: # ()
[//]: # (   В браузере проходим по адресу 'localhost:9090/' и вы уведите рабочее приложение &#40;если устанавливали на сервере, то вместо localhost вбивайте его ip&#41;)

[//]: # ()
[//]: # (   <img src="https://github.com/DlasWEB/kubyshka/blob/master/img_for_readme/1.png" alt="Главная страница">)

[//]: # ()
[//]: # (   ### Далее:)

[//]: # (    1. Регистрируйте пользователя)

[//]: # (    2. Логинтесь)

[//]: # (    3. Добавляйте валюту в требуемом формате)

[//]: # (    4. Добавляйте типы сбережений)

[//]: # (    5. Добавляйте сбережения.)

[//]: # (    6. Смотрите срезы.)

   ### P.S.

   Приложение достаточно простое и сырое, т.к. выполнялось как пэт проект, но я только учусь и собираюсь поддерживать и развивать его дальше.

   ## Разработчики

    - [Денис Ласкин](https://github.com/DlasWEB)

   ## Лицензия

   Проект **[Kubyshka](https://github.com/DlasWEB/kubyshka)** распространяется под лицензией GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007.
