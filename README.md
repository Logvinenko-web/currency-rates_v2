# Currency Rates 

### 🔧 Технології 
1. Java 21
2. Spring Boot
3. R2DBC
4. PostgreSQL
5. Liquibase
6. Docker & Docker Compose

### Endpoints

#### GET `/currency-rates`
- **Response:**
    - **Success (200):** 
```json
    {
    "fiat": [
        {
            "currency": "GBP",
            "rate": 59.76642338420838
        },
        {
            "currency": "EUR",
            "rate": 5.4331016264873995
        },
        {
            "currency": "USD",
            "rate": 39.794756910090825
        }
    ],
    "crypto": [
        {
            "currency": "ETH",
            "rate": 91381.76219912404
        },
        {
            "currency": "LTC",
            "rate": 48497.311672791555
        },
        {
            "currency": "BTC",
            "rate": 74792.6102768552
        }
    ]
} 
```
#### Data not retrieved from the crypto endpoint and does not exist in DB
- **Response:**
    - **Success (200):** 
```json
{
  "fiat": [
    {
      "currency": "GBP",
      "rate": 59.76642338420838
    },
    {
      "currency": "EUR",
      "rate": 5.4331016264873995
    },
    {
      "currency": "USD",
      "rate": 39.794756910090825
    }
  ],
  "crypto": []
}
```
#### Data not retrieved from any endpoint and no records in DB
- **Response:**
    - **Success (200):** 
```json
{
  "fiat": [],
  "crypto": []
}
```

### 🚀 Running the Application

1. **Run the application:**
```
docker-compose up -d
```
2. **Run Fiat and Crypto Rates API on port 8090**
### 📖 Example Requests
**Fetch Currency Rates:**

 ```sh
curl http://localhost:8080/currency-rates
```