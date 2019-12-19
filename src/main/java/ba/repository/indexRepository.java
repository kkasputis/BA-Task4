package ba.repository;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import ba.models.Imones;

@Repository
public class indexRepository {
	@Autowired
	ImonesImpl imonesImpl;

	public void getAllInfo() throws IOException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String source = "tempZip.zip";
		String destination = "tempUnzip/";
		String fileName = "monthly-" + year + ".json";
		URL url = new URL("http://atvira.sodra.lt/imones/downloads/" + year + "/monthly-" + year + ".json.zip");
		if (((HttpURLConnection) url.openConnection()).getResponseCode() == 200) {
			DownloadAndSaveInfo(year, source, destination, fileName);
		} else {
			DownloadAndSaveInfo(year - 1, source, destination, fileName);
		}


	}

	public String checkDate() throws IOException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		URL url = new URL("http://atvira.sodra.lt/imones/downloads/" + year + "/monthly-" + year + ".json.zip");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		long date = httpCon.getLastModified();
		if (date == 0) {
			year = year - 1;
			url = new URL("http://atvira.sodra.lt/imones/downloads/" + year + "/monthly-" + year + ".json.zip");
			httpCon = (HttpURLConnection) url.openConnection();
			date = httpCon.getLastModified();
		} 

		String data = sdf.format(httpCon.getLastModified());
		return data;
	}

	public void DownloadAndSaveInfo(int year, String source, String destination, String fileName)
			throws MalformedURLException, IOException {
		System.out.println("Downloading...");
		InputStream in = new URL("http://atvira.sodra.lt/imones/downloads/" + year + "/monthly-" + year + ".json.zip")
				.openStream();
		Files.copy(in, Paths.get(source), StandardCopyOption.REPLACE_EXISTING);

		System.out.println("Unzipping...");
		try {
			ZipFile zipFile = new ZipFile(source);

			zipFile.extractAll(destination);
			
		} catch (ZipException e) {
			e.printStackTrace();
		}

		File file = new File(destination + fileName);


		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println("Adding to database...");
		List<Imones> imonesList = Arrays
				.asList(objectMapper.readValue(new File(destination + fileName), Imones[].class));
		imonesImpl.saveAll(imonesList);
		File deleteZip = new File(source);
		deleteZip.delete();
		file.delete();
	}

	public String convertToCSV(String[] data) {
		return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
	}

	public String escapeSpecialCharacters(String data) {
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}

	public void writeToCSV(List<Imones> imone, int ik) throws IOException {
		List<String[]> dataLines = new ArrayList<>();
		dataLines.add(new String[] { "lookupId", "Draudėjo kodas", "Juridinių asmenų registro kodas", "Pavadinimas", "Trumpas pavadinimas", "Mėnuo", "Vidutinis darbo užmokestis", "Vidutinis darbo užmokestis 2",
				"Apdraustųjų skaičius", "Apdraustųjų skaičius 2", "Valstybinio socialinio draudimo įmoka ", "Ekonominės veiklos rūšies pavadinimas", "Ekonominės veiklos rūšies kodas", "Savivaldybė" });
		for (Imones tempImone : imone) {
			String tempEcoActName;
			if (tempImone.getEcoActName() == null) {
				tempEcoActName = "";
			}
			else {
				tempEcoActName = tempImone.getEcoActName();
			}
			dataLines.add(new String[] { String.valueOf(tempImone.getLookupId()), String.valueOf(tempImone.getCode()),
					String.valueOf(tempImone.getJarCode()), tempImone.getName(), tempImone.getShortname(),
					String.valueOf(tempImone.getMonth()), String.valueOf(tempImone.getAvgWage()),
					String.valueOf(tempImone.getAvgWage2()), String.valueOf(tempImone.getNumInsured()),
					String.valueOf(tempImone.getNumInsured2()), String.valueOf(tempImone.getTax()),
					tempEcoActName, String.valueOf(tempImone.getEcoActCode()),
					tempImone.getMunicipality() });
		}
	    File csvOutputFile = new File("src\\main\\webapp\\csvfiles\\" + ik + ".csv");
	    
	    try (PrintWriter pw = new PrintWriter(csvOutputFile, "UTF-8")) {
	        dataLines.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	       
	    }
	}
}

