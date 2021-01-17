package ua.helpdesk.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TICKETS")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @NotEmpty
    @Column(name = "NUMBER", unique = true, nullable = false)
    private String number;

    @NotBlank
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @NotBlank
    @Column(name = "THEME", nullable = false)
    private String theme;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICE_ID")
    private Service service;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRIORITY_ID")
    private Priority priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TICKET_STATE_ID")
    private TicketState ticketState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TICKET_TYPE_ID")
    private TicketType ticketType;

    @Column(name = "DATE", nullable = true)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERFORMER_ID")
    private User performer;

    @Column(name = "SOLUTION", nullable = false)
    private String solution;


}