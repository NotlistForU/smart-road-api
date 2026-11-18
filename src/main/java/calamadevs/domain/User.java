package calamadevs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "community_sharing_enabled", nullable = false, columnDefinition = "boolean default false")
    private boolean communitySharingEnabled = false;

    // =========================================================
    // MÉTODOS OBRIGATÓRIOS DO USERDETAILS (A Tradução pro Spring)
    // =========================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna uma lista vazia, pois no MVP não teremos perfis complexos
        // (Admin/User)
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password; // Diz pro Spring onde está a senha
    }

    @Override
    public String getUsername() {
        return this.email; // Diz pro Spring que o "login" é o e-mail
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta não expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta não bloqueia
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Senha não expira
    }

    @Override
    public boolean isEnabled() {
        return true; // Usuário sempre ativo por padrão
    }

}
