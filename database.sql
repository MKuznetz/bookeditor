#DROP DATABASE IF EXISTS test;
CREATE DATABASE IF NOT EXISTS test CHARACTER SET utf8 COLLATE utf8_general_ci;

USE test;

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  title VARCHAR(100) NOT NULL,
  description VARCHAR(255) NOT NULL,
  author VARCHAR(100) NOT NULL,
  isbn VARCHAR(20) NOT NULL,
  print_year INT NOT NULL,
  read_already BIT(1) NOT NULL DEFAULT 0
)
  ENGINE = innoDB
  DEFAULT CHARACTER SET = utf8
;

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Spring 4 для профессионалов (4-е издание)',
   'Вы изучите основы и ключевые темы, связанные с платформой Spring.',
   'Крис Шеффер, Кларенс Хо, Роб Харроп',
   '978-1-4302-6151-3',
   2017, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Алгоритмы. Теория и практическое применение',
   'Численные алгоритмы, структуры данных, методы работы с массивами, связанными списками и сетями',
   'Род Стивенс',
   '978-5-699-81729-0',
   2017, TRUE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Философия Java',
   'Основные проблемы написания кода: в чем их природа и какой подход использует Java в их разрешении.',
   'Брюс Эккель',
   '978-5-496-01127-3',
   2016, TRUE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Bash. Карманный справочник системного администратора',
   'Описание оболочки Bash',
   'Арнольд Роббинс',
   '978-5-9909445-4-1',
   2017, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Приемы объектно-ориентированного проектирования. Паттерны проектирования',
   'Принципы использования паттернов проектирования.',
   'Эрих Гамма, Ричард Хелм, Ральф Джонсон, Джон Влиссидес',
   '978-5-459-01720-5',
   2016, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Чистый код: создание, анализ и рефакторинг.',
   'Библиотека программиста. Как писать хороший код и как преобразовать плохой код в хороший.',
   'Роберт К. Мартин',
   '978-5-496-00487-9',
   2016, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Язык программирования Go',
   'Знакомство с языком программирования Go.',
   'Алан А. А. Донован, Брайан У. Керниган',
   '978-5-8459-2051-5',
   2016, TRUE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('ES6 и не только',
   'ES6 повествует о тонкостях языка Ecmascript 6 (ES6) - последней версии стандарта JavaScript.',
   'Кайл Симпсон',
   '978-5-8459-2051-5',
   2017, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('PHP 7',
   'Рассмотрены основы языка PHP и его рабочего окружения в Windows, Mac OS X и Linux.',
   'Дмитрий Котеров, Игорь Симдянов',
   '978-5-9775-3725-4',
   2017, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Zend Framework 2.0. Разработка веб-приложений',
   'Zend Framework 2 представляет собой последнее обновление фреймворка Zend Framework.',
   'Кришна Шасанкар',
   '978-5-496-00837-2',
   2014, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Разработка веб-приложений в Yii 2',
   'Yii-это высокопроизводительный фреймворк, используемый для быстрой разработки веб-приложений на РНР.',
   'Марк Сафронов',
   '978-5-97060-252-2',
   2015, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Java. Том 1. Основы.',
   'Исчерпывающее руководство и практическое справочное пособие для опытных программистов, стремящихся писать на Java.',
   'Кей С. Хорстманн',
   '978-5-8459-2084-3',
   2017, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Разработка Backbone.js приложений',
   'Backbone - это javascript-библиотека для тяжелых фронтэнд javascript-приложений, таких, например, как gmail или twitter.',
   'Эдди Османи',
   '978-5-496-00962-1',
   2014, FALSE );

INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('PHP объекты, шаблоны и методики программирования.',
   'Создавайте высокопрофессиональный код на PHP, изучив его объектно-орентированные возможности, шаблоны проектирования и важные средства разработки.',
   'Мет Зандстра',
   '978-5-8459-1689-1',
   2013, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Swing. Эффектные пользовательские интерфейсы.',
   'Создание пользовательских интерфейсов Java-приложений с помощью библиотеки Swing и Java Foundation Classes.',
   'Иван Портянкин',
   '978-5-85582-305-9',
   2011, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Kotlin в действии.',
   'Язык Kotlin предлагает выразительный синтаксис, мощную и понятную систему типов, великолепную поддержку и бесшовную совместимость с существующим кодом на Java, богатый выбор библиотек и фреймворков.',
   'Дмитрий Жемеров, Светлана Исакова',
   '978-5-97060-497-7',
   2017, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Spring в действии.',
   'Фреймворк Spring Framework - необходимый инструмент для разработчиков приложений на Java.',
   'Крейг Уоллс',
   '978-5-97060-171-6',
   2015, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('AJAX и PHP. Разработка динамических веб-приложений.',
   'Книга "AJAX и PHP. Разработка динамических веб-приложений" - самый удобный и полезный ресурс, который поможет вам войти в захватывающий мир AJAX.',
   'Кристиан Дари, Богдан Бринзаре, Филип Черчез-Тоза, Михай Бусика',
   '5-93286-077-4',
   2009, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Разработка веб-приложений в ReactJS.',
   'ReactJS выделяется из массы прочих веб-фреймворков собственным подходом к композиции, который обеспечивает сверхбыстрое отображение.',
   'Адам Хортон, Райан Вайс',
   '978-5-94074-819-9',
   2016, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('XSLT',
   'Цель книги - научить читателя эффективно использовать XSLT.',
   'Дуг Тидуэлл',
   '978-5-93286-150-9',
   2010, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('JavaScript. Шаблоны.',
   'Данная книга предлагает большое количество различных шаблонов программирования на JavaScript.',
   'Стоян Стефанов',
   '978-5-93286-208-7',
   2011, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Node.js. Разработка серверных приложений на JavaScript.',
   'Книга посвящена разработке веб-приложений в Node.js - платформе, которая выводит язык JavaScript за пределы браузера и позволяет использовать его в серверных приложениях.',
   'Дэвид Хэррон',
   '978-5-97060-397-0',
   2016, FALSE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Python. Разработка на основе тестирования.',
   'На реальном примере. Операции TDD. Модульный тест, программный код и рефакторизация. Функциональные и нтегрированные тесты. Автотесты, среда непрерывной интеграции.',
   'Гарри Персиваль',
   '978-5-97060-594-3',
   2018, TRUE );
   
INSERT INTO book (title, description, author, isbn, print_year, read_already) VALUES
  ('Vaadin 7 UI Design By Example Beginners Guide.',
   'This book will teach you how to develop Java web applications in minutes.',
   'Alejandro Duarte',
   '978-1-78216-226-1',
   2013, TRUE );