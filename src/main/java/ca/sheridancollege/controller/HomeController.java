package ca.sheridancollege.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Movie;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.database.UserDatabaseAccess;
import ca.sheridancollege.database.MovieDatabaseAccess;

@Controller
public class HomeController {

	@Autowired
	@Lazy
	private UserDatabaseAccess da;
	@Autowired
	private MovieDatabaseAccess mda;

	// go home page
	@GetMapping("/")
	public String index() {
		return "index.html";
	}

	// show registration page to new users
	@GetMapping("/createAccount")
	public String showCreateAccount(Model model) {
		model.addAttribute("user", new User());
		return "registration.html";
	}

	// register user in database and display movies on registering
	@PostMapping("/createAccount")
	public String register(Model model, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String username, @RequestParam String password) {

		da.addUser(firstname, lastname, username, password);
		
		long userId = da.findUserAccount(username).getUserId();
		da.addRole(userId, 2);
		model.addAttribute("movies", mda.getMovies());
		return "/User/viewMovie.html";
	}

	// show movies to non registered users
	@GetMapping("/viewMovie")
	public String showMoviesToUser(Model model) {
		model.addAttribute("movies", mda.getMovies());
		return "/User/viewMovie.html";
	}

	// Display movie page to registered users
	@PostMapping("/displayMovies")
	public String showMoviesToUser2(Model model) {
		model.addAttribute("movies", mda.getMovies());
		return "/User/viewMovie.html";
	}

	// =================================================================================
	// ==================================================================================

	// The mappings for pages that are allowed to admin to perform CRUD operation on
	// users database
	// show add user page
	@GetMapping("/addUser")
	public String showaddUser(Model model) {
		model.addAttribute("user", new User());
		return "/admin/addUser.html";
	}

	// add user to the database
	@PostMapping("/addUser")
	public String addUser(Model model, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String username, @RequestParam String password) {
		da.addUser(firstname, lastname, username, password);
		long userId = da.findUserAccount(username).getUserId();
		da.addRole(userId, 2);
		return "redirect:/viewUser";
	}

	// view user database page
	@GetMapping("/viewUser")
	public String viewUsers(Model model) {
		model.addAttribute("users", da.getUsers());
		return "/admin/viewUser.html";
	}

	// edit link that take to the modify page link
	@GetMapping("/editlink/{id}")
	public String editUser(Model model, @PathVariable int id) {
		User user = da.getUserbyIds(id);
		model.addAttribute("user", user);
		return "/admin/modifyUser.html";
	}

	// modify the user and take back to view user page
	@GetMapping("/modifyUser")
	public String modifyUser(Model model, @ModelAttribute User user) {
		da.editUser(user);
		return "redirect:/viewUser";
	}

	// delete the user and refresh view user page
	@GetMapping("/deletelink/{id}")
	public String deleteUser(Model model, @PathVariable int id, @ModelAttribute User user) {
		da.deleteUser(id);
		return "redirect:/viewUser";
	}

	// ================================================================================
	// =================================================================================

	// The following mappings are for CRUD operations of movies performed by admin
	// Show the content of movies database
	@GetMapping("/viewMovies")
	public String viewMovies(Model model) {
		model.addAttribute("movies", mda.getMovies());
		return "/admin/viewMovies.html";
	}

	// show add movie page
	@GetMapping("/addMovie")
	public String addMovie(Model model) {
		model.addAttribute("movie", new Movie());
		return "/admin/addMovie.html";
	}

	// after adding, it shows view movie page
	@PostMapping("/addMovie")
	public String afteraddViewMovies(Model model, @ModelAttribute Movie movie) {
		model.addAttribute("movie", new Movie());
		mda.addMovie(movie);
		model.addAttribute("movies", mda.getMovies());
		return "redirect:/viewMovies";
	}

	// show the editing movies page
	@GetMapping("/editMovie/{id}")
	public String editMovie(Model model, @PathVariable int id) {
		Movie movie = mda.getMoviesbyIds(id);
		model.addAttribute("movie", movie);
		return "/admin/modifyMovie.html";
	}

	// after editing take back to view movie page
	@GetMapping("/modifyMovie")
	public String modifyMovie(Model model, @ModelAttribute Movie movie) {
		mda.editMovie(movie);
		return "redirect:/viewMovies";
	}

	// delete movie from the database and then refresh the view movies page
	@GetMapping("/deleteMovie/{id}")
	public String deleteMovie(Model model, @PathVariable int id) {
		mda.deleteMovie(id);
		return "redirect:/viewMovies";
	}

	// =================================================================================
	// ==================================================================================

	//print the receipt 
	@GetMapping("/receipt")
	public String showRecipt(Model model, @RequestParam HashMap<String, String> map) {
		int totalSeats = 0;
		double totalAmount = 0;

		if (map.get("generalAdmission") != null) {
			int generalSeats = Integer.parseInt(map.get("generalSeats"));
			totalAmount += 15 * generalSeats;
			totalSeats += generalSeats;
			model.addAttribute("general", "General Admission");
		}
		if (map.get("sheridanStudent") != null) {
			int sheridanStudentSeats = Integer.parseInt(map.get("sheridanStudentSeats"));
			totalAmount += 10 * sheridanStudentSeats;
			totalSeats += sheridanStudentSeats;
			model.addAttribute("sheridan", "Sheridan College Student");
		}
		if (map.get("prog32758") != null) {
			int prog32758Seats = Integer.parseInt(map.get("prog32758Seats"));
			totalAmount += 8 * prog32758Seats;
			totalSeats += prog32758Seats;
			model.addAttribute("prog", "PROG 32758 Student");
		}
		if (map.get("seniorCitizen") != null) {
			int seniorCitizenSeats = Integer.parseInt(map.get("seniorCitizenSeats"));
			totalAmount += 5 * seniorCitizenSeats;
			totalSeats += seniorCitizenSeats;
			model.addAttribute("senior", "Senior Citizen");
		}
		if (map.get("children") != null) {
			int childrenSeats = Integer.parseInt(map.get("childrenSeats"));
			totalAmount += 5 * childrenSeats;
			totalSeats += childrenSeats;
			model.addAttribute("children", "Children");
		}

		int movieId = Integer.parseInt(map.get("movie"));
		Movie movie = mda.getMoviesbyIds(movieId);
		
		model.addAttribute("movie", movie);
		
		String date = map.get("date");
		
		model.addAttribute("date", date);
		model.addAttribute("seats", totalSeats);
		model.addAttribute("amount", totalAmount);

		//update the seats available, once the user buy seats for that movie
		mda.updateSeats(movieId, totalSeats, movie);

		//the receipt text file is in the static folder
		String path = "C:\\Receipt\\reciept.txt";

		File file = new File(path);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("Reciept\n");
			writer.newLine();
			writer.write("\nMovie Name:                  " + movie.getTitle() + "\nDuration:                         "
					+ movie.getMovieDuration() + "\nLanguage:                       " + movie.getLanguage()
					+ "\nShow Time:                      " + movie.getShowTime() + "\nShow Date:                      "
					+ date + "\nTotal Number of Seats:   " + totalSeats + "\nTotal Amount:                 "
					+ totalAmount);

			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println("Error");
		}
		return "receipt.html";
	}
	
	@GetMapping("/accessdenied")
	public String permissionDenied() {
		return "/error/accessdenied";
	}
}
