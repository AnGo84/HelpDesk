package ua.helpdesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ua.helpdesk.entities.*;
import ua.helpdesk.service.CategoryService;
import ua.helpdesk.service.TableDataService;
import ua.helpdesk.service.TicketViewService;
import ua.helpdesk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Locale;


//@Controller
//@RequestMapping("/")
@SessionAttributes("roles")
public class OldAppController {
    static final Logger logger = LoggerFactory.getLogger(OldAppController.class);

    @Autowired
    UserService userService;

	/*@Autowired
	UserTypeService userTypeService;*/

    @Autowired
    TableDataService<Service> serviceService;
    @Autowired
    CategoryService categoryService;
    //TableDataService<Category> categoryService;

    @Autowired
    TicketViewService ticketViewService;
    @Autowired
    TableDataService<Group> groupService;
    @Autowired
    TableDataService<Priority> priorityService;
    @Autowired
    TableDataService<TicketType> ticketTypeService;
    @Autowired
    TableDataService<TicketState> ticketStateService;
    @Autowired
    TableDataService<Ticket> ticketService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;


    /**
     * This method will list all tasks for logged user.
     */
    @RequestMapping(value = {"/", "/index", "/list", "/tickets"}, method = RequestMethod.GET)
    public String listTasks(ModelMap model) {

        List<TicketView> users = ticketViewService.findTicketsForUser(getPrincipal());
        model.addAttribute("tickets", users);
        model.addAttribute("loggedinuser", getPrincipal());
        return "index";
    }


    /*USER*/

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = {"/userslist"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("loggedinuser", getPrincipal());
        return "userslist";
    }

    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "user";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {
        logger.info("NEW USER: " + user.toString());
        if (result.hasErrors()) {
            //return "registration";
            return "user";
        }

        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation
         * and applying it on field [sso] of Model class [User].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
        /*if (!userService.isUserLoginUnique(user.getId(), user.getLogin())) {
            FieldError loginError = new FieldError("user", "login", messageSource.getMessage("non.unique.login", new String[]{user.getLogin()}, Locale.getDefault()));
            result.addError(loginError);
            //return "registration";
            return "user";
        }*/


        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getLogin() + " (" + user.getLastName() + " " + user.getFirstName() + ") registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/userslist");
        model.addAttribute("linktotext", "Users list");

