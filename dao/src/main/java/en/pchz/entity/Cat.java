package en.pchz.entity;

import en.pchz.common.CatColor;
import en.pchz.common.CatColorType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cat")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private String breed;

    @Enumerated(EnumType.STRING)
    @Type(value = CatColorType.class)
    @ColumnTransformer(read = "UPPER(color)", write = "LOWER(?)")
    private CatColor color;

    @ManyToOne
    @JoinColumn(name = "cat_master_id")
    private CatMaster catMaster;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cat_cat_friends",
            joinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    private Set<Cat> catFriends;

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate.toString() +
                ", breed='" + breed + '\'' +
                ", color=" + color.toString() +
                '}';
    }
}
