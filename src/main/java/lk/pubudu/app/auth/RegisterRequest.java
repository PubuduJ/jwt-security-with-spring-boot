package lk.pubudu.app.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -3978548037673187009L;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
