package ca.sheridancollege.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.User;

@Repository
public class UserDatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void addUser(String firstName, String lastName, String userName, String password) {
		MapSqlParameterSource userParameter = new MapSqlParameterSource();
		String query = "INSERT INTO User_info"
				+ "(firstName, lastName, userName, encryptedPassword, ENABLED)" 
				+ " VALUES " + 
				"(:firstName, :lastName, :userName, :encryptedPassword, 1)";
		userParameter.addValue("firstName", firstName);
		userParameter.addValue("lastName", lastName);
		userParameter.addValue("userName", userName);
		userParameter.addValue("encryptedPassword", 
				passwordEncoder.encode(password));
		jdbc.update(query, userParameter);
	}
	
	
	//==============================================================================================
	public ArrayList<User> getUsers() {
		String query = "SELECT * FROM User_info";
		ArrayList<User> users = new ArrayList<User>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String,Object>());
		for (Map<String, Object> row: rows) {
			User u = new User();

			u.setUserId((Long) row.get("userId"));
			u.setFirstName((String) row.get("firstName"));
			u.setLastName((String) row.get("lastName"));
			u.setUserName((String) row.get("userName"));
			u.setEncyptedPassword((String) row.get("encryptedPassword"));
			users.add(u);
		}
		return users;
	}
	
	
	//==============================================================================================
	public User getUserbyIds(int userId) {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT * FROM User_info WHERE userId=:userId"; 
		MapSqlParameterSource userParameter = new MapSqlParameterSource();
		userParameter.addValue("userId", userId);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, userParameter);
		for (Map<String, Object> row: rows) {
			User u = new User();
			u.setUserId((Long) row.get("userId"));
			u.setFirstName((String) row.get("firstName"));
			u.setLastName((String) row.get("lastName"));
			u.setUserName((String) row.get("userName"));
			u.setEncyptedPassword((String) row.get("encryptedPassword"));
			users.add(u);
		}
		if (users.size() > 0) {
			return users.get(0);
		}
		return null ;
	}
	
	
	//==============================================================================================
	public void editUser(User user) {
		MapSqlParameterSource userParameter = new MapSqlParameterSource();
		String query = "UPDATE User_info Set firstName=:firstName,"
				+ " lastName=:lastName, userName=:userName"
				+ " WHERE userId=:userId";
		userParameter.addValue("userId", user.getUserId());
		userParameter.addValue("firstName", user.getFirstName());
		userParameter.addValue("lastName", user.getLastName());
		userParameter.addValue("userName", user.getUserName());
		
		jdbc.update(query, userParameter);
	}
	
	
	//==============================================================================================
	public void deleteUser(int userId) {
		MapSqlParameterSource roleParameter = new MapSqlParameterSource();
		String query2 = "DELETE FROM User_Role WHERE userId=:userId";
		roleParameter.addValue("userId", userId);
		jdbc.update(query2, roleParameter);
		
		MapSqlParameterSource userParameter = new MapSqlParameterSource();
		String query = "DELETE FROM User_info WHERE userId=:userId";
		userParameter.addValue("userId", userId);
		jdbc.update(query, userParameter);
	}
	
	
	//==============================================================================================
	
	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM User_info WHERE userName=:userName";
		parameters.addValue("userName", userName);
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size()>0)
			return users.get(0);
		else
			return null;
	}
	
	public List<String> getRolesById(long userId) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT User_Role.userId, Role.roleName "
				+ "FROM User_Role, Role "
				+ "WHERE User_Role.roleId = Role.roleId "
				+ "and userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String)row.get("roleName"));
		}
		return roles;
	} 
	
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO User_Role (userId, roleId)" 
				+ "VALUES (:userId, :roleId);";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);	
	}	
}
