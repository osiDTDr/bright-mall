package cn.iocoder.mall.userweb.controller.auth.address;

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
 * @since 2020/12/15-13:23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserAddressControllerTest {

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
    public void createUserAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user-address/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer db0c9a02f72543188f729157eee09ca9")
                .param("areaCode", "610632")
                .param("detailAddress", "测试街道558号")
                .param("mobile", "15601691234")
                .param("name", "测试人员")
                .param("type", "1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUserAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user-address/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer a8f9571bf4934fd89615c8385d24c7b6")
                .param("areaCode", "610632")
                .param("detailAddress", "杭州市下沙街道不知名互联网公司789号")
                .param("mobile", "15601691234")
                .param("name", "测试人员")
                .param("type", "1")
                .param("id", "3")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user-address/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer d5e05d95282e4e1393842215ef7fcc70")
                .param("userAddressId", "3")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getUserAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user-address/get")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer a8f9571bf4934fd89615c8385d24c7b6")
                .param("userAddressId", "3")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.detailAddress").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getDefaultUserAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user-address/get-default")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer 18c1fdf73a914e53a6aee56e1c6af066")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.detailAddress").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void listUserAddresses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user-address/list")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer 18c1fdf73a914e53a6aee56e1c6af066")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andDo(MockMvcResultHandlers.print());
    }
}