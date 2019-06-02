# Sudoku
====

## 概要

以下の機能を作成予定です。

name   | Front | Batch | Admin
:----- | :---- | :---- | :----
数独解答作成 | ○     | ○     | ○
数独虫食作成 | ○     | ✖︎    | ✖︎
数独実行   | ○     | ✖︎    | ✖︎
数独検索   | ○     | ✖︎    | ○
スコア登録  | ○     | ✖︎    | ✖︎
スコア編集  | ✖︎    | ✖︎    | ○
管理機能   | ✖︎    | ✖︎    | ○

上記をマルチプロジェクトで作成し、管理します。

## 起動方法

1. 当プロジェクトをIntelliJ IDEAにGradleProjectとしてclone  
2. docker-composeよりMysqlを起動  
    `$ cd docker`  
    `$ docker-compose -f docker-compose.yml build`  
    `$ docker-compose -f docker-compose.yml up -d`  
3. GradleタスクよりbootRun(`:micro-api -> Tasks -> application -> bootRun`)
4. GradleタスクよりbootRun(`:micro-web -> Tasks -> application -> bootRun`)

## 停止方法

1. bootRunの停止  
2. docker-composeより停止  
    `$ cd docker`  
    `$ docker-compose -f docker-compose.yml stop`
3. docker-composeよりコンテナ破棄  
    `$ docker-compose -f docker-compose.yml down`
    
## Endpoints

[HAL Browser][] 
[SudokuWeb][] 
[Zipkin][]
[PhpMyAdmin][]
    
## 環境

### Middleware

| name              | version
| :---------------- | :-------
| OpenJdk           | 11
| DockerCompose     | 1.24.0
| Gradle            | 5.4.1 

[HAL Browser]: http://localhost:8085/     "HAL Browser"
[SudokuWeb]: http://localhost:8081/linkList     "SudokuWeb"
[Zipkin]: http://localhost:9411/zipkin     "Zipkin"
[PhpMyAdmin]: http://localhost:8021/     "PhpMyAdmin"