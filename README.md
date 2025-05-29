
# College Fee Payment System

**Subject Name**: Advanced Java  
**Subject Code**: BCS613D  
**Name**: Kiran Kumar  
**USN**: 4AL22CS078  
**Sem/Section**: VI/B  

---

A web-based application for managing college fee payments using JSP, Servlets, and MySQL, following MVC architecture principles.

## 🌟 Features

- 💳 **Complete Fee Management**: Add, update, delete, and view student fee payment records
- 🔍 **Advanced Search**: Search payments by student ID or status
- 📊 **Comprehensive Reports**: Generate reports including:
  - Students with overdue payments
  - Students who haven't paid within a period
  - Total collections over a date range
- 🛡️ **Validation**: Both client-side and server-side validation
- 💻 **Responsive Design**: Bootstrap-powered user interface
- 🏗️ **MVC Architecture**: Clear separation of model, view, and controller
- 🛢️ **Database Connectivity**: MySQL with JDBC

## 📋 Prerequisites

- Java JDK 8 or higher
- Apache Tomcat 9 or higher
- MySQL Server 5.7 or XAMPP
- MySQL JDBC Driver
- Java IDE (Eclipse, IntelliJ IDEA)
- Modern Web Browser

## 📁 Project Structure

```
CollegeFeeWebApp/
├── WebContent/
│   ├── index.jsp
│   ├── feepaymentadd.jsp
│   ├── feepaymentupdate.jsp
│   ├── feepaymentdelete.jsp
│   ├── feepaymentdisplay.jsp
│   ├── reports.jsp
│   ├── report_form.jsp
│   └── report_result.jsp
├── src/
│   └── com/
│       ├── dao/
│       │   └── FeePaymentDAO.java
│       ├── model/
│       │   └── FeePayment.java
│       └── servlet/
│           ├── AddFeePaymentServlet.java
│           ├── UpdateFeePaymentServlet.java
│           ├── DeleteFeePaymentServlet.java
│           ├── DisplayFeePaymentsServlet.java
│           ├── ReportServlet.java
│           └── ReportCriteriaServlet.java
└── WEB-INF/
    └── web.xml
```

## 🗄️ Database Setup

```sql
CREATE DATABASE IF NOT EXISTS college_fee_db;
USE college_fee_db;

CREATE TABLE FeePayments (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    StudentID INT,
    StudentName VARCHAR(100),
    PaymentDate DATE,
    Amount DECIMAL(10,2),
    Status VARCHAR(20)
);
```

## ⚙️ Installation & Setup

1. **Clone Project**: Download or clone the repository.
2. **Configure Database**: 
   - Run the SQL script above.
   - Update DB credentials in `FeePaymentDAO.java`:
     ```java
     connection = DriverManager.getConnection(
         "jdbc:mysql://localhost:3306/college_fee_db", 
         "your_username", 
         "your_password");
     ```
3. **Add MySQL JDBC Driver**:
   - Download and include it in `WEB-INF/lib`.
4. **Deploy on Tomcat**:
   - Use IDE to deploy or place in `webapps` folder.
5. **Access Application**:
   - Visit `http://localhost:8080/CollegeFeeWebApp/`

## 🧾 Usage

### ➕ Add Fee Payment
- Fill in Student ID, Name, Date, Amount, and Status (e.g., Paid/Overdue).
- Click "Add Payment".

### ✏️ Update Payment
- Enter Student ID and load existing data.
- Modify and save changes.

### ❌ Delete Payment
- Search by ID, confirm, and delete.

### 📋 Display Records
- View all records or filter by ID.

### 📈 Generate Reports
- **Overdue**: List overdue students.
- **Unpaid**: Within a time range.
- **Collections**: Total fee collected over date range.

## 🧪 Test Cases

1. ✅ Add Payment: Valid inputs, duplicate IDs
2. 🔄 Update Payment: Existing vs. non-existing
3. 🗑️ Delete Payment: Confirm delete, handle missing records
4. 🔍 Search/Display: Filter, empty result handling
5. 📊 Reports: Validate filters, date ranges

## 🚀 Outcomes

- Strong understanding of JSP/Servlet-based web applications
- Practical experience with MVC design pattern
- Effective use of JDBC for database interaction
- Dynamic reporting and validation handling
- Responsive UI with real-world functionality
