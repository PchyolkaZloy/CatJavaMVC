package en.pchz.entity;

import en.pchz.common.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;
    @OneToOne
    @JoinTable(
            name = "user_cat_master",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cat_master_id", referencedColumnName = "id")
    )
    private CatMaster catMaster;
}
