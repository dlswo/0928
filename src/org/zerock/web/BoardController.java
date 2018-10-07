package org.zerock.web;

import org.zerock.anno.GetMapping;
import org.zerock.anno.RequestMapping;
import org.zerock.anno.RequestParam;

@RequestMapping("/board/")
public class BoardController {
	
	@GetMapping("/board/register.do")
	public void add() {
		
	}
	
	@GetMapping("/board/list.do")
	public void myList(@RequestParam("page") String pageStr) {
		
		System.out.println("list called...");
		
	}

}
