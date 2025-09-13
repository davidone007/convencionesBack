package co.edu.icesi.planeacionpostgrados.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @Column(name = "id", nullable = false, insertable=false, updatable=false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 25)
    @NotNull
    @Column(name = "documentId", nullable = false, length = 25)
    private String documentId;

}