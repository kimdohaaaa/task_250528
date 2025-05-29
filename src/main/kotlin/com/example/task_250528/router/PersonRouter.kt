package com.example.task_250528.router

import com.example.task_250528.handler.PersonHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

// SpecialistInfoId
@Configuration
class PersonRouter(private val personHandler: PersonHandler) { // PersonHandler 주입


    @Bean // Spring 에 bean 등록
    fun personRoutes(): RouterFunction<ServerResponse> {
        return coRouter {
            accept(MediaType.APPLICATION_JSON).nest { // Content-Type : application/json
                GET("/person/{id}", personHandler::findById)
                GET("/person", personHandler::findAll)
                POST("/person", personHandler::createPerson)
                DELETE("/person/{id}", personHandler::deleteById)
                PUT("/person/{id}", personHandler::updateById)
            }

        }

    }
}