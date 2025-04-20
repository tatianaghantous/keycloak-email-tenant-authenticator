# Email-Tenant Authenticator for Keycloak

A custom Keycloak Authenticator SPI that allows users to log in based on both their **email** and a **tenant ID**, built for multi-tenant environments.

---

## 🔧 How to Use

1. **Build the JAR**:
   ```bash
   mvn clean package
   ```

2. **Deploy to Keycloak**:
   Copy the generated JAR to the Keycloak `providers` directory:
   ```
   /opt/keycloak/providers/email-tenant-authenticator.jar
   ```

3. **Update Realm Settings**:
   Go to **Realm Settings → Login** and configure the following:
   - **Email as username**: Off  
   - **Login with email**: Off  
   - **Duplicate emails**: On  

4. **Username Format**:
   When creating users, use this format for usernames:
   ```
   user@example.com__tenant-id
   ```

5. **User Attributes**:
   Add a `tenant_id` attribute to each user’s profile to link users to their respective tenant.

6. **Authentication Flow**:
   - Go to **Authentication → Flows**
   - Create a new flow or duplicate an existing one (like `Browser` or `Direct Grant`)
   - Add execution step using `email-tenant-authenticator` and position it **before password verification**

---

## ✅ Advantages

- Allows the **same email** to exist across multiple tenants.
- Users can log in with **different credentials per tenant**.
- Supports multi-tenant SaaS systems with clear user isolation.

---

## ⚠️ Limitations

- You need to **customize** additional flows:
  - Forgot password
  - Email verification
  - External auth providers (e.g., Google)

- Keycloak’s default login page does **not support duplicate emails** correctly.
  Use a **custom login frontend** and authenticate using the REST API.

---

## 📌 Requirements

- **Keycloak 18+**
- **Java 17**
- Realm Settings:
  - **Email as username**: Off  
  - **Login with email**: Off  
  - **Duplicate emails**: On  

---
