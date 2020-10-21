package com.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.board.domain.BoardVO;
import com.board.domain.Page;
import com.board.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Inject
	private BoardService service;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getList(Model model) throws Exception {

		List<BoardVO> list = null;
		list = service.list();
		model.addAttribute("list", list);
	}
	
	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWirte() throws Exception {
	   
	}
	
	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWirte(BoardVO vo) throws Exception {
	  service.write(vo);	  
	  return "redirect:/board/list";
	}
	
	// 게시물 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("bno") int bno, Model model) throws Exception {
		
		BoardVO vo = service.view(bno);
		model.addAttribute("view", vo);

	}
	
	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("bno") int bno, Model model) throws Exception {
		
		BoardVO vo = service.view(bno);
		model.addAttribute("view", vo);
	}
	
	// 게시물 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo) throws Exception {

		 service.modify(vo);		   
		 return "redirect:/board/view?bno=" + vo.getBno();
	}
	
	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("bno") int bno) throws Exception {
	  
		 service.delete(bno);  	
		 return "redirect:/board/list";
	}
	
	// 게시물 목록 + 페이징 추가
	@RequestMapping(value = "/listpage", method = RequestMethod.GET)
	public void getListPage(Model model) throws Exception {
	  
		 List<BoardVO> list = null; 
		 list = service.list();
		 model.addAttribute("list", list);   
	}
	
	// 게시물 목록 + 페이징 추가
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void getListPage(Model model, @RequestParam("num") int num) throws Exception {
		
//		BoardController에서 작업한 페이징 기능을 Page로 옮기는것
		
		Page page = new Page();
		
		page.setNum(num);
		page.setCount(service.count());  

		List<BoardVO> list = null; 
		list = service.listPage(page.getDisplayPost(), page.getPostNum());

		model.addAttribute("list", list);  
		
		
		model.addAttribute("page", page);
//		model.addAttribute("pageNum", page.getPageNum());
//
//		model.addAttribute("startPageNum", page.getStartPageNum());
//		model.addAttribute("endPageNum", page.getEndPageNum());
//		 
//		model.addAttribute("prev", page.getPrev());
//		model.addAttribute("next", page.getNext()); 
		
		

		model.addAttribute("select", num);
		
		/*
		 * 매개변수로 num은 페이지 번호입니다.
		 * 
		 * 1. 게시물의 총 갯수를 구하고 
		 * 2. 한 페이지당 출력할 게시물 갯수를 정하고(10개) 
		 * 3. 하단에 표시할 페이징 번호의 갯수를 구하고(소수점은 올림) 
		 * 4. 현재 페이지를 기준으로 10개의 데이터를 출력합니다
		 */
//
//		// 게시물 총 갯수
//		int count = service.count();
//
//		// 한 페이지에 출력할 게시물 갯수
//		int postNum = 10;
//
//		// 하단 페이징 번호 ([ 게시물 총 갯수 ÷ 한 페이지에 출력할 갯수 ]의 올림)
//		int pageNum = (int) Math.ceil((double) count / postNum);
//
//		// 출력할 게시물
//		int displayPost = (num - 1) * postNum;
//		
//		// 한번에 표시할 페이징 번호의 갯수
//		int pageNum_cnt = 10;
//
//		// 표시되는 페이지 번호 중 마지막 번호
//		int endPageNum = (int)(Math.ceil((double)num / (double)pageNum_cnt) * pageNum_cnt);
//
//		// 표시되는 페이지 번호 중 첫번째 번호
//		int startPageNum = endPageNum - (pageNum_cnt - 1);
//		/**		
//		한번에 표시할 페이지 번호의 갯수(pageNum_cnt)는 10개로 했습니다.
//
//		현재 페이지 번호가 8번이라면, 한번에 표시할 페이지 번호의 갯수인 10으로 나눕니다. 8 / 10 = 0.8		소수점을 올림처리(ceil)하면 0.8은 1이 됩니다.
//		1을 한번에 표시할 페이지 번호의 갯수인 10을 곱하면 10이 됩니다.
//
//		현재 페이지 번호가 41이라면, 한번에 표시할 페이지 번호의 갯수인 10으로 나눕니다. 41 / 10 = 4.1		소수점을 올림처리하면 4.1은 5가 됩니다
//		5를 한번에 표시할 페이지 번호의 갯수인 10을 곱하면 50이 됩니다.
//
//		즉, 마지막 페이지 번호를 구하는 공식은 다음과 같습니다.
//
//		마지막 페이지 번호 = ((올림)(현재 페이지 번호 / 한번에 표시할 페이지 번호의 갯수)) * 한번에 표시할 페이지 번호의 갯수
//
//		마지막 페이지 번호에서 한번에 표시할 번호의 갯수를 빼면,		마지막 페이지 번호가 10일 경우 0		마지막 페이지 번호가 50일 경우 40
//		여기에 1을 더하면 각 페이지의 시작 번호가 됩니다.
//
//		그러므로 시작 페이지를 구하는 공식은 다음과 같습니다.
//
//		시작 페이지 = 마지막 페이지 번호 - 한번에 표시할 페이지 번호의 갯수 + 1
//		*/
//		
//		// 마지막 번호 재계산
//		int endPageNum_tmp = (int)(Math.ceil((double)count / (double)pageNum_cnt));
//		 
//		if(endPageNum > endPageNum_tmp) {
//		 endPageNum = endPageNum_tmp;
//		}
//		/**
//		마지막 페이지 번호는 다시 한번 더 계산할 필요가 있습니다.
//
//		마지막 페이지 번호를 구하는 공식은 위에서 구한대로
//		[ 마지막 페이지 번호 = ((올림)(현재 페이지 번호 / 한번에 표시할 페이지 번호의 갯수)) * 한번에 표시할 페이지 번호의 갯수 ]입니다.
//
//		만약 게시물 총 갯수가 112개라면, 하단에 표시될 페이지 번호는 1 ~ 10, 11 ~ 12가 되어야합니다.
//		여기서 한번에 표시할 페이지 번호의 갯수가 10이고, 현재 페이지가 11일 경우
//
//		# 1차 계산
//		[ ((올림)(11 / 10)) * 10 => (올림)1.1 * 10 => 2 * 10 = 20 ] 이 됩니다.
//		즉, 13 ~ 20까지 없어야할 페이지 번호가 출력됩니다.
//
//		# 2차 계산
//		여기에서 게시물 총 갯수와 한번에 표시될 페이지 번호의 갯수를 이용해 재계산합니다.
//		[ (올림)112 / 10 => (올림)11.2 => 12 ] 가 됩니다.
//
//		1차 계산한 마지막 페이지 번호는 20이며, 2차로 계산한 마지막 페이지 번호는 12입니다.
//		이 둘을 비교해서 만약 1차 계산이 더 크다면, 2차로 계산한 값을 넣어줍니다.
//
//		다른 예시로, 게시물 총 갯수가 500개이며, 현재 페이지가 5인 경우
//
//		# 1차 계산
//		[ ((올림)(5 / 10)) * 10 => (올림)0.5 * 10 => 1 * 10 = 10 ]
//
//		# 2차 계산
//		[ (올림)500 / 10 => (올림)50 => 50 ]
//
//		1차 계산의 결과가 2차 계산의 결과보다 작으므로 조건문은 false가 되어 실행되지 않으므로, 1차 계산에서 나온 결과인 10이 그대로 나오게 됩니다.
//		*/
//		
//		boolean prev = startPageNum == 1 ? false : true;
//		boolean next = endPageNum * pageNum_cnt >= count ? false : true;
//
//		List<BoardVO> list = null;
//		list = service.listPage(displayPost, postNum);
//		model.addAttribute("list", list);
//		model.addAttribute("pageNum", pageNum);
//		
//		// 시작 및 끝 번호
//		model.addAttribute("startPageNum", startPageNum);
//		model.addAttribute("endPageNum", endPageNum);
//
//		// 이전 및 다음 
//		model.addAttribute("prev", prev);
//		model.addAttribute("next", next);
//		
//		// 현재 페이지
//		model.addAttribute("select", num);
				
		
	}
	
	// 게시물 목록 + 페이징 추가 + 검색
	@RequestMapping(value = "/listPageSearch", method = RequestMethod.GET)
	public void getListPageSearch(Model model, @RequestParam("num") int num,
			@RequestParam(value = "searchType",required = false, defaultValue = "title") String searchType,
			   @RequestParam(value = "keyword",required = false, defaultValue = "") String keyword) throws Exception {
		
//		매개변수부에 @RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword 를 추가하여, 
//		URL을 통해 searchType과 keyword를 받아낼 수 있도록 했습니다.

		Page page = new Page();

		page.setNum(num);
//		page.setCount(service.count());
		page.setCount(service.searchCount(searchType, keyword));
		
		// 검색 타입과 검색어
//		page.setSearchTypeKeyword(searchType, keyword);
		page.setSearchType(searchType);
		page.setKeyword(keyword);

		List<BoardVO> list = null;
		// list = service.listPage(page.getDisplayPost(), page.getPostNum());
		list = service.listPageSearch(page.getDisplayPost(), page.getPostNum(), searchType, keyword);

		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("select", num);
		
//		model.addAttribute("searchType", searchType);
//		model.addAttribute("keyword", keyword);

	}
	
	
}