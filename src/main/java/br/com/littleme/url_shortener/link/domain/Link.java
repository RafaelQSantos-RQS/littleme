package br.com.littleme.url_shortener.link.domain;

import br.com.littleme.url_shortener.common.domain.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "links")
@Table(name = "links")
public class Link extends Auditable {
    @Id
    private UUID id;

    @Column(name = "full_url", nullable = false)
    private String fullUrl;

    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

}
