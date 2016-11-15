package bootsample.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import bootsample.dao.InstanceRepository;
import bootsample.model.Instance;
import bootsample.model.User;
import bootsample.service.InstanceService;
import bootsample.service.UserService;

@RestController
public class UserController {
	
		private static final String Integer = null;
		@Autowired
		private UserService userService;
		
		@Autowired
		private InstanceService instanceService;
		
		@PostMapping("/api/user/register")
		public ModelAndView registerUser(@RequestParam(value="first_name",required=true) String f_name,
				@RequestParam(value="last_name",required=true) String l_name,
				@RequestParam(value="email",required=true) String email,
				@RequestParam(value="password",required=true) String password,
				@RequestParam(value="org",required=true) String org)
		{
			ModelMap model=new ModelMap();	
			try{
			userService.register(f_name, l_name, email, password, org);
			}
			
			catch(Exception e)
			{
				model.addAttribute("error","Duplicate Id's found!");
				return new ModelAndView("SignUp",model);
				
			}
			model.addAttribute("message","User resgistered sucessfully");
			return new ModelAndView("LogIn",model);
			
		}
		
		@PostMapping("/api/user/login")
		public void login(
				@RequestParam(value="email",required=true) String email,
				@RequestParam(value="password",required=true) String password,
				HttpServletResponse response,
				HttpServletRequest request
		)
		{
			ModelMap model=new ModelMap();	
			User user;
			
			user=userService.login(email, password);
			
			if(user==null){
				model.addAttribute("error","Invalid Login");
			}
			else{
				System.out.println("user:"+user.toString());
				model.addAttribute("user",user);
				try {
					response.sendRedirect("/api/instance/getUserInstances?user_id="+user.getUser_id());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		
}
