# Secure Authentication Module (OWASP Top 10 Mitigation)

## 📌 Project Overview
This project consists of a secure authentication module developed in Java that demonstrates practical mitigations against critical web application vulnerabilities, mapping directly to the **OWASP Top 10** framework. 

The application implements defense-in-depth concepts to prevent **SQL Injection (SQLi)** and **Brute Force Attacks** during the user authentication lifecycle.

---

## 🛠️ Security Mechanisms Implemented

### 1. SQL Injection Prevention (Parameterized Simulation)
* **The Vulnerability:** Traditional SQL Injections occur when untrusted user input is directly concatenated into database queries, allowing attackers to manipulate SQL commands.
* **The Mitigation:** This module simulates the usage of **Prepared Statements** (Parameterized Queries). By strictly separating code from data, any malicious payload (such as `' OR '1'='1`) is treated strictly as a literal string parameter, making query manipulation impossible.

### 2. Brute Force Mitigation (Rate Limiting & Account Lockout)
* **The Vulnerability:** Automated bots trying thousands of password combinations until they find the correct one.
* **The Mitigation:** Implements an in-memory threshold controller. If a specific user identification triggers 3 consecutive authentication failures, the application triggers a temporary **Account Lockout**, rejecting further attempts and protecting the user's credentials from extraction.

---

## 📂 Core Architecture
* `SistemaAutenticacaoSegura.java`: Monolithic execution block containing core logic for secure input handling, routing, credential mapping, and threat response logs.

---

## 🚀 Execution & Testing

1. Compile and run the Java file using your preferred IDE (VS Code, IntelliJ) or the terminal:
   ```bash
   javac SistemaAutenticacaoSegura.java
   java SistemaAutenticacaoSegura
2.​Test Brute Force Defenses: Try entering a valid user (e.g., admin@empresa.com.br) with a wrong password 3 times in a row. Watch the system trigger the lockout state.
​Test SQLi Defenses: Notice how inputs containing database syntax tokens do not break or bypass the logical routing layer.