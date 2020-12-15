package cn.iocoder.mall.userweb.controller.auth.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Mr.z
 * @since 2020/12/15-11:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserControllerTest {

    // 启用web上下文
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        // 使用上下文构建mockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getUserInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/info")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer d5e05d95282e4e1393842215ef7fcc70")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUserAvatar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update-avatar")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer d5e05d95282e4e1393842215ef7fcc70")
                .param("avatar", "http://test.png")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUserNickname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update-nickname")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer d5e05d95282e4e1393842215ef7fcc70")
                .param("nickname", "啦啦啦啦")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andDo(MockMvcResultHandlers.print());
    }
}