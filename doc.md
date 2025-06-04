# NITC Connect - Backend


## Instructions to clone and run

### 1. Clone the Repository

```bash
git clone https://github.com/GDSC-NIT-Calicut/nitc-connect-backend.git
cd nitc-connect-backend
```

### 2. Create the `.env` File

Copy `.env.example` and fill the details for your db username and password

```
cp .env.example .env
```
Do **not** commit this file. It is listed in `.gitignore`.

### 3. Set Up PostgreSQL

Ensure PostgreSQL is installed and running. Then create the database:

```sql
CREATE DATABASE nitcconnect;
```


### 4. Build and Run the App

Use Maven to compile and run the app:

```bash
./mvnw spring-boot:run
```

Or, if using IntelliJ IDEA, right-click on `NitcConnectApplication.java` and run it.

---

## Example `.env`

```env
DB_URL=jdbc:postgresql://localhost:5432/nitcconnect
DB_USERNAME=postgres
DB_PASSWORD=admin
```

(Replace with your actual credentials.)


---

## 📄 License

[MIT](LICENSE)
