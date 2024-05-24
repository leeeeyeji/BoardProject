package project.board.post.repository;

import org.springframework.data.repository.CrudRepository;
import project.board.post.entity.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
}
