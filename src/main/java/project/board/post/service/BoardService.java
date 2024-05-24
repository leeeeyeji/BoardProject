package project.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.board.dto.UpdateResponseDto;
import project.board.post.entity.Board;
import project.board.post.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    public List<Board> getPostList(int page) {
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        Iterable<Board> allPosts = boardRepository.findAll();

        List<Board> sortedPosts = StreamSupport.stream(allPosts.spliterator(), false)
                .sorted((b1, b2) -> b2.getCreatedAt().compareTo(b1.getCreatedAt()))
                .collect(Collectors.toList());

        int totalItems = sortedPosts.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        // 페이지 번호가 유효하지 않으면 빈 리스트 반환
        if (page < 1 || page > totalPages) {
            return List.of();
        }

        int startIndex = (page - 1) * pageSize;

        return sortedPosts.stream()
                .skip(startIndex)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public int getTotalPages() {
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        Iterable<Board> allPosts = boardRepository.findAll();
        int totalItems = (int) StreamSupport.stream(allPosts.spliterator(), false).count();
        return (int) Math.ceil((double) totalItems / pageSize);
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }

    public void createPost(Board newBoard) {
        boardRepository.save(newBoard);
    }

    public boolean deletePost(Long id, String password) {
        Board findPost = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        if (!password.equals(findPost.getPassword())) {
            return false;
        }
        boardRepository.deleteById(id);
        return true;
    }

    public UpdateResponseDto getPostInfo(Long id){
        Board findPost = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        return new UpdateResponseDto(findPost.getName(),findPost.getTitle(),findPost.getContent());
    }

    public boolean updatePost(Long id, String name, String title,String content,String password){
        Board findPost = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
        if (!password.equals(findPost.getPassword())) {
            return false;
        }
        findPost.setName(name);
        findPost.setTitle(title);
        findPost.setContent(content);
        findPost.setUpdatedAt(LocalDateTime.now());

        boardRepository.save(findPost);

        return true;
    }
}