        //return "registrationsuccess";
        return "actionSuccess";
    }


    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = {"/edit-user-{login}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable String login, ModelMap model) {
        User user = userService.findByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "user";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = {"/edit-user-{login}"}, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String login) {
        logger.info("EDIT USER: " + user.toString());
        if (result.hasErrors()) {
            return "user";
        }

		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING Login in UI which is a unique key to a User.
		if(!userService.isUserLoginUnique(user.getId(), user.getLogin())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getLogin()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}*/


        userService.updateUser(user);

        model.addAttribute("success", "User " + user.getLogin() + " (" + user.getLastName() + " " + user.getFirstName() + ") updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/userslist");
        model.addAttribute("linktotext", "Users list");

        //return "registrationsuccess";
        return "actionsuccess";
    }


    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = {"/delete-user-{login}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String login) {
        userService.deleteUserByLogin(login);
        return "redirect:/userslist";
    }
    /*END USER*/

    /*SERVICES*/

    /**
     * This method will list all existing services.
     */
    @RequestMapping(value = {"/serviceslist"}, method = RequestMethod.GET)
    public String listServices(ModelMap model) {
        List<Service> services = serviceService.findAllData();
        model.addAttribute("services", services);
        model.addAttribute("loggedinuser", getPrincipal());
        return "serviceslist";
    }

    /**
     * This method will provide the medium to add a new service.
     */
    @RequestMapping(value = {"/newservice"}, method = RequestMethod.GET)
    public String newService(ModelMap model) {
        logger.info("New SERVICE");
        Service service = new Service();
        model.addAttribute("service", service);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "service";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving service in database. It also validates the service input
     */
    @RequestMapping(value = {"/newservice"}, method = RequestMethod.POST)
    public String saveService(@Valid Service service, BindingResult result,
                              ModelMap model) {
        logger.info("NEW SERVICE: " + service.toString());
        if (result.hasErrors()) {
            return "service";
        }
        if (!serviceService.isDataUnique(service.getId(), service.getName())) {
            FieldError nameError = new FieldError("service", "name", messageSource.getMessage("non.unique.name", new String[]{service.getName()}, Locale.getDefault()));
            result.addError(nameError);
            //return "registration";
            return "service";
        }
        serviceService.saveData(service);

        model.addAttribute("success", "Service " + service.getName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/serviceslist");
        model.addAttribute("linktotext", "Services list");

        return "actionSuccess";
    }


    /**
     * This method will provide the medium to update an existing service.
     */
    @RequestMapping(value = {"/edit-service-{id}"}, method = RequestMethod.GET)
    public String editService(@PathVariable Integer id, ModelMap model) {
        logger.info("EDIT SERVICE: " + id);
        Service service = serviceService.findById(id);

        model.addAttribute("service", service);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "service";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating service in database. It also validates the service input
     */
    @RequestMapping(value = {"/edit-service-{id}"}, method = RequestMethod.POST)
    public String updateService(@Valid Service service, BindingResult result,
                                ModelMap model, @PathVariable Integer id) {
        logger.info("EDIT SERVICE: " + service);
        if (result.hasErrors()) {
            return "service";
        }
        serviceService.updateData(service);

        model.addAttribute("success", "Service " + service.getName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/serviceslist");
        model.addAttribute("linktotext", "Services list");

        return "actionSuccess";
    }

    /**
     * This method will delete an service by it's ID value.
     */
    @RequestMapping(value = {"/delete-service-{id}"}, method = RequestMethod.GET)
    public String deleteService(@PathVariable Integer id) {
        serviceService.deleteDataById(id);
        return "redirect:/serviceslist";
    }

    /*END SERVICES*/

    /*CATEGORIES*/

    /**
     * This method will list all existing categories.
     */
    @RequestMapping(value = {"/categorieslist"}, method = RequestMethod.GET)
    public String listCategories(ModelMap model) {
        List<Category> categories = categoryService.findAllData();
        model.addAttribute("categories", categories);
        model.addAttribute("loggedinuser", getPrincipal());
        return "categorieslist";
    }

    /**
     * This method will provide the medium to add a new category.
     */
    @RequestMapping(value = {"/newcategory"}, method = RequestMethod.GET)
    public String newCategory(ModelMap model) {
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "category";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving category in database. It also validates the category input
     */
    @RequestMapping(value = {"/newcategory"}, method = RequestMethod.POST)
    public String saveCategory(@Valid Category category, BindingResult result,
                               ModelMap model) {
        logger.info("Save CATEGORY: " + category);
        if (result.hasErrors()) {
            logger.error(result.getAllErrors().toString());
            return "category";
        }
        if (!categoryService.isDataUnique(category.getId(), category.getName(), category.getService().getId())) {
            FieldError nameError = new FieldError("category", "name", messageSource.getMessage("non.unique.name", new String[]{category.getName()}, Locale.getDefault()));
            result.addError(nameError);
            return "category";
        }
        categoryService.saveData(category);

        model.addAttribute("success", "Category " + category.getName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/categorieslist");
        model.addAttribute("linktotext", "Categories list");

        return "actionSuccess";
    }


    /**
     * This method will provide the medium to update an existing category.
     */
    @RequestMapping(value = {"/edit-category-{id}"}, method = RequestMethod.GET)
    public String editCategory(@PathVariable Integer id, ModelMap model) {
        logger.info("edit-category-: " + id);

        Category category = categoryService.findById(id);

        model.addAttribute("category", category);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "category";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating category in database. It also validates the category input
     */
    @RequestMapping(value = {"/edit-category-{id}"}, method = RequestMethod.POST)
    public String updateCategory(@Valid Category category, BindingResult result,
                                 ModelMap model, @PathVariable Integer id) {
        logger.info("EDIT CATEGORY: " + category);
        if (result.hasErrors()) {
            return "category";
        }
        categoryService.updateData(category);

        model.addAttribute("success", "Category " + category.getName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/categorieslist");
        model.addAttribute("linktotext", "Categories list");

        return "actionSuccess";
    }

    /**
     * This method will delete an category by it's ID value.
     */
    @RequestMapping(value = {"/delete-category-{id}"}, method = RequestMethod.GET)
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteDataById(id);
        return "redirect:/categorieslist";
    }

    /*END CATEGORIES*/

    /*GROUPS*/

    /**
     * This method will list all existing groups.
     */
    @RequestMapping(value = {"/groupslist"}, method = RequestMethod.GET)
    public String listGroups(ModelMap model) {
        List<Group> groups = groupService.findAllData();
        model.addAttribute("groups", groups);
        model.addAttribute("loggedinuser", getPrincipal());
        return "groupslist";
    }

    /**
     * This method will provide the medium to add a new group.
     */
    @RequestMapping(value = {"/newgroup"}, method = RequestMethod.GET)
    public String newGroup(ModelMap model) {
        logger.info("New Group");
        Group group = new Group();
        model.addAttribute("group", group);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "group";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving group in database. It also validates the group input
     */
    @RequestMapping(value = {"/newgroup"}, method = RequestMethod.POST)
    public String saveService(@Valid Group group, BindingResult result,
                              ModelMap model) {
        logger.info("NEW GROUP: " + group.toString());
        if (result.hasErrors()) {
            return "group";
        }
        if (!groupService.isDataUnique(group.getId(), group.getName())) {
            FieldError nameError = new FieldError("group", "name", messageSource.getMessage("non.unique.name", new String[]{group.getName()}, Locale.getDefault()));
            result.addError(nameError);
            //return "registration";
            return "group";
        }
        groupService.saveData(group);

        model.addAttribute("success", "Group " + group.getName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/groupslist");
        model.addAttribute("linktotext", "Groups list");

        return "actionSuccess";
    }


    /**
     * This method will provide the medium to update an existing group.
     */
    @RequestMapping(value = {"/edit-group-{id}"}, method = RequestMethod.GET)
    public String editGroup(@PathVariable Integer id, ModelMap model) {
        logger.info("EDIT GROUP: " + id);
        Group group = groupService.findById(id);

        model.addAttribute("group", group);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "group";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating group in database. It also validates the group input
     */
    @RequestMapping(value = {"/edit-group-{id}"}, method = RequestMethod.POST)
    public String updateGroup(@Valid Group group, BindingResult result,
                              ModelMap model, @PathVariable Integer id) {
        logger.info("EDIT GROUP: " + group);
        if (result.hasErrors()) {
            return "group";
        }
        groupService.updateData(group);

        model.addAttribute("success", "Group " + group.getName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/groupslist");
        model.addAttribute("linktotext", "Groups list");

        return "actionSuccess";
    }

    /**
     * This method will delete an group by it's ID value.
     */
    @RequestMapping(value = {"/delete-group-{id}"}, method = RequestMethod.GET)
    public String deleteGroup(@PathVariable Integer id) {
        groupService.deleteDataById(id);
        return "redirect:/grouplist";
    }

    /*END GROUPS*/

    /*TICKET*/

    /**
     * This method will list all existing tickets.
     */
    @RequestMapping(value = {"/ticketslist"}, method = RequestMethod.GET)
    public String listTickets(ModelMap model) {
        List<TicketView> tickets = ticketViewService.findTicketsForUser(getPrincipal());

        model.addAttribute("tickets", tickets);
        model.addAttribute("loggedinuser", getPrincipal());
        return "index";
    }

    /**
     * This method will provide the medium to add a new ticket.
     */
    @RequestMapping(value = {"/newticket"}, method = RequestMethod.GET)
    public String newTicket(ModelMap model) {
        logger.info("New Ticket");
        Ticket ticket = new Ticket();
        model.addAttribute("ticket", ticket);
        model.addAttribute("loggedinuser", getPrincipal());
        return "addTicket";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving ticket in database. It also validates the ticket input
     */
    @RequestMapping(value = {"/newticket"}, method = RequestMethod.POST)
    public String saveTicket(@Valid Ticket ticket, BindingResult result,
                              ModelMap model) {
        logger.info("NEW TICKET: " + ticket.toString());
        if (result.hasErrors()) {
            logger.info("Error: " + result.getAllErrors().toString());
            return "addTicket";
        }
        logger.info("Before update fields: " + ticket.toString());
        ticket.setUser(userService.findByLogin(getPrincipal()));
        ticket.setNumber(ticket.getService().getName().replaceAll(" ",""));
        ticket.setDate(new Date());
        ticket.setTicketState(ticketStateService.findById(1));
        //logger.info("After update fields: " + ticket.toString());

        /*if (!ticketService.isDataUnique(ticket.getId(), ticket.getNumber())) {
            FieldError nameError = new FieldError("ticket", "number", messageSource.getMessage("non.unique.name", new String[]{ticket.getNumber()}, Locale.getDefault()));
            result.addError(nameError);
            //return "registration";
            return "addTicket";
        }*/
        ticketService.saveData(ticket);

        model.addAttribute("success", "Ticket " + ticket.getNumber() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/ticketslist");
        model.addAttribute("linktotext", "Tickets list");

        return "actionSuccess";
    }

    /**
     * This method will provide the medium to update an existing ticket.
     */
    @RequestMapping(value = {"/edit-ticket-{id}"}, method = RequestMethod.GET)
    public String editTicket(@PathVariable String id, ModelMap model) {
        Ticket ticket = ticketService.findById(Integer.valueOf(id));
        model.addAttribute("ticket", ticket);
        //model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "ticket";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = {"/edit-ticket-{id}"}, method = RequestMethod.POST)
    public String updateTicket(@Valid Ticket ticket, BindingResult result,
                             ModelMap model, @PathVariable String id) {
        logger.info("EDIT Ticket: " + ticket.toString());
        if (result.hasErrors()) {
            for (ObjectError objectError:
            result.getAllErrors()) {
                //logger.info(objectError.getDefaultMessage());
                logger.info(objectError.toString());
            }

            return "ticket";
        }
		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING Login in UI which is a unique key to a User.
		if(!userService.isUserLoginUnique(user.getId(), user.getLogin())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getLogin()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}*/
        ticketService.updateData(ticket);

        model.addAttribute("success", "Ticket " + ticket.getNumber() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("linkto", "/tickets");
        model.addAttribute("linktotext", "Tickets list");
        //return "registrationsuccess";
        return "actionSuccess";
    }

    /*END TICKET*/

    /**
     * This method will provide User list to views
     */
    @ModelAttribute("usersList")
    public List<User> initializeUsers() {
        return userService.findAllUsers();
    }

    /**
     * This method will provide UserType list to views
     */
   /* @ModelAttribute("roles")
    public List<UserType> initializeProfiles() {
        return userTypeService.findAll();
    }
*/
    /**
     * This method will provide Services list to views
     */
    @ModelAttribute("servicesList")
    public @ResponseBody List<Service> initializeServices() {
        return serviceService.findAllData();
    }

    /**
     * This method will provide Categories list to views
     */
    @ModelAttribute("categoriesList")
    public List<Category> initializeCategories() {
        return categoryService.findAllData();
    }

    /**
     * This method will provide Groups list to views
     */
    @ModelAttribute("groupsList")
    public List<Group> initializeGroups() {
        return groupService.findAllData();
    }

    /**
     * This method will provide Priorities list to views
     */
    @ModelAttribute("prioritiesList")
    public List<Priority> initializePriorities() {
        return priorityService.findAllData();
    }

    /**
     * This method will provide TicketTypes list to views
     */
    @ModelAttribute("ticketTypesList")
    public List<TicketType> initializeTicketTypes() {
        return ticketTypeService.findAllData();
    }

    /**
     * This method will provide TicketStates list to views
     */
    @ModelAttribute("ticketStatesList")
    public List<TicketState> initializeTicketStatyes() {
        return ticketStateService.findAllData();
    }

    /**
     * This method will provide Categories list for Service by name to views
     */
    /**@RequestMapping(value = "/services", method = RequestMethod.GET)
    public @ResponseBody
    List<Service> findAllServices() {
        logger.debug("finding all services");
        return serviceService.findAllData();
    }
    */
    @RequestMapping(value = "/serviceCategories", method = RequestMethod.GET)
    public @ResponseBody
    List<Category> categoriesForService(
            @RequestParam(value = "serviceName", required = true) Integer service) {
        //logger.info("finding categories for service " + service);
        return categoryService.findCategoryForService(service);
    }

    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }

    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/list";
        }
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }


}
