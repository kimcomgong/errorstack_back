package kimce.kyj.errorstack.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Stack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tag;
    private String code;
    private String body;

    private String createdBy;
    private String createdAt;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();
}