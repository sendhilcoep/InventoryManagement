package authentication.controller;

import java.util.List;

import authentication.dao.AuthenticationDAO;
import authentication.model.*;
public class Authentication {
public static List<User> authenticate(User user)
{
	AuthenticationDAO dao = new AuthenticationDAO();
	return(dao.authenticate(user));
}
}
