package project.board.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table("board")
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    private Long id;

    private String name;
    private String title;
    private String content;
    private String password;

    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime updatedAt=LocalDateTime.now();

    public Board(String name, String title, String content, String password) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.password = password;
    }
}
