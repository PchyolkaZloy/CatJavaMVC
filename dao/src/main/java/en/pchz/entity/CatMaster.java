package en.pchz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cat_master")
public class CatMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(
            mappedBy = "catMaster",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Cat> cats;

    @Override
    public String toString() {
        return "CatMaster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate.toString() +
                '}';
    }
}
