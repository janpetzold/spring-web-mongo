package de.janpetzold.app.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import de.janpetzold.app.service.CityService;

// TODO: Enable Logging
// TODO: Create index on city name
@Controller
public class AppController {
    private CityService cityService;
	
	@Autowired
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		model.addAttribute("serverTime", dateFormat.format(date));
		
		return "home";
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
		return cityService.getCity(cityName.toLowerCase());
	}
	
	@RequestMapping(value = "/readCities/{countryCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8") 
	@ResponseBody
	public List<City> readByCountry(@PathVariable String countryCode) {
		return cityService.getCitiesForCountry(countryCode);
	}
	
	@RequestMapping(value = "/readAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<City> readAll()  {
		return cityService.listCities();
	}
	
	@RequestMapping(value = "/readBiggest", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Page<City> readBiggest()  {
		return cityService.findBiggestCities();
	}
	
	@RequestMapping(value = "/readRandom", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public City readRandom()  {
		return cityService.getRandom();
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<City> test()  {
		return cityService.test();
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public City add(@ModelAttribute("city") City city) {
		// TODO: Add validation
		return cityService.saveCity(city);
	}
	
}
