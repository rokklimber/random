package org.example.hello

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * spring boot boilerplate launcher class for web application
 */
@SpringBootApplication
class Application {
}

object Application extends App {
  SpringApplication.run(classOf[Application], args:_*)
}