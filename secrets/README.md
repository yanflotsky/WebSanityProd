# Docker Secrets Setup

This folder contains sensitive credentials for Docker containers.

## ⚠️ IMPORTANT SECURITY NOTICE

**Files in this folder should NEVER be committed to Git!**

This directory is already in `.gitignore`.

## Setup Instructions

### 1. Create password file

Create a file named `teleadmin_password.txt` in this directory with your plain text password:

```bash
# Windows PowerShell:
echo "YourActualPassword" > secrets/teleadmin_password.txt

# Or manually create the file and paste your password (no quotes, just the password)
```

**Important:** 
- File should contain ONLY the password (no extra spaces or newlines)
- Use plain text password (not the encrypted one from pom.xml)
- The file will be mounted as `/run/secrets/teleadmin_password` in Docker containers

### 2. Verify file exists

```bash
# Check if file exists
Test-Path secrets/teleadmin_password.txt

# View content (for testing only - don't share this!)
Get-Content secrets/teleadmin_password.txt
```

### 3. Run Docker tests

```bash
# SecureConfig will automatically read from /run/secrets/teleadmin_password
docker compose up
```

## Security Benefits

✅ Password never appears in docker-compose.yml  
✅ Password never appears in environment variables  
✅ Password never appears in logs  
✅ File is in .gitignore - won't be committed  
✅ Docker mounts secret as read-only file  
✅ Production-ready approach  

## How It Works

1. **Local (Windows):**
   - Uses encrypted passwords from pom.xml and config.properties
   - `SecureConfig.decrypt()` uses your machine's COMPUTERNAME

2. **Docker (with secrets):**
   - `USE_DOCKER_SECRETS=true` environment variable
   - `SecureConfig.decryptSecret()` reads `/run/secrets/teleadmin_password` and `/run/secrets/gmail_password`
   - Passwords stored securely in `./secrets/` folder (not in Git)

## Example File Structure

```
secrets/
├── README.md                    (this file - safe to commit)
├── .gitkeep                     (safe to commit)
└── teleadmin_password.txt       (❌ NEVER commit this!)
```

## Troubleshooting

### Error: "Docker secret file not found"

**Solution:** Create `secrets/teleadmin_password.txt` with your password

### Error: "Failed to read Docker secret"

**Solution:** Check file permissions and content format (plain text only)

### Password doesn't work

**Solution:** Make sure you're using the PLAIN TEXT password, not the encrypted one from pom.xml

