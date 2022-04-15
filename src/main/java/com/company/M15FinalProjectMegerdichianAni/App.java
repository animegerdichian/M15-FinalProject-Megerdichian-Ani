package com.company.M15FinalProjectMegerdichianAni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
			System.out.println("\nAPI Retriever");
			System.out.println("1. Weather by City");
			System.out.println("2. Location of ISS");
			System.out.println("3. Weather at ISS Location");
			System.out.println("4. Crypto");
			System.out.println("5. Exit");

			int userChoice = 0;

			do{

				try{
					System.out.print("Please make a selection: ");
					userChoice = Integer.parseInt(scanner.nextLine());

				}
				catch(NumberFormatException e){
					userChoice = 0;
				}

				if(userChoice < 1 || userChoice > 5){
					System.out.println("Incorrect selection; must be an integer in range 1-5!");
				}

			}while(userChoice < 1 || userChoice > 5);


			switch(userChoice){
				case 1:
					weatherByCityOutput();
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

	// get a city as input from the user
	public static String getCityFromUser(){
		System.out.print("\nPlease enter a city: ");
		Scanner scanner = new Scanner(System.in);
		String userCity = scanner.nextLine();
		return userCity;
	}

	// get a WeatherResponse given an URI
	public static WeatherResponse getWeatherResponse(String weatherURI){

		WebClient client = WebClient.create(weatherURI);
		WeatherResponse weatherResponse = null;
		try{
			// get response
			Mono<WeatherResponse> response = client
					.get()
					.retrieve()
					.bodyToMono(WeatherResponse.class);

			 weatherResponse = response.share().block();
		}
		catch (WebClientResponseException we) {
			int statusCode = we.getRawStatusCode();
			if (statusCode >= 400 && statusCode <500){
				System.out.println("Client Error");
			}
			else if (statusCode >= 500 && statusCode <600){
				System.out.println("Server Error");
			}
			//System.out.println("Message: " + we.getMessage());
		}


		return weatherResponse;

	}

	// prints pertinent info given a WeatherResponse
	public static void printWeatherReport(WeatherResponse weatherResponse){
		if(weatherResponse == null){
			return;
		}
		System.out.println("\nWeather Report");
		System.out.println("Current Temperature: " + weatherResponse.getMain().getTemp());
		System.out.println("Feels Like: " + weatherResponse.getMain().getFeels_like());
		System.out.println("Temperature Range: " + weatherResponse.getMain().getTemp_min() + " - " + weatherResponse.getMain().getTemp_max());
		System.out.println("Humidity: " + weatherResponse.getMain().getHumidity());
	}

	// option 1
	public static void weatherByCityOutput(){

		WeatherResponse weatherResponse = null;
		do{
			String cityName = getCityFromUser();
			String weatherURI = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=ca4ebfaa6ee730995228e42c6b18719d&units=imperial";
			weatherResponse = getWeatherResponse(weatherURI);

		}while(weatherResponse == null);
		printWeatherReport(weatherResponse);

	}


	// return the iss location SpaceResponse
	public static SpaceResponse getSpaceResponse(){

		String issURI = "http://api.open-notify.org/iss-now.json";
		WebClient client = WebClient.create(issURI);

		SpaceResponse spaceResponse = null;
		try{
			// get response
			Mono<SpaceResponse> response = client
					.get()
					.retrieve()
					.bodyToMono(SpaceResponse.class);

			spaceResponse = response.share().block();
		}
		catch (WebClientResponseException we) {
			int statusCode = we.getRawStatusCode();
			if (statusCode >= 400 && statusCode <500){
				System.out.println("Client Error");
			}
			else if (statusCode >= 500 && statusCode <600){
				System.out.println("Server Error");
			}
			//System.out.println("Message: " + we.getMessage());
		}
		return spaceResponse;
	}


	// prints ISS location info
	public static void printISSLocationReport(SpaceResponse spaceResponse, WeatherResponse weatherResponse){
		if(spaceResponse == null || weatherResponse == null){
			System.out.println("Sorry, ISS location was not found!");
			return;
		}
		// print iss coordinates
		System.out.print("\nISS Coordinates: (");
		System.out.print(spaceResponse.getIss_position().getLongitude() + " , ");
		System.out.println(spaceResponse.getIss_position().getLatitude() + ")");

		// print iss city, country
		if(weatherResponse.getSys() == null || weatherResponse.getName().equals("")){
			System.out.println("The ISS is not in a country right now!");
		}
		else{
			System.out.println("Location: " + weatherResponse.getName() + ", " + weatherResponse.getSys().getCountry());
		}


	}

	// used for options 2 and 3
	// prints iss coordinates, city, country and returns weatherResponse to be used for option 3
	public static WeatherResponse issLocationOutput(){
		SpaceResponse spaceResponse = getSpaceResponse();
		WeatherResponse weatherResponse = null;
		if(spaceResponse != null){
			String weatherURI = "https://api.openweathermap.org/data/2.5/weather?lat="+
					spaceResponse.getIss_position().getLatitude() + "&lon=" + spaceResponse.getIss_position().getLongitude() + "&appid=ca4ebfaa6ee730995228e42c6b18719d";
			weatherResponse = getWeatherResponse(weatherURI);

			printISSLocationReport(spaceResponse, weatherResponse);
		}

		return weatherResponse;
	}


	public static void issWeatherOutput(){
		WeatherResponse weatherResponse = issLocationOutput();
		// print weather info
		printWeatherReport(weatherResponse);

	}

	// CRYPTO
	public static String getCryptoFromUser(){
		System.out.print("\nPlease enter a cryptocurrency symbol: ");
		Scanner scanner = new Scanner(System.in);
		String userCrypto = scanner.nextLine();
		return userCrypto;
	}

	public static CryptoResponse getCryptoResponse(String cryptoURI){
		WebClient client = WebClient.create(cryptoURI);

		CryptoResponse cryptoResponse = null;
		try{
			Mono<CryptoResponse[]> response = client
					.get()
					.retrieve()
					.bodyToMono(CryptoResponse[].class);


			cryptoResponse = response.share().block()[0];

		}
		catch(ArrayIndexOutOfBoundsException e){

			cryptoResponse = null;
		}
		catch (WebClientResponseException we) {
			int statusCode = we.getRawStatusCode();
			if (statusCode >= 400 && statusCode <500){
				System.out.println("Client Error");
			}
			else if (statusCode >= 500 && statusCode <600){
				System.out.println("Server Error");
			}
			//System.out.println("Message: " + we.getMessage());
		}


		return cryptoResponse;
	}


	public static void printCryptoReport(CryptoResponse cryptoResponse){
		if(cryptoResponse == null){
			System.out.println("\nSorry, cryptocurrency was not found.");
			return;
		}
		System.out.println("\nName: " + cryptoResponse.getName());
		System.out.println("Symbol: " + cryptoResponse.getAsset_id());
		System.out.format("%7s%.2f\n","Price: $", cryptoResponse.getPrice_usd());

	}

	public static void cryptoOutput(){
		String asset_id = getCryptoFromUser();
		String cryptoURI = "https://rest.coinapi.io/v1/assets/" + asset_id + "?apikey=891FBB85-F2B7-40A5-A203-A6962E71B4CB";
		CryptoResponse cryptoResponse = getCryptoResponse(cryptoURI);
		printCryptoReport(cryptoResponse);
	}


}
