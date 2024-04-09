package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.MemoController;
import com.example.demo.dataaccess.MemoRepository;
import com.example.demo.model.MemoEntity;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private MemoController mc;

	@Mock
	private MemoRepository mr;

	private MockMvc mockMvc;

	private AutoCloseable closeable;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() {
		// 各テストの実行前にモックオブジェクトを初期化する
		MockitoAnnotations.initMocks(this);

		closeable = MockitoAnnotations.openMocks(mc);
		mockMvc = MockMvcBuilders.standaloneSetup(mc).build();
	}

	@AfterEach
	void closeMocks() throws Exception {
		closeable.close();
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test1() throws Exception {
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andReturn().equals("memoHome");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test2() throws Exception {
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.get("/toMemoCreate"))
				.andReturn().equals("memoCreate");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test3() throws Exception {
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.get("/toUpdate"))
				.andReturn().equals("memoEdit");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test4() throws Exception {
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.get("/toHome"))
				.andReturn().equals("memoHome");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test5() throws Exception {
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.get("/toDetail"))
				.andReturn().equals("memoDetail");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test6() throws Exception {
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.get("/select"))
				.andReturn().equals("memoDetail");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test7() throws Exception {
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.get("/select"))
				.andReturn().equals("memoDetail");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test8() throws Exception {
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.get("/createMemo"))
				.andReturn().equals("memoHome");
	}

}
