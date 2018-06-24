package ua.helpdesk.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @NotEmpty
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @NotEmpty
    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @NotEmpty
    @Column(name = "MIDDLENAME", nullable = false)
    private String middleName;

    @NotEmpty
    @Column(name = "EMAIL", nullable = false)
    private String email;

    //    @NotEmpty
    @Column(name = "PHONE", length = 50, nullable = true)
    private String phone;

//	@NotEmpty
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "APP_USER_USER_PROFILE",
//             joinColumns = { @JoinColumn(name = "USER_ID") },
//             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
//	private Set<UserType> userProfiles = new HashSet<UserType>();

    //    @NotEmpty
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_TYPE_ID")
    private UserType userType;

    /*@NotNull*/
    @ManyToMany(fetch = FetchType.LAZY)
    //@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_GROUPS",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")})
    private Set<Group> groups = new HashSet<>();


    @Column(name = "active", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((userType == null) ? 0 : userType.hashCode());
        //result = prime * result + ((groups == null) ? 0 : groups.hashCode());
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;

        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;

        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;

        if (middleName == null) {
            if (other.middleName != null)
                return false;
        } else if (!middleName.equals(other.middleName))
            return false;

        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;

        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;

        if (userType == null) {
            if (other.userType != null)
                return false;
        } else if (!userType.equals(other.login))
            return false;

//        if (groups == null) {
//            if (other.groups != null)
//                return false;
//        } else if (!groups.equals(other.groups))
//            return false;

        if (active == null) {
            if (other.active != null)
                return false;
        } else if (!active.equals(other.active))
            return false;


        return true;
    }

    /*
     * DO-NOT-INCLUDE passwords in toString function.
     * It is done here just for convenience purpose.
     */

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", middleName='").append(middleName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", userType=").append(userType);
        sb.append(", groups=").append(groups);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
