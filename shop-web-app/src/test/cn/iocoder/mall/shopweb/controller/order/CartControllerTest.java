package cn.iocoder.mall.shopweb.controller.order;

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
 * @since 2020/12/16-10:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CartControllerTest {
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
    public void addCartItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer 3f5d513c58ed4abeaa71f5276c1634e5")
                .param("quantity","1")
                .param("skuId","3")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void sumCartItemQuantity() {
    }

    @Test
    public void getCartDetail() {
    }

    @Test
    public void updateCartItemQuantity() {
    }

    @Test
    public void updateCartItemSelected() {
    }
}