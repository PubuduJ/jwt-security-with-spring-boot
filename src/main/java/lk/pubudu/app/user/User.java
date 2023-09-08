package lk.pubudu.app.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String lastName;
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    private String email;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;
}
