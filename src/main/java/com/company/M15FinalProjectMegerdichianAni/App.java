package com.company.M15FinalProjectMegerdichianAni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Scanner;

/*
Crypto:
	input: symbol of a cryptocurrency (ex. BTC), the asset_id
	output: name, symbol, and current price of the currency
 */
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

		Scanner scanner = new Scanner(System.in);

		while(true){
			System.out.println("1. Weather by City");
			System.out.println("2. Location of ISS");
			System.out.println("3. Weather at ISS Location");
			System.out.println("4. Crypto");
			System.out.println("5. Exit");
			System.out.print("Please make a selection: ");
			int userChoice = Integer.parseInt(scanner.nextLine());
			System.out.println(userChoice);
			switch(userChoice){
				case 1:
					weatherByCity();
					break;
				case 2:
					issLocationOutput();
					break;
				case 3:
					issWeatherOutput();
					break;
				case 4:
					cryptoOutput();
					break;
				default:
					return;
			}
		}


	}

	public static WeatherResponse getWeatherResponse(String weatherURI){

		WebClient client = WebClient.create(weatherURI);

		// get response
		Mono<WeatherResponse> response2 = client
				.get()
				.retrieve()
				.bodyToMono(WeatherResponse.class);

		WeatherResponse weatherResponse = response2.share().block();
		return weatherResponse;

	}

	public static String getCityFromUser(){
		System.out.print("Please enter a city: ");
		Scanner scanner = new Scanner(System.in);
		String userCity = scanner.nextLine();
		return userCity;
	}
	public static void weatherByCity(){

		String cityName = getCityFromUser();
		String weatherURI = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=ca4ebfaa6ee730995228e42c6b18719d&units=imperial";
		WeatherResponse weatherResponse = getWeatherResponse(weatherURI);
		printWeatherReport(weatherResponse);

	}

	// used for options 2 and 3
	// prints iss coordinates, city, country and returns weatherResponse to be used for option 3
	public static WeatherResponse issLocationOutput(){
		SpaceResponse space = issLocation();
		WeatherResponse weatherResponse = issWeather(space.iss_position.latitude,space.iss_position.longitude);

		// print iss coordinates
		System.out.print("ISS Coordinates: (");
		System.out.print(space.iss_position.longitude + " , ");
		System.out.println(space.iss_position.latitude + ")");

		// print iss city, country
		if(weatherResponse.sys == null || weatherResponse.name.equals("")){
			System.out.println("The ISS is not in a country right now!");
		}
		else{
			System.out.println("Location: " + weatherResponse.name + ", " + weatherResponse.sys.country);
		}
		System.out.println("\n");
		return weatherResponse;
	}


	public static void issWeatherOutput(){
		WeatherResponse weatherResponse = issLocationOutput();
		// print weather info
		printWeatherReport(weatherResponse);

	}

	public static void printWeatherReport(WeatherResponse weatherResponse){
		System.out.println("Weather Report");
		System.out.println("Current Temperature: " + weatherResponse.main.temp);
		System.out.println("Feels Like: " + weatherResponse.main.feels_like);
		System.out.println("Temperature Range: " + weatherResponse.main.temp_min + " - " + weatherResponse.main.temp_max);
		System.out.println("Humidity: " + weatherResponse.main.humidity);
		System.out.println("\n");
	}
	// return the iss location response
	public static SpaceResponse issLocation(){

		String issURI = "http://api.open-notify.org/iss-now.json";
		WebClient client = WebClient.create(issURI);

		// get response
		Mono<SpaceResponse> response = client
				.get()
				.retrieve()
				.bodyToMono(SpaceResponse.class);

		SpaceResponse issResponse = response.share().block();

		return issResponse;
	}

	// return iss weather response
	public static WeatherResponse issWeather(String lat, String lon){
		String mv = "https://api.openweathermap.org/data/2.5/weather?lat=37.39&lon=-122.08&appid=ca4ebfaa6ee730995228e42c6b18719d";
		String weatherURI = "https://api.openweathermap.org/data/2.5/weather?lat="+
				lat + "&lon=" + lon + "&appid=ca4ebfaa6ee730995228e42c6b18719d";

		WebClient client = WebClient.create(weatherURI);

		// get response
		Mono<WeatherResponse> response2 = client
				.get()
				.retrieve()
				.bodyToMono(WeatherResponse.class);

		WeatherResponse weatherResponse = response2.share().block();
		return weatherResponse;

	}


	public static String getCryptoFromUser(){
		System.out.print("Please enter a cryptocurrency symbol: ");
		Scanner scanner = new Scanner(System.in);
		String userCrypto = scanner.nextLine();
		return userCrypto;
	}

	public static CryptoResponse getCryptoResponse(String cryptoURI){
		WebClient client = WebClient.create(cryptoURI);

		Mono<CryptoResponse[]> response = client
				.get()
				.retrieve()
				.bodyToMono(CryptoResponse[].class);


		CryptoResponse cryptoResponse = response.share().block()[0];

		return cryptoResponse;
	}

	public static void printCryptoReport(CryptoResponse cryptoResponse){
		System.out.println("Name: " + cryptoResponse.name);
		System.out.println("Symbol: " + cryptoResponse.asset_id);
		System.out.format("%7s%.2f\n","Price: $", cryptoResponse.price_usd);

	}

	public static void cryptoOutput(){
		String asset_id = getCryptoFromUser();
		String cryptoURI = "https://rest.coinapi.io/v1/assets/" + asset_id + "?apikey=891FBB85-F2B7-40A5-A203-A6962E71B4CB";
		CryptoResponse cryptoResponse = getCryptoResponse(cryptoURI);
		printCryptoReport(cryptoResponse);
	}


}
