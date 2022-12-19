package avada.media.myhouse24_admin.model.systemSettings.pages;

import avada.media.myhouse24_admin.model.common.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends MappedEntity implements UserDetails {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    @ManyToOne
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        grantedAuthorities.addAll(role.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toList()));
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status != Status.INACTIVE;
    }

    public enum Status {
        NEW,
        ACTIVE,
        INACTIVE
    }

}
