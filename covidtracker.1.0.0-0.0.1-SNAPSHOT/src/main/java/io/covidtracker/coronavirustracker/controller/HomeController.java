package io.covidtracker.coronavirustracker.controller;
import java.math.BigInteger;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import io.covidtracker.coronavirustracker.services.CoronaVirusDataService;

@Controller
public class HomeController {
	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model)
	{
		
		BigInteger cases=coronaVirusDataService.getAllStats().stream().map(data -> data.getTotalCases()).reduce(BigInteger::add).get();
		String deltaIncrease=coronaVirusDataService.getAllStats().stream().map(data -> data.getDiffFromPrevDay()).reduce(0,Integer::sum).toString();
		model.addAttribute("totalReportedCases",cases.toString());
		model.addAttribute("locationStats",coronaVirusDataService.getAllStats());
		model.addAttribute("increasedCases",deltaIncrease);
		return "home";
	}

}
