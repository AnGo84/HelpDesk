package ua.helpdesk.entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "SERVICES")
@Data
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty
    @Column(name = "name", unique = true, nullable = false)
    private String name;

}
