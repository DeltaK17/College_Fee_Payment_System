# College Fee Payment System

![Java](https://img.shields.io/badge/Java-17-blue)
![Tomcat](https://img.shields.io/badge/Tomcat-9.0.105-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-green)

A web-based application to manage student fee payments, built with Java Servlets, JSP, MySQL, and Tailwind CSS. The system supports adding, updating, deleting, and viewing fee payments, along with generating reports for overdue payments, non-paid students, and total collections.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Overview
The **College Fee Payment System** streamlines the management of student fee payments for college administrators. It provides a user-friendly interface to record fee payments, update records, delete entries, and view payment histories. The system also generates reports to track overdue payments, identify non-paying students within a date range, and calculate total fee collections. Built using the MVC architecture, it ensures scalability and maintainability.

## Features
### Modules
- **FeeAdd**: Record new fee payments with Student ID, Name, Date, Amount, and Status (Paid, Unpaid, Overdue).
- **FeeUpdate**: Modify existing payment details.
- **FeeDelete**: Remove payment records by Payment ID.
- **FeeDisplay**: View all payment records in a tabular format.
- **Reports**:
  - List students with overdue payments.
  - Identify students who haven’t paid in a specified period.
  - Calculate total fee collections over a date range.

## Technologies
- **Backend**: Java Servlets, JDBC
- **Frontend**: JSP, Tailwind CSS
- **Database**: MySQL (via MySQL Connector/J 9.3.0)
- **Server**: Apache Tomcat 9.0.105
- **IDE**: Eclipse Enterprise
- **Build Tool**: Manual build in Eclipse
- **Dependencies**:
  - MySQL Connector/J 9.3.0
  - JSTL 1.2 (`jstl.jar`, `standard.jar`)

## Database Schema
The system uses a MySQL database named `college_fee_system` with one table:

```sql
CREATE TABLE FeePayments (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    StudentID INT,
    StudentName VARCHAR(100),
    PaymentDate DATE,
    Amount DECIMAL(10,2),
    Status VARCHAR(20) -- Paid, Unpaid, Overdue
);
```

## Project Structure
```
CollegeFeeWebApp/
├── WebContent/
│   ├── index.jsp              # Home page with navigation
│   ├── feepaymentadd.jsp      # Add fee payment form
│   ├── feepaymentupdate.jsp   # Update payment form
│   ├── feepaymentdelete.jsp   # Delete payment form
│   ├── feepaymentdisplay.jsp  # Display all payments
│   ├── reports.jsp            # Reports landing page
│   ├── report_form.jsp        # Report selection form
│   └── report_result.jsp      # Report results
├── src/
│   ├── com/
│   │   ├── dao/
│   │   │   └── FeePaymentDAO.java        # Database operations
│   │   ├── model/
│   │   │   └── FeePayment.java           # Payment model
│   │   └── servlet/
│   │       ├── AddFeePaymentServlet.java      # Add payment
│   │       ├── UpdateFeePaymentServlet.java   # Update payment
│   │       ├── DeleteFeePaymentServlet.java   # Delete payment
│   │       ├── DisplayFeePaymentsServlet.java # Display payments
│   │       ├── ReportServlet.java            # Generate reports
│   │       └── ReportCriteriaServlet.java    # Report form
└── WEB-INF/
    ├── lib/
    │   ├── mysql-connector-j-9.3.0.jar
    │   ├── jstl.jar
    │   ├── standard.jar
    └── web.xml                        # Servlet mappings
```

## Installation
### Prerequisites
- Java JDK 8 or higher
- Apache Tomcat 9.0.105
- MySQL (via XAMPP or standalone)
- Eclipse IDE for Enterprise Java Developers
- Git (optional, for cloning)

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/CollegeFeeWebApp.git
   cd CollegeFeeWebApp
   ```

2. **Set Up the Database**:
   - Start MySQL (e.g., via XAMPP).
   - Create the database:
     ```sql
     CREATE DATABASE college_fee_system;
     ```
   - Create the `FeePayments` table:
     ```sql
     CREATE TABLE FeePayments (
         PaymentID INT PRIMARY KEY AUTO_INCREMENT,
         StudentID INT,
         StudentName VARCHAR(100),
         PaymentDate DATE,
         Amount DECIMAL(10,2),
         Status VARCHAR(20)
     );
     ```
   - Add sample data:
     ```sql
     INSERT INTO FeePayments (StudentID, StudentName, PaymentDate, Amount, Status) VALUES
         (1, 'John Doe', '2025-01-15', 500.00, 'Paid'),
         (2, 'Jane Smith', '2025-02-10', 750.00, 'Overdue'),
         (3, 'Alice Johnson', '2025-03-20', 600.00, 'Paid'),
         (4, 'Bob Wilson', '2025-04-05', 800.00, 'Unpaid'),
         (5, 'Emma Brown', '2025-05-01', 450.00, 'Paid');
     ```

3. **Configure Eclipse**:
   - Import the project: **File > Import > Existing Projects into Workspace**.
   - Add Tomcat: **Window > Preferences > Server > Runtime Environments**.
   - Add dependencies to `WEB-INF/lib` and build path:
     - `mysql-connector-j-9.3.0.jar`
     - `jstl.jar`
     - `standard.jar`

4. **Deploy to Tomcat**:
   - In **Servers** view, add `CollegeFeeWebApp` to Tomcat.
   - Ensure port 8080 is free (or configure 8081 in `server.xml`).
   - Start Tomcat: **Servers** > **Start**.

5. **Access the Application**:
   - Open: `http://localhost:8080/CollegeFeeWebApp/`

## Usage
- **Home Page**: `index.jsp` provides links to all modules.
- **Add Payment**: Navigate to `feepaymentadd.jsp`, enter details (e.g., StudentID=6, StudentName="Michael Lee", PaymentDate="2025-05-28", Amount=500.00, Status="Unpaid"), and submit.
- **Update Payment**: Use `feepaymentupdate.jsp` to edit records by PaymentID.
- **Delete Payment**: Enter PaymentID in `feepaymentdelete.jsp` to remove records.
- **View Payments**: Access `/displayFeePayments` to see all records in `feepaymentdisplay.jsp`.
- **Reports**:
  - Go to `/reportCriteria` (`report_form.jsp`).
  - Select report type (Overdue, Non-Paid, Total Collection) and date range.
  - View results in `report_result.jsp`.

## Troubleshooting
- **Tomcat 404 Error (e.g., `/CollegeFeeWebApplication/addFeePayment`)**:
  - Verify context path: Ensure project name is `CollegeFeeWebApp`. If `CollegeFeeWebApplication`, update JSPs to use `${pageContext.request.contextPath}`.
  - Check `web.xml` for correct servlet mappings.
  - Confirm `AddFeePaymentServlet.class` in `WEB-INF/classes/com/servlet/`.
  - Redeploy: **Servers** > **Add and Remove** > **Clean**.
- **Tomcat Startup Failure**:
  - Check port conflicts (8080): Change to 8081` in `<Tomcat_Home>/conf/server.xml`.
  - Validate `web.xml` syntax.
  - View logs: `<Tomcat_Home>/logs/catalina.out` or Eclipse **Console**.
- **Database Connection Issues**:
  - Ensure MySQL is running and `college_fee_system` exists.
  - Verify `mysql-connector-j-9.3.0.jar` in `WEB-INF/lib`.
  - Test connection:
    ```java
    import java.sql.*;

    public class TestDBConnection {
        public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:3306/college_fee_system?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "";
            try {
                Class.forName("com.mysql.cj.Driver");
                Connection conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connection successful!");
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    ```

## Contributing
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature`.
3. Commit changes: `git commit -m "Add your feature"`.
4. Push to branch: `git push origin feature/your-feature`.
5. Open a Pull Request.

## License
[MIT License](LICENSE.txt) - Free for educational use.
