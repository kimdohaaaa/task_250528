package com.example.task_250528.handler

import com.example.task_250528.domain.PersonDto
import com.example.task_250528.service.PersonService
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.stereotype.Component

@Component
class PersonHandler (private val personService: PersonService){ // PersonService 의존성 주입
    // POST
    suspend fun createPerson(request: ServerRequest): ServerResponse{
        // HTTP Request Body 에서 PersonDto 객체 받아오기(비동기)
        val  personDto = request.awaitBody<PersonDto>();
        println("Request 확인 : personDto = $personDto")

        // PersonService의 처리 결과를 awaitSingle 로 대기 후 반환 받음 (단일값)
        val  result: Boolean = personService.createPerson(personDto).awaitSingle();
        println("Response 확인 : $result")

        // 결과 반환
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(result)
    }

    // GET(전체 조회)
    suspend fun findAll(request: ServerRequest): ServerResponse {
        // PersonService의 처리결과 반환 (다중값)
        val result : Flow<PersonDto> = personService.findAll();
        println("Response 확인 : $result")

        // 결과반환
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyAndAwait(result);
    }

    // GET(특정 id 조회)
    suspend fun findById(request: ServerRequest): ServerResponse {
        // URL 로 전달받은 id
        val id = request.pathVariable("id");
        println("Request 확인 : id = $id")

        // PersonService의 처리 결과를 awaitSingle 로 대기 후 반환 받음 (단일값)
        val result : PersonDto = personService.findById(id).awaitSingle();
        println("Response 확인 : $result")

        // 결과반환
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(result);
    }

    // PUT
    suspend fun updateById(request: ServerRequest): ServerResponse{
        // URL 로 전달받은 id
        val  id = request.pathVariable("id");
        // HTTP Request Body 에서 PersonDto 객체 받아오기(비동기)
        val personDto = request.awaitBody<PersonDto>();
        println("Request 확인 : id = $id personDto = $personDto")

        // PersonService의 처리 결과를 awaitSingle 로 대기 후 반환 받음 (단일값)
        val result : Boolean = personService.updateById(id, personDto).awaitSingle();
        println("Response 확인 : $result")

        // 결과반환
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(result)
    }

    // DELETE
    suspend fun deleteById(request: ServerRequest): ServerResponse {
        // URL 로 전달받은 id
        val id = request.pathVariable("id");
        println("Request 확인 : id = $id")

        // PersonService의 처리 결과를 awaitSingle 로 대기 후 반환 받음 (단일값)
        val result : Boolean = personService.deleteById(id).awaitSingle();
        println("Response 확인 : $result")

        // 결과반환
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(result)
    }
}