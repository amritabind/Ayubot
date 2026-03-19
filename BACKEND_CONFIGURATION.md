# Backend Configuration for Render Deployment

## Database Configuration

### Option 1: Using PostgreSQL (RECOMMENDED for Render)

If you want to use Render's PostgreSQL database, add this dependency to `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

Update the database URL format in `application-render.properties`:
```properties
spring.datasource.url=jdbc:postgresql://hostname:5432/ayubot
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect
```

### Option 2: Keep Using MySQL (With External Server)

Your current setup uses MySQL connector-j, which is fine. You can use:
- PlanetScale (MySQL-compatible, free tier available)
- AWS RDS free tier
- Your own external MySQL server

---

## Environment Variables Required

When deploying to Render, set these in the Dashboard:

```
SPRING_DATASOURCE_URL=<database_connection_url>
SPRING_DATASOURCE_USERNAME=<database_user>
SPRING_DATASOURCE_PASSWORD=<database_password>
SPRING_PROFILES_ACTIVE=render
GROQ_API_KEY=<your_groq_api_key>
```

---

## Database Migration

### On First Deployment

1. Render will automatically run migrations based on these settings:
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   ```

2. To verify your database schema was created:
   - Go to Render PostgreSQL dashboard
   - Use Query Editor or connect with a client
   - Run: `\dt` to see all tables

### Running SQL Scripts

To run `database.sql` on your Render PostgreSQL:

```bash
# Install PostgreSQL client tools if needed
brew install postgresql  # macOS
# OR
apt-get install postgresql-client  # Linux

# Connect to your Render PostgreSQL
psql "postgresql://user:password@host:5432/ayubot" < database.sql
```

---

## Security Configuration

The `application-render.properties` includes:
- Secure cookie settings
- HTTPS enforcement
- CORS configuration
- SQL show-sql disabled for security

---

## Testing Database Connection

Add a test endpoint to verify database connectivity. You can check the backend logs in Render dashboard for connection errors.

---

## Important Notes

1. **Cold Start Times**: Java applications have 30-60 second cold starts on free tier
2. **Database Sleep**: Free tier databases may sleep after 7 days of inactivity
3. **Backup**: Render includes automatic backups - configure backup frequency

---

## Troubleshooting Database Issues

### Connection Timeout
- Check that database service is running (check Render dashboard)
- Verify firewall/network settings
- Ensure password is correct

### Table Not Found  
- Check `spring.jpa.hibernate.ddl-auto=update` is set
- Look at Render logs for SQL errors
- Verify SQL scripts match your current schema

### Data Loss After Redeploy
- This shouldn't happen if using persistent database
- Always backup important data before major changes

---

## Next: Frontend API Configuration

The frontend has been configured to automatically detect the API URL.

The `config.js` file in frontend/ will:
1. Check if running on localhost → use `http://localhost:8080/api`
2. Otherwise → use the same domain as frontend

No additional frontend configuration needed! ✨

---

## Deployment Checklist

- [ ] PostgreSQL dependency added to pom.xml (if using PostgreSQL)
- [ ] Environment variables configured in Render dashboard
- [ ] Database connection URL is correct
- [ ] GROQ_API_KEY is set
- [ ] Backend builds successfully (`mvn clean package`)
- [ ] Frontend API config loads correctly (check browser console)
- [ ] Can log in with test user
- [ ] Chat functionality works with Groq API

