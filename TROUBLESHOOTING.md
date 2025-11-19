# Troubleshooting Guide

## Common Issues and Solutions

### Issue 1: Spring Boot Server Won't Start

**Symptoms:**
- Error when running `mvn spring-boot:run`
- Port 8080 already in use
- Database connection errors

**Solutions:**

1. **Port Already in Use:**
   - Check if port 8080 is already in use: `netstat -ano | findstr :8080`
   - Kill the process or change the port in `application.properties`: `server.port=8081`

2. **Maven Dependencies Not Downloaded:**
   - Run: `mvn clean install -U`
   - This will force Maven to download all dependencies

3. **Database Schema Issues:**
   - Check that `schema.sql` exists in `src/main/resources/`
   - Verify the SQL syntax is correct
   - Check the console for SQL errors

### Issue 2: JavaFX Client Can't Connect to Server

**Symptoms:**
- "Connection refused" errors
- Teams not loading
- Add/Edit/Delete operations fail

**Solutions:**

1. **Server Not Running:**
   - Make sure the Spring Boot server is running first
   - Check server logs for startup errors
   - Verify server is accessible at `http://localhost:8080`

2. **Wrong Server URL:**
   - Check `TeamRestClient.java` - BASE_URL should be `http://localhost:8080/api/teams`
   - If server is on different port, update the URL

3. **CORS Issues (if accessing from browser):**
   - Add CORS configuration to `TeamController.java`

### Issue 3: Database Not Persisting Data

**Symptoms:**
- Data disappears after server restart
- H2 in-memory database loses data

**Note:** This is expected behavior with H2 in-memory database. To persist data:
- Change `spring.datasource.url` in `application.properties` from `jdbc:h2:mem:teamdb` to `jdbc:h2:file:./teamdb`

### Issue 4: Compilation Errors

**Symptoms:**
- Maven build fails
- Missing dependencies

**Solutions:**

1. **Clean and Rebuild:**
   ```bash
   mvn clean compile
   ```

2. **Update Dependencies:**
   ```bash
   mvn clean install -U
   ```

3. **Check Java Version:**
   - Ensure Java 17 or higher is installed
   - Verify with: `java -version`

### Issue 5: Running Both Applications

**Correct Order:**

1. **First:** Start Spring Boot Server
   ```bash
   mvn spring-boot:run
   ```
   Or run `TeamServerApplication` from your IDE

2. **Second:** Start JavaFX Client (in separate terminal/IDE run configuration)
   ```bash
   mvn javafx:run
   ```
   Or run `TeamManagerApp` from your IDE

### Testing the Server

1. **Check if server is running:**
   - Open browser: `http://localhost:8080/api/teams`
   - Should return empty array: `[]`

2. **Test with curl (if available):**
   ```bash
   curl http://localhost:8080/api/teams
   ```

3. **Check H2 Console:**
   - Open: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:teamdb`
   - Username: `sa`
   - Password: (empty)

### Logs

Check the following for error messages:
- Console output when starting the server
- `application.log` file in project root
- Spring Boot startup logs

