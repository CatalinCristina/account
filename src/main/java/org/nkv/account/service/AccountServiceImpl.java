package org.nkv.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.nkv.account.domain.Account;
import org.nkv.account.domain.User;
import org.nkv.account.exception.EntityNotFoundException;
import org.nkv.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final ConcurrentHashMap<String, BigDecimal> cache;
    private final AccountRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public Account getByUser(User user) {
        Optional<Account> optAccount = repository.findById(user.getId());
        if (!optAccount.isPresent()) {
            throw new EntityNotFoundException("Account not found");
        }
        Account account = optAccount.get();
        convert(account);
        return account;

    }

    private void convert(Account account) {
        account.setBalance(account.getBalance().divide(getExchangeRate(account), 4, RoundingMode.CEILING));
        account.setCurrency("EUR");
        log.info("cache = {}", cache.get(account.getUpdate().toString()));
    }

    @SuppressWarnings("unchecked")
    @HystrixCommand(fallbackMethod = "reliable")
    private BigDecimal getExchangeRate(Account account) {
        String day = account.getUpdate().minusDays(count(account.getUpdate().getDayOfWeek())).toString();
        if (cache.containsKey(day)) {
            account.setUpdate(LocalDate.parse(day.subSequence(0, day.length())));
            return (BigDecimal) cache.get(day);
        }
        URI uri = URI.create("https://api.exchangeratesapi.io/latest");
        Map<String, Object> responseBody = restTemplate.getForObject(uri, HashMap.class);
        BigDecimal rate = BigDecimal.valueOf((Double) ((HashMap<String, Object>) responseBody.get("rates")).get("RON"));
        String date = responseBody.get("date").toString();
        log.info("Rate = {}, date = {}", rate, date);
        cache.putIfAbsent(date, rate);
        account.setUpdate(LocalDate.parse(date.subSequence(0, date.length())));
        return rate;
    }

    public BigDecimal reliable() {
        return new BigDecimal(4.8484);
    }

    private long count(DayOfWeek day) {
        switch (day) {
            case SUNDAY:
                return 2L;
            case SATURDAY:
            default:
                return 1L;
        }
    }
}