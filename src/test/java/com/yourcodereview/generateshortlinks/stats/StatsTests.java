package com.yourcodereview.generateshortlinks.stats;

import com.yourcodereview.generateshortlinks.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StatsTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LinkRepository linkRepository;

    @BeforeEach
    public void setUp() {
        linkRepository.saveLink("https://qwe.wer.asd/qwe/asd/zxc?param=1", "C59A2aJ");
        linkRepository.saveLink("https://qwe.wer.asd/qwe/asd/zxc?param=2", "C59rPL8");
        for (int i = 0;i < 10;i++) {
            linkRepository.getOriginalLink("C59A2aJ", false);
        }
        for (int i = 0;i < 20;i++) {
            linkRepository.getOriginalLink("C59rPL8", false);
        }
    }

    @Test
    public void return_statsByShortLink() throws Exception {
        mockMvc.perform(get("/stats/C59A2aJ"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link", is("/l/C59A2aJ")))
                .andExpect(jsonPath("$.original", is("https://qwe.wer.asd/qwe/asd/zxc?param=1")))
                .andExpect(jsonPath("$.rank", is(2)))
                .andExpect(jsonPath("$.count", is(20)));

        mockMvc.perform(get("/stats/C59rPL8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link", is("/l/C59rPL8")))
                .andExpect(jsonPath("$.original", is("https://qwe.wer.asd/qwe/asd/zxc?param=2")))
                .andExpect(jsonPath("$.rank", is(1)))
                .andExpect(jsonPath("$.count", is(40)));
    }

    @Test
    public void return_statsByRank() throws Exception {
        mockMvc.perform(get("/stats?page=0&count=100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].link", is("/l/C59rPL8")))
                .andExpect(jsonPath("$[0].original", is("https://qwe.wer.asd/qwe/asd/zxc?param=2")))
                .andExpect(jsonPath("$[0].rank", is(1)))
                .andExpect(jsonPath("$[0].count", is(20)))
                .andExpect(jsonPath("$[1].link", is("/l/C59A2aJ")))
                .andExpect(jsonPath("$[1].original", is("https://qwe.wer.asd/qwe/asd/zxc?param=1")))
                .andExpect(jsonPath("$[1].rank", is(2)))
                .andExpect(jsonPath("$[1].count", is(10)));
    }
}
