package lx.edu.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lx.edu.springmvc.dao.AddrBookDAO;
import lx.edu.springmvc.vo.AddrBookVO;

@Controller
public class AddrBookController {
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	AddrBookDAO dao;
	
	@RequestMapping("/addrbook_form.do")
	public String form() {
		return "addrbook_form"; // jsp file name
	}
	
	
	/*
	 * @RequestMapping("/addrbook_list.do") public String list(HttpServletRequest req) throws Exception { 
	 * List<AddrBookVO> list = dao.getDBList(); 
	 * // list를request에 넣는다. 
	 * req.setAttribute("data", list); 
	 * return "addrbook_list"; }
	 */
	
	@RequestMapping("/addrbook_list.do")
	public String list(HttpSession session, HttpServletRequest req) throws Exception {
		
		if(session.getAttribute("userId") == null ) {
			return "login";
		}
		
		List<AddrBookVO> list = dao.getDBList();
		return "addrbook_list";
	}
	
	@RequestMapping("/insert.do")
	public String insert(AddrBookVO vo) throws Exception {
		// 클라이언트에 입력한 값들을 받아와
		System.out.println(vo);
		// DB에 추가 --> 여기서 비지니스 로직을 호출해서 DB에 때려넣음
		dao.insertDB(vo);
		// 다시 List를 보여줘야해
		return "redirect:addrbook_list.do";
	}
	
	@RequestMapping("/addrbook_edit_form.do")
	public String moveToEditForm(@RequestParam("abId") int abId, HttpServletRequest req) throws Exception {
		AddrBookVO vo = dao.selectById(abId);
		req.setAttribute("ab", vo);
		System.out.println(vo);
		return "addrbook_edit_form";
	}
	
	@RequestMapping("/edit.do")
	public String edit(AddrBookVO vo) throws Exception {
		dao.updateDB(vo);
		return "redirect:addrbook_list.do";
	}
}
