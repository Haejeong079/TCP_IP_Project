package com.example.Pratice.controller;


import com.example.Pratice.dto.BoardDto;
import com.example.Pratice.dto.CommentDto;


import com.example.Pratice.entity.DashBoard;
import com.example.Pratice.entity.Member;
import com.example.Pratice.repository.BoardRepository;
import com.example.Pratice.service.BoardService;
import com.example.Pratice.service.CommentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class DashboardController {


    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BoardService boardService; // BoardService 인스턴스 주입

    // 대시보드 메인 페이지
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Member user = (Member) session.getAttribute("user");
        //model.addAttribute("user", user.getNickname());

        // 모든 dashboard를 들고온다 !
        List<DashBoard> dashboardEntityList = boardRepository.findAll();

        model.addAttribute("boardlist", dashboardEntityList);
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("user", user.getNickname());
            log.info(user.getNickname() + " 님 께서 로그인하셨습니다.");


            //model.addAttribute("boardlist", dashboardEntityList);

        } else {
            model.addAttribute("isLoggedIn", false);
        }

        return "dashboard/dashboard";
    }

    // 대시보드 생성 페이지
    @GetMapping("dashboard/new")
    public String newDashboard(HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        Member user = (Member) session.getAttribute("user");

        // 사용자 정보를 모델에 추가
        model.addAttribute("isLoggedIn", user != null);
        model.addAttribute("user", user != null ? user.getNickname() : null);

        return "dashboard/dashboard_New";
    }

    @PostMapping("/dashboard/create")
    public String newDashboardForm(HttpSession session, BoardDto Bdto) {
        Member user = (Member) session.getAttribute("user");
        Bdto.setNickname(user.getNickname());
        Bdto.setUploadDate(LocalDate.now());

        log.info(Bdto.toString());

        DashBoard dashBoard = Bdto.toEntity();
        log.info(dashBoard.toString());

        // 중복 검사를 하지 않고 바로 저장
        DashBoard saved = boardRepository.save(dashBoard);
        log.info(saved.toString());


        return "redirect:/dashboard/" + saved.getNickname() + "/" + saved.getId();
    }


    // 대시보드 생성후 사용자 닉네임값 / 아이디(게시물 순서) 로 지정한 주소

    @GetMapping("/dashboard/{nickname}/{id}")
    public String showBoard(@PathVariable String nickname,
                            @PathVariable Long id, HttpSession session, Model model) {
        Member user = (Member) session.getAttribute("user");
        DashBoard dashBoardEntity = boardRepository.findByIdAndNickname(id, nickname);

        boolean isAuthor = user != null && user.getNickname().equals(nickname);
        // 조회수 업데이트
        if (dashBoardEntity != null) {
            dashBoardEntity.setViewCount(dashBoardEntity.getViewCount() + 1);
            boardRepository.save(dashBoardEntity); // 조회수 변경을 데이터베이스에 저장
        }

        List<CommentDto> comments = commentService.comments(id); // 댓글 리스트 가져오기

        model.addAttribute("viewCount", dashBoardEntity.getViewCount());
        model.addAttribute("commentCount", dashBoardEntity.getCommentCount());
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("user", user != null ? user.getNickname() : null);
        model.addAttribute("boardlist", dashBoardEntity);
        model.addAttribute("isAuthor", isAuthor);
        model.addAttribute("comments", comments); // 여기서 comments는 CommentDto 객체 목록


        return "dashboard/dashboard_Show";
    }



    // 게시물 update (수정) controller
    @GetMapping("/dashboard/{nickname}/{id}/edit")
    public String edit(@PathVariable String nickname, @PathVariable Long id, Model model,HttpSession session) {
        Member user = (Member) session.getAttribute("user");
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("user", user.getNickname());

        // 수정할 게시글 데이터 가져오기
        DashBoard dashBoard = boardRepository.findByNicknameAndId(nickname, id);

        if (dashBoard == null) {
            // 게시글이 존재하지 않을 경우 처리
            return "redirect:/"; // 또는 적절한 에러 페이지로 리다이렉트
        }

        model.addAttribute("editDashboard", dashBoard);

        return "dashboard/dashboard_Edit";
    }

    @PostMapping("dashboard/update")
    public String update(BoardDto boardDto, HttpSession session, Model model) {
        Member user = (Member) session.getAttribute("user");
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("user", user.getNickname());

        // 1: 기존 엔티티를 가져온다.
        Optional<DashBoard> optionalBoardEntity = boardRepository.findById(boardDto.getId());

        if (optionalBoardEntity.isPresent()) {
            DashBoard existingBoard = optionalBoardEntity.get();

            // 2: 엔티티의 제목과 내용을 업데이트한다.
            existingBoard.setTitle(boardDto.getTitle());
            existingBoard.setContent(boardDto.getContent());

            // 3: DB에 업데이트된 엔티티를 저장한다.
            boardRepository.save(existingBoard);
        }

        return "redirect:/dashboard/" + boardDto.getNickname() + "/" + boardDto.getId();
    }

    @GetMapping("/dashboard/{nickname}/{id}/delete")
    public String Delete(HttpSession session, @PathVariable Long id,
                         @PathVariable String nickname, RedirectAttributes rttr,
                         Model model) {
        Member user = (Member) session.getAttribute("user");
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("user", user.getNickname());

        // 서비스 레이어의 삭제 메소드 호출
        boardService.deleteDashBoard(id, nickname);

        rttr.addFlashAttribute("msg", "삭제가 완료되었습니다!");

        return "redirect:/dashboard";
    }






}