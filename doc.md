
## Instructions to clone and run

> 🛠️ **Requirements**:  
> Make sure you have **Java 17+**, **Maven**, and **PostgreSQL** installed before proceeding.

### 1. Clone the Repository

```
git clone https://github.com/GDSC-NIT-Calicut/nitc-connect-backend.git
cd nitc-connect-backend
```

### 2. Create the `.env` File

Copy `.env.example` and fill in all required fields, including database credentials, Google OAuth keys, JWT secret, and mail credentials.

```
cp .env.example .env
```

### 3. Set Up PostgreSQL

Ensure PostgreSQL is installed and running. Then create the database:

```
CREATE DATABASE nitcconnect;
```


### 4. Build and Run the App

Use Maven to compile and run the app:

```
./mvnw spring-boot:run
```

Or, if using IntelliJ IDEA, right-click on `NitcConnectApplication.java` and run it.

---

## Example `.env`

```
# Database
DB_URL=jdbc:postgresql://localhost:5432/nitcconnect
DB_USERNAME=postgres
DB_PASSWORD=admin

# Google OAuth
OAUTH_CLIENT_ID=your_google_client_id
OAUTH_CLIENT_SECRET=your_google_client_secret

# JWT
JWT_SECRET=your_32_char_secret

# Mail (for sending 2FA codes)
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_email_app_password
```

(Replace with your actual credentials.)


---

## 📄 License

[MIT](LICENSE)
