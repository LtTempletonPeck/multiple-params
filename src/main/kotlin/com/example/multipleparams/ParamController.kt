package com.example.multipleparams

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ParamController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun query(@ModelAttribute query: ParamQuery) = query.ids1 + query.ids2

}

data class ParamQuery(
    val ids1: Set<String> = setOf()
) {
    var ids2: Set<String> = setOf()
}
