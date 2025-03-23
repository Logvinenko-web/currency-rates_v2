package com.anton.rate;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class RatesFunctionalTests extends BaseFunctionalTest {

    @Test
    @DisplayName("Checking valid currency response ")
    public void checkValidResponse () throws JSONException {

        mockGetSuccessResponse(FIAT_PATH, readAsString("fiat"));
        mockGetSuccessResponse(CRYPTO_PATH, readAsString("crypto"));

        var actual = toJson(getCurrency());
        var expected = readAsString("currency");

        JSONAssert.assertEquals(expected, actual, false);

    }

    @Test
    @DisplayName("Checking the currency response if Fiat is empty (Got internal error from fiat api)")
    public void checkEmptyFiat () {

        mockGetInternalErrorStatus(FIAT_PATH);
        mockGetSuccessResponse(CRYPTO_PATH, readAsString("crypto"));

        var response = getCurrency();

        assertTrue(response.getFiat().isEmpty());
    }

    @Test
    @DisplayName("Checking the currency response if Crypto is empty (Got internal error from crypto api)")
    public void checkEmptyCrypto () {

        mockGetInternalErrorStatus(CRYPTO_PATH);
        mockGetSuccessResponse(FIAT_PATH, readAsString("fiat"));

        var response = getCurrency();

        assertTrue(response.getCrypto().isEmpty());
    }

    @Test
    @DisplayName("Check last 3 data with fiat db")
    public void checkFiatDataWithDb () throws JSONException {

        mockGetSuccessResponse(FIAT_PATH, readAsString("fiat"));
        mockGetSuccessResponse(CRYPTO_PATH, readAsString("crypto"));

        getCurrency();

        mockGetInternalErrorStatus(FIAT_PATH);
        mockGetSuccessResponse(CRYPTO_PATH, readAsString("crypto-one"));

        var actual = toJson(getCurrency());
        var expected = readAsString("currency-crypto-one");

        JSONAssert.assertEquals(expected, actual, false);

    }

    @Test
    @DisplayName("Check last 3 data with crypto db")
    public void checkCryptoDataWithDb () throws JSONException {

        mockGetSuccessResponse(FIAT_PATH, readAsString("fiat"));
        mockGetSuccessResponse(CRYPTO_PATH, readAsString("crypto"));

        getCurrency();

        mockGetInternalErrorStatus(CRYPTO_PATH);
        mockGetSuccessResponse(FIAT_PATH, readAsString("fiat-one"));

        var actual = toJson(getCurrency());
        var expected = readAsString("currency-fiat-one");

        JSONAssert.assertEquals(expected, actual, false);

    }

}
