package com.haiilo.kata.backend;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.service.ReceiptService;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = {"/cleanup.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class KataApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JsonTestUtils jsonTestUtils;

	@MockitoBean
	private ReceiptService receiptService;

	@Test
	void contextLoads() {
	}

	@Test
	void addProduct_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/new_product_request.json");

		mockMvc.perform(post("/api/kata/v1/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void updateProduct_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/update_product_request.json");

		mockMvc.perform(put("/api/kata/v1/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void getProducts_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/product/products"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void getProduct_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/product/1"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void addOffer_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/new_offer_request.json");

		mockMvc.perform(post("/api/kata/v1/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void updateOffer_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/update_offer_request.json");

		mockMvc.perform(put("/api/kata/v1/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void getOffer_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/offer/1"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void getCurrentCart_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/cart/current"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void updateCart_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/update_cart_request.json");

		mockMvc.perform(put("/api/kata/v1/cart")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void getCart_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/cart/1"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void addCartItem_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/new_cart_item_request.json");

		mockMvc.perform(post("/api/kata/v1/cart/item")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void updateCartItem_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/update_cart_item_request.json");

		mockMvc.perform(put("/api/kata/v1/cart/item")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void addProductOffer_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/new_product_offer_request.json");

		mockMvc.perform(post("/api/kata/v1/product/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void updateProductOffer_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/dto/v1/fixed_amount_product_offer_dto.json");

		mockMvc.perform(put("/api/kata/v1/product/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void executeCheckout_Success() throws Exception {
		var request = jsonTestUtils.loadRequest("model/request/v1/checkout_request.json");
		var receiptDto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);

		when(receiptService.addReceipt(any())).thenReturn(receiptDto);

		mockMvc.perform(post("/api/kata/v1/checkout")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().is2xxSuccessful());
	}
}
