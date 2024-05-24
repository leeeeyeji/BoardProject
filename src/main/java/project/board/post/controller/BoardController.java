package project.board.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.board.dto.UpdateResponseDto;
import project.board.post.entity.Board;
import project.board.post.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String getPostList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        Iterable<Board> postList = boardService.getPostList(page);

        int totalPages = boardService.getTotalPages();

        model.addAttribute("postList", postList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "index";
    }

    @GetMapping("/view")
    public String getPostDetail(@RequestParam(name = "id") Long id, Model model) {
        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "view";
    }

    @GetMapping("/writeform")
    public String showWriteForm() {
        return "writeForm";
    }

    @PostMapping("/write")
    public String createPost(@RequestParam("name") String name,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("password") String password) {
        Board newBoard = new Board(name, title, content, password);
        boardService.createPost(newBoard);
        return "redirect:/list";
    }

    @GetMapping("/deleteform")
    public String showDeleteForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "deleteForm";
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam("id") Long id,
                             @RequestParam("password") String password,
                             Model model) {
        boolean success = boardService.deletePost(id, password);

        if (success) {
            return "redirect:/list";
        } else {
            model.addAttribute("error", "비밀번호가 틀렸습니다. 다시 확인해주세요!");
            model.addAttribute("id", id);
            return "deleteForm";
        }
    }

    @GetMapping("/updateform")
    public String showUpdateForm(@RequestParam("id") Long id,
                                 Model model) {
        model.addAttribute("id", id);
        UpdateResponseDto postInfo = boardService.getPostInfo(id);

        model.addAttribute("post", postInfo);

        return "updateForm";
    }

    // @PutMapping :전체수정 @PatchMapping : 부분수정
    @PostMapping("/update")
    public String updatePost(@RequestParam("id") Long id,
                             @RequestParam("name") String name,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("password") String password,
                             Model model){
        boolean success = boardService.updatePost(id, name, title, content, password);
        if (success){
            return "redirect:/view?id="+id;
        } else {
            model.addAttribute("error", "비밀번호가 틀렸습니다. 다시 확인해주세요!");
            model.addAttribute("id", id);
            model.addAttribute("post", new UpdateResponseDto(name, title, content));
            return "updateForm";
        }
    }
}
