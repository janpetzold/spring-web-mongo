package de.janpetzold.app.controller;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import de.janpetzold.app.model.City;
import de.janpetzold.app.model.Customer;
import de.janpetzold.app.service.AppService;

// TODO: Enable Logging
@Controller
public class AppController {
    private AppService appService;
	
    // TODO: Maybe add a method that will create all necessary indexes. Protect that "somehow".
    
	@Autowired
	public void setCityService(AppService appService) {
		this.appService = appService;
	}
	
	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browseCities() {
		ModelAndView mv = new ModelAndView("browse");
		
		// Initially we list the top 10 cities
		mv.addObject("cities", appService.findBiggestCities().getContent());
		
		return mv;
	}
	
	@RequestMapping(value = "/addCity", method = RequestMethod.GET)
	public ModelAndView addCity() {
		City city = new City();
		
		ModelAndView mv = new ModelAndView("city");
		mv.addObject("city", city);
		
		return mv;
	}
	
	@RequestMapping(value = "/readCity/{cityName}", method = RequestMethod.GET, produces = "application/json; charset=utf-8") 
	@ResponseBody
	public City readCity(@PathVariable String cityName) {
		return appService.getCity(cityName.toLowerCase());
	}
	
	@RequestMapping(value = "/readCities/{countryCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8") 
	@ResponseBody
	public List<City> readByCountry(@PathVariable String countryCode) {
		return appService.getCitiesForCountry(countryCode);
	}
	
	@RequestMapping(value = "/readAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<City> readAll()  {
		return appService.listCities();
	}
	
	@RequestMapping(value = "/readBiggest", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<City> readBiggest()  {
		return appService.findBiggestCities().getContent();
	}
	
	@RequestMapping(value = "/readRandom", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public City readRandom()  {
		return appService.getRandom();
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public City add(@ModelAttribute("city") City city) {
		// TODO: Add validation
		return appService.saveCity(city);
	}
	
	@RequestMapping(value = "/readCustomers/{city}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Customer> readCustomers(@PathVariable String city)  {
		return appService.getCustomersForCity(city);
	}
	
	@RequestMapping(value = "/readLocation/{userName}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Collection<Map<String, Object>> readLocation(@PathVariable String userName)  {
		return appService.getLocationForUser(userName);
	}
	
}
