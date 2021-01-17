package ua.helpdesk.entities;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GROUPS")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /*@NotEmpty*/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "GROUPS_CATEGORIES",
            joinColumns = {@JoinColumn(name = "GROUP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    private Set<Category> categories = new HashSet<>();

    @Column(name = "right", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean right;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        Group other = (Group) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (categories == null) {
            if (other.categories != null)
                return false;
        } else if (!categories.equals(other.categories)) {
            return false;
        }
        if (!right.equals(other.right)) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((categories == null || categories.isEmpty()) ? 0 : categories.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Group{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", categories=").append(categories);
        sb.append(", right=").append(right);
        sb.append('}');
        return sb.toString();
    }
}
