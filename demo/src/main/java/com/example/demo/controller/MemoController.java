package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dataaccess.MemoRepository;
import com.example.demo.model.Memo;
import com.example.demo.model.MemoEntity;
import com.example.demo.service.MemoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemoController {

	@Autowired
	private MemoRepository memoRepository;
	@Autowired
	private MemoService memoService;

	// ホーム画面
	@RequestMapping("/")
	public String index(HttpSession session, Model model) {
		clearSession(session);
		createToken(session, model);
		return "memoHome";
	}

	// 新規作成画面へ遷移
	@GetMapping("/toMemoCreate")
	public String toMemoCreate(@ModelAttribute Memo memo, HttpSession session, Model model) {
		MemoEntity memoEntity = new MemoEntity();
		session.setAttribute("memo", memoEntity);
		createToken(session, model);
		return "memoCreate";
	}

	// 戻る（ホーム画面）
	@GetMapping("/toHome")
	public String toHome(Model model, HttpSession session) {
		createToken(session, model);
		return "memoHome";
	}

	// 表示
	@PostMapping("/select")
	public String selectMemo(@RequestParam("id") int id, HttpSession session, MemoEntity memoEntity, Model model) {
		// 検索
		memoEntity = memoRepository.findById(id);

		session.setAttribute("memo", memoEntity);
		createToken(session, model);
		return "memoEdit";
	}

	// 登録
	@PostMapping("/createMemo")
	public String createMemo(MemoEntity memoEntity, Memo memo, HttpSession session, Model model,
			@RequestParam String token) {

		if (!memoService.chkToken(session, token)) {
			model.addAttribute("message", "不正な操作です。");
			createToken(session, model);
			return "memoHome";
		}

		setMemo(session, memo);

		String chkTitle = memoService.chkTitle(memo.getTitle());
		if (!chkTitle.isEmpty()) {
			model.addAttribute("message", chkTitle);
			createToken(session, model);
			return "memoCreate";
		}

		String chkContent = memoService.chkContent(memo.getContent());
		if (!chkContent.isEmpty()) {
			model.addAttribute("message", chkContent);
			createToken(session, model);
			return "memoCreate";
		}

		memoEntity.setTitle(memo.getTitle());
		memoEntity.setContent(memo.getContent());
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		memoEntity.setCreate_time(currentTime);

		// 登録
		memoRepository.save(memoEntity);

		clearSession(session);
		model.addAttribute("message", "登録しました。");
		createToken(session, model);
		return "memoHome";
	}

	// 更新
	@PostMapping("/update")
	public String updateMemo(@RequestParam("id") int id, HttpSession session, MemoEntity memoEntity, Memo memo,
			Model model, @RequestParam String token) {
		if (!memoService.chkToken(session, token)) {
			model.addAttribute("message", "不正な操作です。");
			createToken(session, model);
			return "memoHome";
		}

		setMemo(session, memo);

		String chkTitle = memoService.chkTitle(memo.getTitle());
		if (!chkTitle.isEmpty()) {
			model.addAttribute("message", chkTitle);
			createToken(session, model);
			return "memoEdit";
		}

		String chkContent = memoService.chkContent(memo.getContent());
		if (!chkContent.isEmpty()) {
			model.addAttribute("message", chkContent);
			createToken(session, model);
			return "memoEdit";
		}

		memoEntity = memoRepository.findById(id);
		memoEntity.setTitle(memo.getTitle());
		memoEntity.setContent(memo.getContent());
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		memoEntity.setCreate_time(currentTime);

		memoRepository.save(memoEntity);

		clearSession(session);
		model.addAttribute("message", "保存しました。");

		createToken(session, model);
		return "memoHome";
	}

	// 削除
	@PostMapping("/delete")
	public String deleteMemo(@RequestParam("id") int id, Model model, HttpSession session, @RequestParam String token) {

		if (!memoService.chkToken(session, token)) {
			model.addAttribute("message", "不正な操作です。");
			createToken(session, model);
			return "memoHome";
		}

		memoRepository.deleteById(id);

		List<MemoEntity> memoList = memoRepository.findAll();
		String sortKey = (String) session.getAttribute("sortKey");
		String sortDirection = (String) session.getAttribute("sortDirection");
		memoList = memoService.sort(memoList, sortKey, sortDirection);
		String keyword = (String) session.getAttribute("keyword");
		memoList = memoService.search(memoList, keyword);

		session.setAttribute("sortMemoList", memoList);
		model.addAttribute("message", "削除しました。");

		createToken(session, model);
		return "memoHome";
	}

	// ソート
	@GetMapping("/sort")
	public String sortMemoList(@RequestParam("sortKey") String sortKey,
			@RequestParam("sortDirection") String sortDirection, Model model, HttpSession session,
			@RequestParam String token) {
		// Tokenのチェック
		if (!memoService.chkToken(session, token)) {
			createToken(session, model);
			return "memoHome";
		}

		@SuppressWarnings("unchecked")
		List<MemoEntity> memoList = (List<MemoEntity>) session.getAttribute("sortMemoList");

		session.setAttribute("sortKey", sortKey);
		session.setAttribute("sortDirection", sortDirection);
		session.setAttribute("sortMemoList", memoService.sort(memoList, sortKey, sortDirection));

		createToken(session, model);
		return "memoHome";
	}

	// 検索
	@PostMapping("/search")
	public String searchMemoList(Model model, HttpSession session, @RequestParam String token,
			@RequestParam String keyword) {
		// Tokenのチェック
		if (!memoService.chkToken(session, token)) {
			model.addAttribute("message", "不正な操作です。");
			createToken(session, model);
			return "memoHome";
		}

		@SuppressWarnings("unchecked")
		List<MemoEntity> memoList = (List<MemoEntity>) session.getAttribute("sortMemoList");
		session.setAttribute("sortMemoList", memoService.search(memoList, keyword));
		session.setAttribute("keyword", keyword);

		createToken(session, model);
		return "memoHome";
	}

	// 入力内容の設定
	public MemoEntity setMemo(HttpSession session, Memo memo) {
		MemoEntity memoEntity = (MemoEntity) session.getAttribute("memo");
		memoEntity.setTitle(memo.getTitle());
		memoEntity.setContent(memo.getContent());

		return memoEntity;
	}

	// セッションの初期化
	public void clearSession(HttpSession session) {
		// keyの初期化
		session.setAttribute("sortKey", "id");
		session.setAttribute("sortDirection", "asc");
		session.setAttribute("keyword", "");
		// 一覧の再設定
		List<MemoEntity> memoList = memoService.sort(memoRepository.findAll(), "id", "asc");
		session.setAttribute("sortMemoList", memoList);
	}

	// Tokenの作成
	public void createToken(HttpSession session, Model model) {
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		model.addAttribute("token", token);
	}

}