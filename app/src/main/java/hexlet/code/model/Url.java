package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Url {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastCheck;
    private Integer lastStatusCode;

    public Url(String name) {
        this.name = name;
    }

    public Url(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
