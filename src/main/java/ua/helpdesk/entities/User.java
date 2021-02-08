package ua.helpdesk.entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import ua.helpdesk.entities.converter.UserTypeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @NotBlank
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NotBlank
    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @NotBlank
    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "MIDDLENAME")
    private String middleName;

    @NotBlank
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONE", length = 50, nullable = true)
    private String phone;

    @NotNull
    @Column(name = "USER_TYPE_ID")
    @Convert(converter = UserTypeConverter.class)
    private UserType userType;

    @Column(name = "active", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active;

}
