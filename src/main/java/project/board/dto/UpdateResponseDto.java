package project.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data //dto에만 써야함 getter,setter,toString ... 만들어줌
@AllArgsConstructor
public class UpdateResponseDto {
    private String name;
    private String title;
    private String content;
}
