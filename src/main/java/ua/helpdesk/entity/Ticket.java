package ua.helpdesk.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ua.helpdesk.entity.converter.TicketStateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TICKETS")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private TicketPriority ticketPriority;

    @NotNull
    @Column(name = "TICKET_STATE_ID")
    @Convert(converter = TicketStateConverter.class)
    private TicketState ticketState;

    @NotNull
    @Column(name = "TICKET_TYPE_ID")
    @Convert(converter = TicketStateConverter.class)
    private TicketType ticketType;

    @NotNull
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
    @Size(max = 1000)
    private String solution;

    /*@OneToMany( orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    private List<TicketMessage> messages;*/

}
