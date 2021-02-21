package io.covidtracker.coronavirustracker.services;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.covidtracker.coronavirustracker.model.LocationStats;

@Service
public class CoronaVirusDataService {
	private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private List<LocationStats> allStats=new ArrayList<>();
	
	@Scheduled(cron="* * 1 * *")//Every minute the job runs.
	@PostConstruct
	public void fetchVirusData() throws IOException, InterruptedException
	{
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
		HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
		List<LocationStats> locationStatsList=new ArrayList<LocationStats>();
		StringReader csvReader=new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
		for (CSVRecord record : records) {
			LocationStats locationStats=new LocationStats();
			locationStats.setRegion(record.get("Country/Region").isEmpty()?"NA":record.get("Country/Region"));
			locationStats.setState(record.get("Province/State").isEmpty()?"NA":record.get("Province/State"));
			locationStats.setLatitude(record.get("Lat").isEmpty()?0.0:Double.valueOf(record.get("Lat").toString()));
			locationStats.setLongitude(record.get("Long").isEmpty()?0.0:Double.valueOf(record.get("Long").toString()));
			locationStats.setTotalCases(record.get(record.size()-1).isEmpty()?new BigInteger("0"):new BigInteger(record.get(record.size()-1).toString()));
			locationStats.setDiffFromPrevDay(Integer.valueOf(setDiffValue(record)));
			locationStatsList.add(locationStats);
		}
		this.allStats=locationStatsList;
		
	}

	private String setDiffValue(CSVRecord record){
		return (record.get(record.size()-1).isEmpty() ?new BigInteger("0"):new BigInteger(record.get(record.size()-1)).subtract(record.get(record.size()-2).isEmpty()?new BigInteger("0"):new BigInteger(record.get(record.size()-2)))).abs().toString();
		}

	public List<LocationStats> getAllStats() {
		return allStats;
	}
}
