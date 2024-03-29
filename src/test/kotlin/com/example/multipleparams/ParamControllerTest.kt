package com.example.multipleparams

import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ParamController::class)
internal class ParamControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `multiple params returns 4 entries`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1")
                .param("ids1", "A", "B") // vararg param values
                .param("ids2", "C", "D") // vararg param values
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("""["A","B","C","D"]"""))
            .andExpect(jsonPath("$.length()", equalTo(4)))
            .andExpect(jsonPath("$[0]", equalTo("A")))
            .andExpect(jsonPath("$[1]", equalTo("B")))
            .andExpect(jsonPath("$[2]", equalTo("C")))
            .andExpect(jsonPath("$[3]", equalTo("D")))
    }

    @Test
    fun `multiple params comma-separated returns 4 entries`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1")
                .param("ids1", "A,B") // comma-separated param values
                .param("ids2", "C,D") // comma-separated param values
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("""["A","B","C","D"]""")) // this fails as it returns ["A,B","C","D"]
            .andExpect(jsonPath("$.length()", equalTo(4)))
            .andExpect(jsonPath("$[0]", equalTo("A")))
            .andExpect(jsonPath("$[1]", equalTo("B")))
            .andExpect(jsonPath("$[2]", equalTo("C")))
            .andExpect(jsonPath("$[3]", equalTo("D")))
    }

}
