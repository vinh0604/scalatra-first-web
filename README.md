# Book pricing #

Backend and frontend source code for Book Pricing web extension

## Build & Run ##

#### Backend

```sh
$ cd Scalatra_First_Web
$ ./sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

#### Frontend

```sh
$ cd Scalatra_First_Web/src/main/webapp
$ npm install
$ jspm install
$ node_modules/.bin/gulp
```