package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.MemoModel;
import com.example.demo.service.MemoService;

@Controller
public class MemoController {
	@Autowired
	private MemoService memoService;

	@GetMapping("/newMemo")
	public String newMemo(@ModelAttribute MemoModel memoModel) {
		return "newMemo";
	}

	@GetMapping("/toIndex")
	public String toIndex() {
		return "index";
	}

	@PostMapping("/createMemo")
	public String createMemo(MemoModel memoModel) {

		memoService.createMemo(memoModel.getTitle(), memoModel.getContent());
		// int key = 1;
		// System.out.println(memoModel.getTitle());
		// System.out.println(memoModel.getContent());
		// System.out.println(memoService.getMemo(key).toString());
		return "index";
	}

	// public String greeting(@RequestParam(name="name", required=false,
	// defaultValue="World") String name, Model model) {
	// model.addAttribute("name", name);
	// return "memo";
	// }
}