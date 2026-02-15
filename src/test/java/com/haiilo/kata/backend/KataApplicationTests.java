package com.haiilo.kata.backend;

import com.haiilo.kata.backend.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class KataApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() throws Exception {
		var productsJson = UtilsTest.loadRequest("request/v1/products_list_request.json");
		var products = objectMapper.readTree(productsJson);

		if (products.isArray()) {
			for (var product : products) {
				mockMvc.perform(post("/api/kata/v1/product")
								.contentType(MediaType.APPLICATION_JSON)
								.content(product.toString()))
						.andExpect(status().isOk());
			}
		}
	}

	@Test
	void contextLoads() {
	}

	@Test
	void addProduct_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/new_product_request.json");

		mockMvc.perform(post("/api/kata/v1/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void updateProduct_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/update_product_request.json");

		mockMvc.perform(put("/api/kata/v1/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void getProducts_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/product/products"))
				.andExpect(status().isOk());
	}

	@Test
	void getProduct_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/product/1"))
				.andExpect(status().isOk());
	}

	@Test
	void addOffer_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/new_offer_request.json");

		mockMvc.perform(post("/api/kata/v1/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void updateOffer_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/update_offer_request.json");

		mockMvc.perform(put("/api/kata/v1/offer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void getOffers_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/offer/offers"))
				.andExpect(status().isOk());
	}

	@Test
	void getOffer_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/offer/1"))
				.andExpect(status().isOk());
	}

	@Test
	void addCart_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/new_cart_request.json");

		mockMvc.perform(post("/api/kata/v1/cart")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void updateCart_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/update_cart_request.json");

		mockMvc.perform(put("/api/kata/v1/cart")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void getCurrentCart_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/cart/current"))
				.andExpect(status().isOk());
	}

	@Test
	void getCart_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/cart/1"))
				.andExpect(status().isOk());
	}

	@Test
	void addCartItem_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/new_cart_item_request.json");

		mockMvc.perform(post("/api/kata/v1/cart/item")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void updateCartItem_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/update_cart_item_request.json");

		mockMvc.perform(put("/api/kata/v1/cart/item")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void getCartItems_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/cart/items"))
				.andExpect(status().isOk());
	}

	@Test
	void executeCheckout_Success() throws Exception {
		var request = UtilsTest.loadRequest("request/v1/checkout_request.json");

		mockMvc.perform(post("/api/kata/v1/checkout")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				)
				.andExpect(status().isOk());
	}

	@Test
	void getReceipt_Success() throws Exception {
		mockMvc.perform(get("/api/kata/v1/receipt?cartId=1"))
				.andExpect(status().isOk());
	}
}
