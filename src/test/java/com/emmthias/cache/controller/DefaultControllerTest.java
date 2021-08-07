package com.emmthias.cache.controller;

import com.emmthias.cache.service.CacheService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DefaultController.class)
class DefaultControllerTest {

    @MockBean
    CacheService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenHealthHasReach_returnSuccess() throws Exception {

        final ResultActions resultActions =
                this.mockMvc.perform(get("/_health").contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());
        resultActions.andExpect(status().isOk());

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject response = new JSONObject(contentAsString);

        assertNotNull(response);
    }

    @Test
    public void whenInfoHasReach_returnSuccess() throws Exception {

        final ResultActions resultActions =
                this.mockMvc.perform(get("/info").contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());
        resultActions.andExpect(status().isOk());

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject response = new JSONObject(contentAsString);

        assertNotNull(response);
    }

}