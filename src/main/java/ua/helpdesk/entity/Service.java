package ua.helpdesk.entity;


import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SERVICES")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Service extends AbstractEntity {

    private String name;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
