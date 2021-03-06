package org.nkv.account;

import java.math.BigDecimal;

import org.nkv.account.domain.Account;
import org.nkv.account.domain.Currency;
import org.nkv.account.domain.User;
import org.nkv.account.repository.AccountRepository;
import org.nkv.account.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCircuitBreaker
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, AccountRepository accountRepository) {
		return (args) -> {
			User user = new User("ionescu");
			Account account = new Account();
			account.setIban("RO77RZBR6000XXXX4612YYYY");
			account.setCurrency(Currency.RON.toString());
			account.setBalance(BigDecimal.valueOf(901.4334));
			user.setAccount(account);
			user = userRepository.save(user);
			log.info("{}", userRepository.findByUsername("ionescu").get().getAccount());
		};
	}
}
