package en.pchz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public CatMaster(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.cats = new HashSet<>();
    }

    public CatMaster(Integer id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.cats = new HashSet<>();
    }


    @Override
    public String toString() {
        return "CatMaster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate.toString() +
                '}';
    }
}
