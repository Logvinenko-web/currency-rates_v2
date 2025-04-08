package com.anton.rate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.anton.rate.entity.Crypto;
import com.anton.rate.entity.Fiat;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class RatesRepositoryTests extends BaseFunctionalTest{


    @Test
    void checkFiatInDatabase() {

        Fiat fiat = new Fiat();
        fiat.setCurrency("USD");
        fiat.setRate(37.12);

        Fiat saved = fiatCurrencyRepository.save(fiat).block();

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("USD", saved.getCurrency());
        assertEquals(37.12, saved.getRate());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void checkCryptoInDatabase() {

        Crypto crypto = new Crypto();
        crypto.setCurrency("USD");
        crypto.setRate(37.12);

        Crypto saved = cryptoCurrencyRepository.save(crypto).block();

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("USD", saved.getCurrency());
        assertEquals(37.12, saved.getRate());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void checkInsertNewCurrency() {
        String currency = "USD";
        Double rate = 37.12;
        String source = "fiat";

        StepVerifier.create(lastCurrencyRepository.saveOrUpdateLatestRate(currency, rate, source))
            .verifyComplete();

        StepVerifier.create(lastCurrencyRepository.findLastCurrency(source))
            .assertNext(lastCurrency -> {
                assertEquals( "USD", lastCurrency.getCurrency());
                assertEquals(37.12, lastCurrency.getRate());
                assertEquals("fiat", lastCurrency.getSource());
            })
            .verifyComplete();
    }

    @Test
    void checkUpdateExistingCurrency() {
        String currency = "USD";
        Double initialRate = 37.12;
        String source = "fiat";

        StepVerifier.create(lastCurrencyRepository.saveOrUpdateLatestRate(currency, initialRate, source))
            .verifyComplete();

        StepVerifier.create(lastCurrencyRepository.saveOrUpdateLatestRate(currency, 40.55, source))
            .verifyComplete();

        StepVerifier.create(lastCurrencyRepository.findLastCurrency(source))
            .assertNext(lastCurrency -> {
                assertEquals( "USD", lastCurrency.getCurrency());
                assertEquals(40.55, lastCurrency.getRate());
                assertEquals("fiat", lastCurrency.getSource());
            })
            .verifyComplete();
    }
}
