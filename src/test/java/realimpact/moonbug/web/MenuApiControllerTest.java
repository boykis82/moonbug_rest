package realimpact.moonbug.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import realimpact.moonbug.domain.menu.*;
import realimpact.moonbug.web.dto.*;
import sun.nio.cs.UTF_8;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuRepositorySupport menuRepositorySupport;

    @Autowired
    private WebApplicationContext context;

    /*
    ???????????? ??????????????? LocalDateTime??? ????????? ??? ISO8601 ????????? ??????????????? ?????? ????????????.(???????????? ???????????? ???????????? Jackson?????????????????? ????????? ??? ?????? ????????????.) ????????? ????????? ????????? ????????????. ?????? ???????????? ????????? ????????? ????????????.
    ???????????? ??? ISO8601 ????????? ?????????, ????????? ???????????? ???????????? ObjectMapper??? ????????? ????????? ??? ObjectMapper??? ??????????????????.
    @Autowired ObjectMapper objectMapper;
     */
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // print()??? ????????? ????????? ??????
                .alwaysDo(print())
                .build();
    }

    @After
    public void testDown() {
        menuRepository.deleteAll();
    }

    @Test
    public void testCreateMenu() throws Exception {
        MenuCreateRequestDto americanoDto = createAmericanoCreateRequestDto();

        String url = "http://localhost:" + port + "/api/v1/menus";

        mvc.perform(post(url)
                .contentType( MediaType.APPLICATION_JSON )
                .content( objectMapper.writeValueAsString(americanoDto) )
        ).andExpect( status().isOk() );
    }

    @Test
    public void testUpdateMenu() throws Exception {
        menuRepository.save(TestMenuEntityFactory.createAmericano());
        Menu americano = menuRepository.findByName("???????????????").get();

        MenuUpdateRequestDto menuUpdateRequestDto = createAmericanoUpdateRequestDto(
                americano.getId(), "???????????????v2", "??? ????????? ?????????????????????.", americano.getStartDate(), americano.getExpireDate(), americano.getMenuCategory());

        String url = "http://localhost:" + port + "/api/v1/menus/" + americano.getId();

        mvc.perform(
                put(url)
                .contentType( MediaType.APPLICATION_JSON )
                .content( objectMapper.writeValueAsString(menuUpdateRequestDto) )
        ).andExpect( status().isOk() );
    }

    @Test
    public void testDeleteMenu() throws Exception {
        menuRepository.save(TestMenuEntityFactory.createAmericano());
        Menu americano = menuRepository.findByName("???????????????").get();

        String url = "http://localhost:" + port + "/api/v1/menus/" + americano.getId();

        mvc.perform(
                delete(url)
                .contentType( MediaType.APPLICATION_JSON )
        ).andExpect( status().isOk() );
    }

    @Test
    public void testAddSizePolicy() throws Exception {
        menuRepository.save(TestMenuEntityFactory.createAmericano());
        Menu americano = menuRepository.findByName("???????????????").get();

        MenuSizePolicyCreateRequestDto shortSizeCreateRequestDto = createMenuSizePolicyCreateRequestDto(MenuSize.SHORT, 4100, 8.4);

        String url = "http://localhost:" + port + "/api/v1/menus/" + americano.getId() + "/size";

        mvc.perform(
                post(url)
                .contentType( MediaType.APPLICATION_JSON )
                .content( objectMapper.writeValueAsString(shortSizeCreateRequestDto) )
        ).andExpect( status().isOk() );

        assertThat(menuRepositorySupport.getMenuWithSizePolicy(americano.getId()).getMenuSizePolicies().size()).isEqualTo(4);
    }

    @Test
    public void testUpdateSizePolicy() throws Exception {
        menuRepository.save(TestMenuEntityFactory.createAmericano());
        Menu americano = menuRepository.findByName("???????????????").get();

        MenuSizePolicyUpdateRequestDto tallSizeUpdateRequestDto = createMenuSizePolicyUpdateRequestDto(4300, 9.2);

        String url = "http://localhost:" + port + "/api/v1/menus/" + americano.getId() + "/size/" + MenuSize.TALL;

        mvc.perform(
                put(url)
                .contentType( MediaType.APPLICATION_JSON )
                .content( objectMapper.writeValueAsString(tallSizeUpdateRequestDto) )
        ).andExpect( status().isOk() );

        MenuSizePolicy updatedTallSizePolicy = menuRepositorySupport.getMenuWithSizePolicy(americano.getId()).getMenuSizePolicy(MenuSize.TALL).get();
        assertThat(updatedTallSizePolicy.getPrice()).isEqualTo(4300);
        assertThat(updatedTallSizePolicy.getCalories()).isEqualTo(9.2);
    }

    @Test
    public void testGetOneMenu() throws Exception {
        menuRepository.save(TestMenuEntityFactory.createAmericano());
        Menu americano = menuRepository.findByName("???????????????").get();

        String url = "http://localhost:" + port + "/api/v1/menus/" + americano.getId();

        mvc.perform(get(url))
        .andExpect( status().isOk() )
        .andExpect( content().contentType("application/json") )
        .andExpect( jsonPath("$.id").value(americano.getId()) )
        .andExpect( jsonPath("$.name").value("???????????????") )
        .andExpect( jsonPath("$.menuSizePolicies[0].menuSize.key").value(MenuSize.TALL.getKey()))
        .andExpect( jsonPath("$.menuSizePolicies[1].menuSize.key").value(MenuSize.GRANDE.getKey()))
        .andExpect( jsonPath("$.menuSizePolicies[2].menuSize.key").value(MenuSize.VENTI.getKey()));

    }

    @Test
    public void testGetMenusWithConditions() throws Exception {
        menuRepository.saveAll( TestMenuEntityFactory.createManyMenu(100 ) );

        String url = "http://localhost:" + port + "/api/v1/menus/";

        String qryStr = "name=???????????????";
        String encodedQryStr = URLEncoder.encode( qryStr, StandardCharsets.UTF_8.toString() );

        MultiValueMap<String, String> reqParams = new LinkedMultiValueMap<>();
        reqParams.add("offset", "0");
        reqParams.add("limit", "100");
        reqParams.add("q", encodedQryStr);

        mvc.perform(get(url).params(reqParams))
            .andExpect( status().isOk() )
            .andExpect( content().contentType("application/json") )
            .andExpect( jsonPath("$", hasSize(25) ) );
    }

    private MenuCreateRequestDto createAmericanoCreateRequestDto() {
        // ??????????????? ??????
        MenuCreateRequestDto americanoCreateRequestDto =
                MenuCreateRequestDto.builder()
                                    .name("???????????????")
                                    .content("???????????? ?????? ???????????????.")
                                    .startDate(LocalDate.now())
                                    .expireDate(LocalDate.of(9999,12,31))
                                    .menuCategory(MenuCategory.DRINK)
                                    .build();

        // ????????? tall, grande, venti ??????
        MenuSizePolicyCreateRequestDto tallSizeCreateRequestDto =
                createMenuSizePolicyCreateRequestDto(MenuSize.TALL, 4600, 10.0);
        americanoCreateRequestDto.addMenuSizePolicy( tallSizeCreateRequestDto );
        americanoCreateRequestDto.addMenuSizePolicy( createMenuSizePolicyCreateRequestDto(MenuSize.GRANDE, 5100, 20.0) );
        americanoCreateRequestDto.addMenuSizePolicy( createMenuSizePolicyCreateRequestDto(MenuSize.VENTI, 5600, 30.0) );

        // ??? ??????????????? ?????? ??????
        tallSizeCreateRequestDto.addMenuIngredientCreateRequestDto(
                MenuIngredientCreateRequestDto.builder()
                        .amountWithUnit("10g")
                        .ingredientName("??????")
                        .build()
        );
        tallSizeCreateRequestDto.addMenuIngredientCreateRequestDto(
                MenuIngredientCreateRequestDto.builder()
                        .amountWithUnit("0.5ml")
                        .ingredientName("??????")
                        .build()
        );

        return americanoCreateRequestDto;
    }

    private MenuUpdateRequestDto createAmericanoUpdateRequestDto(int id, String name, String content, LocalDate startDate, LocalDate expireDate, MenuCategory menuCategory) {
        return MenuUpdateRequestDto.builder()
                        .name(name)
                        .content(content)
                        .startDate(startDate)
                        .expireDate(expireDate)
                        .menuCategory(menuCategory)
                        .build();
    }

    private MenuSizePolicyCreateRequestDto createMenuSizePolicyCreateRequestDto(MenuSize menuSize, int price, double calories) {
        return MenuSizePolicyCreateRequestDto.builder()
                .menuSize(menuSize)
                .price(price)
                .calories(calories)
                .build();
    }

    private MenuSizePolicyUpdateRequestDto createMenuSizePolicyUpdateRequestDto(int price, double calories) {
        return MenuSizePolicyUpdateRequestDto.builder()
                .price(price)
                .calories(calories)
                .build();
    }
}


























