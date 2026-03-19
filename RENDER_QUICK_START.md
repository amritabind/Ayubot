# 🚀 Quick Render Deployment Checklist

## Files Already Prepared ✅
- ✅ `render.yaml` - Render configuration file
- ✅ `config.js` - Frontend dynamic API configuration  
- ✅ `application-render.properties` - Backend production configuration
- ✅ `.gitignore` - Proper Git ignore rules
- ✅ All frontend files updated to use dynamic API URL

## Pre-Deployment Steps

### 1. Prepare Your Repository
```bash
# Initialize/update Git if not already done
git init
git add .
git commit -m "Prepare for Render deployment"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/ayubot.git
git push -u origin main
```

### 2. Create GitHub Repository
1. Go to https://github.com/new
2. Create repository named `ayubot`
3. Push your code to GitHub

### 3. Sign Up for Render
1. Go to https://dashboard.render.com
2. Sign up with GitHub account
3. Connect your GitHub profile

---

## Deployment Steps

### Step 1: Create PostgreSQL Database (FREE)
1. Dashboard → **New +** → **PostgreSQL**
2. Fill in:
   - **Name**: `ayubot-db`
   - **Database**: `ayubot`
   - **User**: `ayubot`
   - **Plan**: Free
3. Click **Create Database**
4. **Copy the connection string** (you'll need this later)

### Step 2: Deploy Backend
1. Dashboard → **New +** → **Web Service**
2. **Connect Repository**:
   - Select your `ayubot` GitHub repository
   - Click **Connect**

3. **Configure Service**:
   - **Name**: `ayubot-backend`
   - **Runtime**: Java
   - **Build Command**: 
     ```
     cd backend/ayubot && mvn clean package -DskipTests
     ```
   - **Start Command**: 
     ```
     java -jar target/ayubot-0.0.1-SNAPSHOT.jar
     ```
   - **Plan**: Free (scroll down to find)
   - **Root Directory**: Leave empty

4. **Click Advanced** (Important!)

5. **Add Environment Variables** (Click **Add Environment Variable**):
   ```
   JAVA_VERSION = 21
   PORT = 10000
   SPRING_PROFILES_ACTIVE = render
   SPRING_DATASOURCE_URL = <paste_your_database_connection_string>
   SPRING_DATASOURCE_USERNAME = ayubot
   SPRING_DATASOURCE_PASSWORD = <paste_database_password>
   GROQ_API_KEY = <your_groq_api_key>
   ```

6. **Click Create Web Service**
7. Wait for deployment (10-15 minutes)
8. **Copy the deployed URL** - looks like `https://ayubot-backend.onrender.com`

### Step 3: Deploy Frontend on Vercel (RECOMMENDED)
1. Go to https://vercel.com
2. **New Project**
3. Import your GitHub repository
4. **Configure**:
   - **Project Name**: `ayubot-frontend`
   - **Framework**: Next.js or Other (static)
   - **Root Directory**: `frontend`
   - **Build Command**: Leave empty
5. Click **Deploy**
6. Wait for deployment
7. **Copy your frontend URL** - looks like `https://ayubot-frontend.vercel.app`

### Step 4: Configure Backend for CORS
1. Go back to your Render backend service
2. Go to **Settings** → **Environment**
3. Add this environment variable:
   ```
   SPRING_WEB_CORS_ALLOWED_ORIGINS = https://your-frontend-url.vercel.app
   ```
4. Click **Save Changes** (backend will redeploy)

---

## Testing Your Deployment

### 1. Test Backend
Open in browser: `https://ayubot-backend.onrender.com/api`
(Should show an error or empty response, which is OK - means backend is running)

### 2. Test Frontend
1. Go to: `https://your-frontend-url.vercel.app`
2. Try logging in with test credentials:
   - Email: `patient@example.com`
   - Password: `password` (or whatever you set up)
3. Check browser console (F12) for any errors

### 3. Monitor Logs
- **Backend logs**: Render Dashboard → Your Service → Logs
- **Frontend**: Check browser console (F12)

---

## Troubleshooting

### Issue: "Cannot connect to backend"
**Solution**: 
- Check backend URL in `config.js` is correct
- Verify CORS settings are configured
- Check backend logs for errors

### Issue: Database connection error
**Solution**:
- Verify `SPRING_DATASOURCE_URL`, `_USERNAME`, `_PASSWORD` are correct
- Run database migrations if needed

### Issue: "Service is starting, please try again later"
**Solution**: 
- This is normal - Render free tier spins down after 15 minutes of inactivity
- Wait 2-3 minutes and refresh

### Issue: Slow first load (30+ seconds)
**Solution**: 
- This is normal for Java on free tier
- Cold starts take time - upgrade to paid plan to avoid this

---

## Environment Variables Explained

| Variable | Purpose | Example |
|----------|---------|---------|
| `JAVA_VERSION` | Java runtime version | `21` |
| `PORT` | Server port | `10000` |
| `SPRING_DATASOURCE_URL` | Database connection URL | From PostgreSQL dashboard |
| `SPRING_DATASOURCE_USERNAME` | Database user | `ayubot` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | From PostgreSQL dashboard |
| `GROQ_API_KEY` | AI chat API key | Your Groq API key |
| `SPRING_WEB_CORS_ALLOWED_ORIGINS` | Allowed frontend URLs | Your Vercel URL |

---

## Next Steps After Deployment

1. **Update test credentials** in database with real users
2. **Migrate production data** if you have it
3. **Set up backups** for your database
4. **Monitor usage** and upgrade plan if needed
5. **Configure custom domain** (optional)
6. **Set up SSL certificate** (auto-included on Render)

---

## Cost Information

**Free Tier Includes**:
- 750 compute-hours/month
- 5GB bandwidth/month  
- 1 database (PostgreSQL)

**After Free Tier**:
- Web Service: $7/month
- PostgreSQL: $7/month
- Bandwidth: $0.10/GB after 5GB

---

## Support

- **Render Docs**: https://render.com/docs
- **Render Support**: https://support.render.com
- Check your Render Dashboard for deployment logs and status

**You're all set! 🎉**
