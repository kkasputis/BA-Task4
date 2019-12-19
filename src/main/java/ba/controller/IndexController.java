package ba.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ba.models.Imones;
import ba.models.InfoUpdateDate;
import ba.models.Search;
import ba.repository.ImonesImpl;
import ba.repository.InfoUpdateDateImpl;
import ba.repository.indexRepository;

@Controller
public class IndexController {
	@Autowired
	indexRepository indexRepository;
	@Autowired
	ImonesImpl imonesImpl;
	@Autowired
	InfoUpdateDateImpl infoUpdateDate;

	@RequestMapping()
	public String indexList(Model model) throws ParseException, IOException {
		System.out.println("Atidaromas index'as");
		if ((infoUpdateDate.existsById(1))
				&& (indexRepository.checkDate().equals(infoUpdateDate.findById(1).get().getDate()))) {
			model.addAttribute("search", new Search());
			return "index";
		}

		else {
			InfoUpdateDate newDate;
			try {
				if (infoUpdateDate.existsById(1)) {
					newDate = infoUpdateDate.findById(1).get();
				} else {
					newDate = new InfoUpdateDate();
				}
				newDate.setDate(indexRepository.checkDate());
				newDate.setId(1);
				infoUpdateDate.save(newDate);
				imonesImpl.deleteAllInBatch();
				indexRepository.getAllInfo();
			} catch (IOException e) {
	
				e.printStackTrace();
			}

			return "index";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addCatPost(@ModelAttribute("search") Search search, HttpServletRequest request) {
		System.out.println("Paspaudė ieškoti");
		if (search.getIk() > 0) {
			return "redirect:imone?ik=" + search.getIk();
		} else if (search.getAvgWage() > 0) {
			return "redirect:imone?wage=" + search.getAvgWage();
		} else if (search.getName() != null) {
			return "redirect:imone?name=" + search.getName();
		}

		else {
			return "redirect:error";
		}

	}

	@RequestMapping("/imone")
	public String getProductById(@RequestParam(value = "wage", defaultValue = "0") float wage,
			@RequestParam(value = "ik", defaultValue = "0") long ik,
			@RequestParam(value = "li", defaultValue = "0") int li,
			@RequestParam(value = "name", defaultValue = "false") String name, Model model,
			HttpServletRequest request) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String timeOfAction = dateFormat.format(date);
		if (ik > 0) {
			List<Imones> imonesIrasai = imonesImpl.findAllByJarCode(ik);
			if (imonesIrasai.size() < 1) {
				System.out.println("["+ timeOfAction + "]Gavo errora: Įmonė, tokiu įmonės kodu nerasta.  Iškojo: " + ik);
				model.addAttribute("klaida", "Įmonė, tokiu įmonės kodu nerasta.");
				return "forward:error";
			} else {
				System.out.println("["+ timeOfAction + "]Atidare imone pagal IK: " + ik);
				model.addAttribute("imonesIrasai", imonesIrasai);
				return "imone";
			}
		} else if (wage > 0) {
			List<Imones> imonesIrasai = imonesImpl.findAllWageFrom(wage);
			if (imonesIrasai.size() < 1) {
				System.out.println("["+ timeOfAction + "]Gavo errora: Pasvajok! Įmonių su tokiais atlyginimais nėra! Iškojo: " + wage);
				model.addAttribute("klaida", "Pasvajok! Įmonių su tokiais atlyginimais nėra!");
				return "forward:error";
			} 
			else if (imonesIrasai.size() > 1000) {
				System.out.println("["+ timeOfAction + "]Gavo errora: Imonių su tokiu ir didesniu vidutiniu atlyginimu rasta daugiau nei 1000. Sukonkretinkite paiešką. Ieškojo: " + wage);
				model.addAttribute("klaida", "Imonių su tokiu ir didesniu vidutiniu atlyginimu rasta daugiau nei 1000. Ieškok bent nuo 6000eur :)");
				return "forward:error";
			} 
			
			else {
				Set<Long> set = new HashSet<Long>();
				System.out.println("["+ timeOfAction + "]Gavo imonius sarasa pagal atlyginima nuo: " + wage);
				imonesIrasai.removeIf(p -> !set.add(p.getJarCode()));
				model.addAttribute("imonesIrasai", imonesIrasai);
				return "imoniusarasas";
				
			}
			


		}
		else if (li > 0) {
			List<Imones> imonesIrasai = imonesImpl.findAllByLookupId(li);
			if (imonesIrasai.size() < 1) {
				System.out.println("["+ timeOfAction + "]Bandant atidaryti įmonę pagal LI Gavo errora: Įvyko klaida, tokia įmonė nerasta" + li);
				model.addAttribute("klaida", "Įvyko klaida, tokia įmonė nerasta");
				return "forward:error";
			} 

			
			else {
				model.addAttribute("imonesIrasai", imonesIrasai);
				System.out.println("["+ timeOfAction + "]Atidarė įmonę pagal LI:" + li);
				return "imone";
				
			}
		}
		
		
		else if (!name.equalsIgnoreCase("false")) {
			List<Imones> imonesIrasai = imonesImpl.findByNameContainingIgnoreCase(name.toLowerCase());
			if (imonesIrasai.size() < 1) {
				System.out.println("["+ timeOfAction + "]Gavo errora: Įmonės tokiu pavadinimu nėra. " + name);
				model.addAttribute("klaida", "Įmonės tokiu pavadinimu nėra.");
				return "forward:error?id=3";
			} 
			else if (imonesIrasai.size() > 1000) {
				System.out.println("["+ timeOfAction + "]Gavo errora: Įmonės tokiu pavadinimu nėra. Ieškojo: " + name);
				model.addAttribute("klaida", "Įmoniu tokiu pavadinimu rasta daugiau nei 1000. Sukonkretinkite paiešką.");
				return "forward:error?id=3";
			} 
			else {
				Set<Integer> set = new HashSet<Integer>();
				System.out.println("["+ timeOfAction + "]Atidarė įmonių sąrašą: " + name);
				imonesIrasai.removeIf(p -> !set.add(p.getLookupId()));
				model.addAttribute("imonesIrasai", imonesIrasai);
				return "imoniusarasas";
			}
		}
		else {
			System.out.println("["+ timeOfAction + "]Gavo errora: Įvyko klaida");
			model.addAttribute("klaida", "Įvyko klaida.");
			return "forward:error";
		}

		

	}

	@RequestMapping("/download")
	public String download(@RequestParam(value = "li", defaultValue = "0") int li, Model model,HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("Atidrė download puslapį pagal LI:" + li);
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		List<Imones> imonesIrasai = imonesImpl.findAllByLookupId(li);
		if (imonesIrasai.size() < 1) {
			model.addAttribute("klaida", "Įvyko klaida");
			System.out.println("Gavo Errora: Įvyko klaida pagal LI:" + li);
			return "forward:error";
		} 
		else {

			model.addAttribute("imonesIrasai", imonesIrasai);
			indexRepository.writeToCSV(imonesIrasai, li);
			model.addAttribute("location", rootDirectory + "csvfiles\\");
			model.addAttribute("filename", li + ".csv");
			return "download";
	}
	}
}
