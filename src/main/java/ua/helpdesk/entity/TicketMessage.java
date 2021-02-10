package ua.helpdesk.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "Ticket_messages")
public class TicketMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TICKET_ID")
	private Ticket ticket;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;

	@NotNull
	@Column(name = "DATE", nullable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date date;

	@NotEmpty
	@Size(max = 1000)
	@Column(name = "TEXT", nullable = false)
	private String text;
}
