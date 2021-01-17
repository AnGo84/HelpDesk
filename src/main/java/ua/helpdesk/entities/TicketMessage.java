package ua.helpdesk.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "Ticket_messages")
public class TicketMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

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

	@NotNull
	@Column(name = "TEXT", nullable = false)
	private String text;
}
