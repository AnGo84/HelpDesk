package ua.helpdesk.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ua.helpdesk.entities.converter.TicketStateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "TICKETS")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
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

    @NotNull
    @Column(name = "TICKET_STATE_ID")
    @Convert(converter = TicketStateConverter.class)
    private TicketState ticketState;

    @NotNull
    @Column(name = "TICKET_TYPE_ID")
    @Convert(converter = TicketStateConverter.class)
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    private List<TicketMessage> messages;

}
