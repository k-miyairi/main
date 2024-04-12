package com.example.demo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.MemoController;
import com.example.demo.dataaccess.MemoRepository;
import com.example.demo.model.MemoEntity;
import com.example.demo.service.MemoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DemoApplicationTests {

	@InjectMocks
	private MemoController memoController;
	@Mock
	private MemoRepository memoRepository;
	@Mock
	private MemoService memoService;

	private AutoCloseable closeable;
	private MockMvc mvc;

	@BeforeEach
	public void openMocks() {
		closeable = MockitoAnnotations.openMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(memoController).build();
	}

	@AfterEach
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testIndex() {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testToMemoCreate() {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/toMemoCreate"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoCreate"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testToHome() {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/toHome"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSelectMemo() {
		// Mock
		Mockito.when(memoRepository.findById(Mockito.anyInt())).thenReturn(memoEntity());

		try {
			mvc.perform(MockMvcRequestBuilders.post("/select")
					.param("id", "1"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.request().sessionAttribute("memo", memoEntity()))
					.andExpect(MockMvcResultMatchers.request().sessionAttribute("memo", memoEntity()))
					.andExpect(MockMvcResultMatchers.view().name("memoEdit"));

			Mockito.verify(memoRepository, Mockito.times(1)).findById(Mockito.anyInt());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateMemo() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);
		Mockito.when(memoService.chkTitle(Mockito.anyString())).thenReturn("");
		Mockito.when(memoService.chkContent(Mockito.anyString())).thenReturn("");
		Mockito.when(memoRepository.save(Mockito.any())).thenReturn(memoEntity());

		String formData = "title=title&content=content&token=ok";

		try {
			mvc.perform(MockMvcRequestBuilders.post("/createMemo")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(formData)
					.session(setSession()))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));

			Mockito.verify(memoRepository).save(Mockito.any());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateMemo_Error_Token() {
		String formData = "title=title&content=content&token=ng";
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(false);

		try {
			mvc.perform(MockMvcRequestBuilders.post("/createMemo")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.session(setSession())
					.content(formData))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateMemo_Error_title() {
		String formData = "title=&content=content&token=ok";
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);
		Mockito.when(memoService.chkTitle(Mockito.anyString())).thenReturn("error");

		try {
			mvc.perform(MockMvcRequestBuilders.post("/createMemo")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.session(setSession())
					.content(formData))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoCreate"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateMemo_Error_content() {
		String formData = "title=title&content=content&token=ok";
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);
		Mockito.when(memoService.chkTitle(Mockito.anyString())).thenReturn("");
		Mockito.when(memoService.chkContent(Mockito.anyString())).thenReturn("error");

		try {
			mvc.perform(MockMvcRequestBuilders.post("/createMemo")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.session(setSession())
					.content(formData))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoCreate"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateMemo() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);
		Mockito.when(memoRepository.save(Mockito.any())).thenReturn(memoEntity());
		Mockito.when(memoService.chkTitle(Mockito.anyString())).thenReturn("");
		Mockito.when(memoService.chkContent(Mockito.anyString())).thenReturn("");
		Mockito.when(memoRepository.findById(Mockito.anyInt())).thenReturn(memoEntity());

		String formData = "title=title&content=content&token=ok";

		try {
			mvc.perform(MockMvcRequestBuilders.post("/update")
					.param("id", "1")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.session(setSession())
					.content(formData))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));

			Mockito.verify(memoRepository, Mockito.times(1)).findById(Mockito.anyInt());
			Mockito.verify(memoRepository).save(Mockito.any());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateMemo_Error_Token() {
		String formData = "title=title&content=content&token=ng";
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(false);

		try {
			mvc.perform(MockMvcRequestBuilders.post("/update")
					.param("id", "1")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.session(setSession())
					.content(formData))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateMemo_Error_title() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);
		Mockito.when(memoService.chkTitle(Mockito.anyString())).thenReturn("error");

		String formData = "title=&content=content&token=ok";

		try {
			mvc.perform(MockMvcRequestBuilders.post("/update")
					.param("id", "1")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.session(setSession())
					.content(formData))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoEdit"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateMemo_Error_content() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);
		Mockito.when(memoService.chkTitle(Mockito.anyString())).thenReturn("");
		Mockito.when(memoService.chkContent(Mockito.anyString())).thenReturn("error");

		String formData = "title=&content=content&token=ok";

		try {
			mvc.perform(MockMvcRequestBuilders.post("/update")
					.param("id", "1")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.session(setSession())
					.content(formData))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoEdit"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteMemo() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);
		Mockito.doNothing().when(memoRepository).deleteById(Mockito.anyInt());

		try {
			mvc.perform(MockMvcRequestBuilders.post("/delete")
					.param("id", "1")
					.param("token", "ok"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));

			Mockito.verify(memoRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteMemo_Error_Token() {
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(false);

		try {
			mvc.perform(MockMvcRequestBuilders.post("/delete")
					.param("id", "1")
					.param("token", "ng"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSortMemoList() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);

		try {
			mvc.perform(MockMvcRequestBuilders.get("/sort")
					.param("sortKey", "id")
					.param("sortDirection", "asc")
					.param("token", "ok"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSortMemoList_Error_Token() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(false);

		try {
			mvc.perform(MockMvcRequestBuilders.get("/sort")
					.param("sortKey", "id")
					.param("sortDirection", "asc")
					.param("token", "ng"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchtMemoList() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(true);

		try {
			mvc.perform(MockMvcRequestBuilders.post("/search")
					.param("keyword", "keyword")
					.param("token", "ok"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchMemoList_Error_Token() {
		// Mock
		Mockito.when(memoService.chkToken(Mockito.any(), Mockito.anyString())).thenReturn(false);

		try {
			mvc.perform(MockMvcRequestBuilders.post("/search")
					.param("keyword", "keyword")
					.param("token", "ng"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("memoHome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MemoEntity memoEntity() {
		MemoEntity memo = new MemoEntity();
		memo.setId(1);
		memo.setTitle("title");
		memo.setContent("content");
		Timestamp ts = Timestamp.valueOf("2024-04-10 09:00:00.01");
		memo.setCreate_time(ts);
		return memo;
	}

	public List<MemoEntity> memoList() {
		List<MemoEntity> memoList = new ArrayList<>();

		MemoEntity memo1 = new MemoEntity();
		memo1.setId(1);
		memo1.setTitle("title1");
		memo1.setContent("content");
		memo1.setCreate_time(Timestamp.valueOf("2024-04-10 09:00:00.01"));
		memoList.add(memo1);

		MemoEntity memo2 = new MemoEntity();
		memo2.setId(2);
		memo2.setTitle("title2");
		memo2.setContent("content");
		memo2.setCreate_time(Timestamp.valueOf("2024-04-11 09:00:00.01"));
		memoList.add(memo2);

		return memoList;
	}

	public MockHttpSession setSession() {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("memo", memoEntity());
		session.setAttribute("sortMemoList", memoList());
		return session;
	}
}