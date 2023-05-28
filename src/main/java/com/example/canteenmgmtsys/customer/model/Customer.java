package com.example.canteenmgmtsys.customer.model;

import com.example.canteenmgmtsys.customer.enums.Role;
import com.example.canteenmgmtsys.order.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "email_unique_constraint", columnNames = "email"),
        @UniqueConstraint(name = "phone_unique_constraint", columnNames = "phoneNumber")
})
public class Customer implements UserDetails {
    private static AtomicLong counter = new AtomicLong(1);
    private static final String PREFIX = "C";

    @Id
    private String customerId;
    private String customerName;

    private String email;

    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;
    @OneToMany(mappedBy = "customer")
    private List<Order> order;

    @PrePersist
    private void generateUniqueId() {
        long sequentialNumber = counter.getAndIncrement();
        this.customerId = String.format("%s%d", PREFIX, sequentialNumber);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
