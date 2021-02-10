package ua.helpdesk.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ticket_priorities")
@Data
public class TicketPriority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "name", unique = true, nullable = false)
    private String name;


    @NotNull
    @Column(name = "time_limit", unique = true, nullable = false)
    private Integer timeLimit;

}
