package org.re;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.re.app.hello.HelloController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
@ExtendWith({OutputCaptureExtension.class})
class HelloControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testHello() throws Exception {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.message").value("Hello, World!"));
    }

    @Test
    void testError() throws Exception {
        mockMvc.perform(get("/error"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.error.code").value("500"))
            .andExpect(jsonPath("$.error.message").value("Internal Server Error"));
    }

    @Test
    void testErrorInternal(CapturedOutput out) throws Exception {
        mockMvc.perform(get("/error-internal"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.error.code").value("500"))
            .andExpect(jsonPath("$.error.message").value("Internal Server Error"));

        assertThat(out).contains("java.lang.RuntimeException:");
        assertThat(out).contains("at org.re.app.hello.HelloController.errorInternal(HelloController.java:");
    }
}
