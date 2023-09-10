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
public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 7539048554880289053L;
    private String token;
}
