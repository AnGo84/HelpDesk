package ua.helpdesk.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Ticket_messages")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TicketMessage extends AbstractEntity {

	private Ticket ticket;

	private User user;

	private Date date;

	private String text;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TICKET_ID", nullable = false)
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "DATE", nullable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@NotEmpty
	@Size(max = 1000)
	@Column(name = "TEXT", nullable = false)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
