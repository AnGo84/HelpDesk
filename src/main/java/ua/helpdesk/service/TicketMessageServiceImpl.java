package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.entity.Ticket;
import ua.helpdesk.entity.TicketMessage;
import ua.helpdesk.repository.TicketMessageRepository;

import java.util.List;

@Service
@Transactional
@Slf4j
public class TicketMessageServiceImpl extends AbstractService<TicketMessage, TicketMessageRepository> {
	public TicketMessageServiceImpl(TicketMessageRepository repository) {
		super(repository);
	}

	public List<TicketMessage> getAllByTicket(Ticket ticket) {
		log.debug("Get all TicketMessages by Ticket: {}", ticket);
		return repository.findByTicketOrderByDateDesc(ticket);
	}

	@Override
	public Boolean isExist(TicketMessage entity) {
		log.info("Check on exist: {}", entity);
		if (entity == null || entity.getId() == null) {
			return false;
		}
		TicketMessage foundEntity = repository.getOne(entity.getId());
		if (foundEntity == null) {
			return false;
		} else if (entity.getId().equals(foundEntity.getId())) {
			return true;
		}
		return false;
	}
}

