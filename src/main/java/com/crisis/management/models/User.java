package com.crisis.management.models;

import com.crisis.management.models.authority.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false, unique = true)
    private String tel;

    @OneToOne(mappedBy = "userId", orphanRemoval = true)
    private Employee employee;

    @OneToMany(mappedBy = "user")
    private List<AlertProposition> alertPropositions;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String username, String password, String email, String tel, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + tel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                username.equals(user.username) &&
                email.equals(user.email) &&
                tel.equals(user.tel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, tel);
    }
}
