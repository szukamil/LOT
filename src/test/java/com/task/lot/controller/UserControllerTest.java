package com.task.lot.controller;

import com.task.lot.service.UserVisitService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserVisitService userVisitService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testPostWhenCorrect() throws Exception {
        String url = "/api/save";
        JSONObject bodyObject = new JSONObject();
        bodyObject.put("date","2020-10-15");
        bodyObject.put("ip","212.34.52.003");
        //
        mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(bodyObject))).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testGetWhenCorrect() throws Exception {
        String url = "/api/statistics/{ip}";
        String ip = "123.123.003";
        //
        mockMvc.perform(MockMvcRequestBuilders.get(url,ip)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllWhenCorrect() throws Exception {
        String url = "/api/statistics";
        //
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }
}