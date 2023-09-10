package lk.pubudu.app.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Serial
    private static final long serialVersionUID = -1298330556637687231L;
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
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Should return list of roles.
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // Should return user entity email as the username.
        return email;
    }

    @Override
    public String getPassword() {
        // If entity has a field password, this method will not be overridden.
        // Manually overridden.
        // Should return user entity password.
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Not expired by returning true.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Not locked by returning true.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Not expired by returning true.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // enable by returning true.
        return true;
    }
}
