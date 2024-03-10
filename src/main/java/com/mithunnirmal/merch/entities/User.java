package com.mithunnirmal.merch.entities;

import com.mithunnirmal.merch.entities.verificationtoken.VerificationToken;
import com.mithunnirmal.merch.enums.UserRole;
import com.mithunnirmal.merch.modelDtos.AddressDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class User extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    String id;

    @Column
    String firstName;

    @Column
    String middleName;

    @Column
    String lastName;

//    @Column(length = 60)
    String password;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL)
    List<Address> address;

    @Enumerated(EnumType.ORDINAL)
    UserRole role;

    @Column
    boolean enabled = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL )
    private List<VerificationToken> verificationTokens;

    public User addAddress(Address address) {
        address.setUsers(this);
        this.address.add(address);
        return this;
    }

//Override methods of UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
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
}
