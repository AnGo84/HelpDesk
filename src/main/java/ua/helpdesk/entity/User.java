package ua.helpdesk.entity;

import lombok.Data;
import org.hibernate.annotations.Type;
import ua.helpdesk.entity.converter.UserTypeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "LOGIN", unique = true, nullable = false)
    @Size(min = 4, max = 36)
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

    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;

    @Size(max = 50)
    @Column(name = "PHONE")
    private String phone;

    @NotNull
    @Column(name = "USER_TYPE_ID")
    @Convert(converter = UserTypeConverter.class)
    private UserType userType;

    @NotNull
    @Column(name = "active", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active;

}
