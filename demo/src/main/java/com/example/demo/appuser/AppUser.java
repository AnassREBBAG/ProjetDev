package com.example.demo.appuser;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collections;
import java.util.Collection;
/*
https://zetcode.com/springboot/annotations/#:~:text=The%20%40Entity%20annotation%20specifies%20that,mapped%20to%20a%20database%20table.
The @Entity annotation specifies that the class is an entity and is mapped to a database table.
 The @Table annotation specifies the name of the database table to be used for mapping.
 The @Id annotation specifies the primary key of an entity and the @GeneratedValue provides for
  the specification of generation strategies for the values of primary keys.
 */

@Getter                                                 //https://projectlombok.org/features/GetterSetter
@Setter
@EqualsAndHashCode                                      //https://projectlombok.org/features/EqualsAndHashCode
@NoArgsConstructor                                      //https://www.educative.io/answers/what-is-the-noargsconstructor-annotation-in-lombok
                                                        //The annotation generates the no-arg constructor for us
@Entity                                                 //The @Entity annotation specifies that the class is an entity and is mapped to a database table

public class AppUser implements UserDetails {


    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1)
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole ;
    private Boolean locked;
    private Boolean enabled;

    public AppUser(String firstName,
                   String lastName,
                   String email,
                   String password,
                   AppUserRole appUserRole) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());

        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {return email;}

    public String getFirstName() {return firstName;}

    public String getLastName() {
        return lastName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
