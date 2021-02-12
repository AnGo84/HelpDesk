package ua.helpdesk.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CATEGORIES")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Category extends AbstractEntity {

    private String name;

    private Service service;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "NAME", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICE_ID")
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
