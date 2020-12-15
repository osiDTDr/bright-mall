package cn.iocoder.mall.userweb.controller.pub.passport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Mr.z
 * @since 2020/12/14-20:55
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class PassportControllerTest {

    // 启用web上下文
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        // 使用上下文构建mockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 通过验证码登录
     *
     * @throws Exception 异常
     */
    @Test
    public void loginBySms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/passport/login-by-sms")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("code", "9999").param("mobile", "15601691234")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.accessToken").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 获取验证码
     *
     * @throws Exception 异常
     */
    @Test
    public void sendSmsCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/passport/send-sms-code")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("mobile", "15601691234").param("scene", "1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 刷新token
     *
     * @throws Exception 异常
     */
    @Test
    public void refreshToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/passport/refresh-token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("refreshToken", "f6afbb4dde4d42f893850dccd82208ff")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.accessToken").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }
}