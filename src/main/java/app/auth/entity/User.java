package app.auth.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String[] roles;

    @Column(name = "isblocked")
    private boolean isBlocked;

}
