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
public class M15FinalProjectMegerdichianAniApplication {

	public static void main(String[] args) {
		SpringApplication.run(M15FinalProjectMegerdichianAniApplication.class, args);

		Scanner scanner = new Scanner(System.in);
		boolean showMenu = true;

		while(true){
			System.out.println("1. Crypto");
			System.out.println("2. Exit");
			System.out.print("Please make a selection: ");
			int userChoice = Integer.parseInt(scanner.nextLine());
			switch(userChoice){
				case 1:
					crypto();
					break;
				default:
					return;
			}
		}


	}

	public static void crypto(){
		String asset_id = "ETH";
		String cryptoURI = "https://rest.coinapi.io/v1/assets/" + asset_id + "?apikey=891FBB85-F2B7-40A5-A203-A6962E71B4CB";
		WebClient client = WebClient.create(cryptoURI);

		Mono<CryptoResponse[]> response2 = client
				.get()
				.retrieve()
				.bodyToMono(CryptoResponse[].class);


		CryptoResponse cryptoResponse = response2.share().block()[0];


		System.out.println(cryptoResponse.name);
		System.out.println(cryptoResponse.asset_id);
		System.out.println(cryptoResponse.price_usd);


	}

}
