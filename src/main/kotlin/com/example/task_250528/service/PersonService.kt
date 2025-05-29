package com.example.task_250528.service

import com.example.task_250528.domain.PersonDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

// SpecialistInfoId@Component
class PersonService {

    // DB 대신 사용
    private  val people: ConcurrentHashMap<String, PersonDto> = ConcurrentHashMap();


    // POST
    fun createPerson(personDto: PersonDto): Mono<Boolean> {
        println("파라미터 : personDto=$personDto")
        return Mono.fromCallable { // ConcurrentHashMap(동기) 를 비동기 처리 하기 위해 사용
                                    //
            // 랜덤 id
            val createId = UUID.randomUUID().toString()
            // 객체 불변성을 위해 copy()
            val obj = personDto.copy(id = createId)
            // 등록
            people[createId] = obj
            // 결과 반환
            true
        }
    }

    fun findAll(): Flow<PersonDto> {
        // ConcurrentHashMao(동기) 을 asFlow 로 비동기 결과 반환
        return people.values.asFlow();
    }
    fun findById(id: String): Mono<PersonDto>{
        println("파라미터 : id=$id")
        // id 로 조회 (없으면 null)
        val findPersonDto: PersonDto? = people[id];

        // 결과 반환
        return Mono.fromCallable { findPersonDto }

    }

    fun updateById(id: String, personDto: PersonDto): Mono<Boolean> {
        println("파라미터 : id=$id, personDto=$personDto")

        return Mono.fromCallable {
            // 아이디 존재 시
            if (people.containsKey(id)) {
                // 수정
                people[id] = personDto
                // 결과 반환
                true
            } else {
                // 결과 반환
                false
            }
        }
    }

    fun deleteById(id: String): Mono<Boolean>{
        println("파라미터 : id=$id")
        return Mono.fromCallable {
            // 아이디 존재 시 삭제 후 true 없으면 false 결과 반환
            people.remove(id) != null;
        }
    }


}