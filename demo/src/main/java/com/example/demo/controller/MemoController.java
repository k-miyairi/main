package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import com.example.demo.model.Memo;
import com.example.demo.model.MemoEntity;
import com.example.demo.service.MemoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemoController {
	@Autowired
	private MemoService memoService;
    //private MemoRepository memoRepository;

	// ホーム画面
	@RequestMapping("/")
	String index(HttpSession session) {
		List<MemoEntity> memolist = memoService.getAllMemo();
		session.setAttribute("memolist", memolist);
		return "memoHome";
	}

	// 新規作成画面へ遷移
	@GetMapping("/toMemoCreate")
	public String toMemoCreate(@ModelAttribute Memo memo) {
		return "memoCreate";
	}

	// 登録
	@PostMapping("/createMemo")
	public String createMemo(Memo memo, HttpSession session) {

		if ((memo.getTitle()).isEmpty()) {
			return "memoCreate";
		} else {
			memoService.createMemo(memo.getTitle(), memo.getContent());
			session.setAttribute("memolist", memoService.getAllMemo());
		}
		return "memoHome";
	}

	// 参照
	@PostMapping("/select")
	public String selectMemo(@RequestParam("id") int id, HttpSession session) {
		MemoEntity memoEntity = memoService.selectMemo(id);
		session.setAttribute("memo", memoEntity);
		return "memoDetail";
	}

	// 削除
	@PostMapping("/delete")
	public String deleteMemo(@RequestParam("id") int id, HttpSession session) {
		memoService.deleteMemo(id);
		session.setAttribute("memolist", memoService.getAllMemo());
		return "memoHome";
	}

	// 戻る
	@GetMapping("/toHome")
	public String toHome() {
		// model.addAttribute("memolist", memoService.getAllMemo());
		return "memoHome";
	}

}