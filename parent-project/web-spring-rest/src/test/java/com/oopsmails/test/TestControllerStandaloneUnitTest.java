package com.oopsmails.test;

import com.oopsmails.test.config.WebConfigTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by liu on 2017-07-07.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfigTest.class})
public class TestControllerStandaloneUnitTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_get_all_success() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("John Snow")));
    }

    // =========================================== Get Users by Ids ==========================================

    @Test
    public void test_get_by_ids_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users").param("id", "1").param("id", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].username", is("Arya Stark")))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("responseContent = " + responseContent);
    }

    @Test
    public void test_get_by_ids_success_aop_changed_param() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users").param("id", "2").param("id", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1))) // this is changed dynamically by MethodParameterAspect
                .andExpect(jsonPath("$[0].username", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].username", is("Arya Stark")))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("responseContent = " + responseContent);
    }

    @Test
    public void test_get_by_ids_fail_01() throws Exception {
        final String ERR_MSG = "Failed to convert";
        MvcResult mvcResult = mockMvc.perform(get("/users").param("id", "a").param("id", "3"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("responseContent = " + responseContent);
        String errMsg = mvcResult.getResolvedException().getMessage();
        System.out.println("errMsg = " + errMsg);
        assertEquals(true, errMsg.indexOf(ERR_MSG) >= 0);
    }
}
