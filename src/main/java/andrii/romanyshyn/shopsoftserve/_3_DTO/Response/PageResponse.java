package andrii.romanyshyn.shopsoftserve._3_DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponse<T> {
    private Integer totalPages;
    private Long totalElement;
    private List<T> data;
}
