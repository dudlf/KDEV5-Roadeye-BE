package org.re.mdtlog.collector.app.cyclelog;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.re.mdtlog.collector.app.cyclelog.dto.MdtAddCycleLogRequestFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MdtCycleLogControllerIntegrationTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Timestamp 헤더가 없으면 요청에 실패해야 한다.")
    public void testAddCycleLogsWithoutTimestamp() throws Exception {
        // given
        var tuid = UUID.randomUUID().toString();
        var dto = MdtAddCycleLogRequestFixture.create(3);
        var body = objectMapper.writeValueAsString(dto);

        // when
        var req = post("/api/cycle-log")
            .header("X-TUID", tuid)
            .contentType("application/json")
            .content(body);
        mvc.perform(req)
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("TUID 헤더가 없으면 요청에 실패해야 한다.")
    public void testAddCycleLogsWithoutTuid() throws Exception {
        // given
        var dto = MdtAddCycleLogRequestFixture.create(3);
        var body = objectMapper.writeValueAsString(dto);

        // when
        var req = post("/api/cycle-log")
            .header("X-TIMESTAMP", "2023-10-01 15:23:00.412")
            .contentType("application/json")
            .content(body);
        mvc.perform(req)
            .andExpect(status().isInternalServerError());
    }
}
