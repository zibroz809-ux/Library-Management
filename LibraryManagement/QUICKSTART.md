# 🎉 LibraryManagement - Quick Start Guide

## Installation & Running

### Prerequisites
- Java 21 or higher (you have Java 23 ✓)
- Maven 3.6+ (already installed ✓)

### 1. **Build the Application**
```powershell
cd d:\College\Semester-3\PPBO\Praktikum\Project\LibraryManagement
mvn clean package
```

### 2. **Run the Application** ⭐ (Choose ONE)

#### Option A: Maven (Recommended)
```powershell
mvn -q exec:java "-Dexec.mainClass=com.library.App"
```

#### Option B: Direct JAR with dependencies
```powershell
java -cp "target/classes;target/dependency/*" com.library.App
```

---

## ✨ Key Features

### 📚 **Loan Management**
- **Create loans** with date out, due date, borrower, book, and branch
- **Track returns** with actual return date recording
- **Overdue detection** — automatically flags loans past due date
- **Delete loans** with confirmation dialog

### 🔍 **Search & Filter**
- Real-time search by:
  - Loan ID
  - Book title
  - Borrower name
  - Branch name

### 📊 **Dashboard**
- **Live statistics:**
  - Total active loans count
  - Overdue loans count
  - Last action feedback

### 🎨 **Visual Status Indicators**
- 🟢 **✓ Returned** — Green (loan completed)
- 🟠 **📚 Active** — Orange (in progress)
- 🔴 **⚠ OVERDUE** — Red (past due date)

### ✅ **Smart Validation**
- Enforces loan period: 1-90 days
- Prevents invalid dates
- Requires all fields before creating loan
- Validates return dates (cannot be future or before loan date)

---

## 🚀 Getting Started (Screenshots)

### 1. **Launch the App**
You'll see the **Loan Management** screen with:
- Search bar at the top
- Empty table (no loans yet)
- Blue header with statistics

### 2. **Add Your First Loan**
1. Click **"➕ Add Loan"** button
2. **Select Book** — choose from your library
3. **Select Branch** — pick a branch location
4. **Select Borrower** — choose who's borrowing
5. **Date Out** — defaults to today ✓
6. **Due Date** — defaults to +14 days ✓
7. Click **"✓ Create Loan"** → Success! ✓

### 3. **View Your Loans**
- Loan appears in the table immediately
- Status shows "📚 Active" (blue)
- Search bar is ready to use

### 4. **Return a Book**
1. Select a loan in the table
2. Click **"✓ Return Book"**
3. Enter the actual return date
4. Click OK
5. Loan status changes to "✓ Returned" (green) ✓

### 5. **Search Loans**
1. Type in the search bar (top of table)
2. Results filter in real-time
3. Shows "Showing X of Y loans"

---

## 📋 Field Reference

### Loan Fields
| Field | Required | Format | Notes |
|-------|----------|--------|-------|
| Book | ✓ Yes | Dropdown | Shows book titles |
| Branch | ✓ Yes | Dropdown | Shows branch names |
| Borrower | ✓ Yes | Dropdown | Shows borrower names |
| Date Out | ✓ Yes | YYYY-MM-DD | Defaults to today |
| Due Date | ✓ Yes | YYYY-MM-DD | Must be after date out |
| Date Returned | Optional | YYYY-MM-DD | Set only when returning |

---

## 🐛 Troubleshooting

### **"Module javafx.controls not found"**
→ Run using: `java -cp "target/classes;target/dependency/*" com.library.App`

### **"JavaFX runtime components are missing"**
→ Ensure dependencies are copied: `mvn dependency:copy-dependencies`

### **"Cannot select loan to return"**
→ Make sure loan status is "📚 Active" (not already returned)

### **App doesn't start**
→ Check Java version: `java -version` (should show Java 21+)
→ Try rebuilding: `mvn clean package`

---

## 📁 Project Structure

```
LibraryManagement/
├── src/main/java/com/library/
│   ├── App.java                      # Main entry point
│   ├── controllers/
│   │   ├── LoanController.java       # ✨ NEW: Search, status, overdue tracking
│   │   ├── AddLoanController.java    # ✨ UPDATED: Date validation
│   │   └── ... (other controllers)
│   ├── models/
│   │   ├── Loan.java                 # ✨ UPDATED: LocalDate support
│   │   └── ... (other models)
│   ├── services/
│   │   ├── LoanService.java          # ✨ UPDATED: updateLoan() method
│   │   └── ... (other services)
│   └── database/
│       └── JsonDatabase.java         # ✨ UPDATED: LocalDate serialization
├── src/main/resources/views/
│   ├── loan.fxml                     # ✨ REDESIGNED: Search, stats, colors
│   ├── add_loan.fxml                 # ✨ REDESIGNED: Date pickers, validation
│   └── ... (other views)
├── data/
│   └── loans.json                    # Loan data (auto-created)
├── target/
│   ├── classes/                      # Compiled classes
│   └── dependency/                   # JAR dependencies
├── IMPROVEMENTS.md                   # ✨ NEW: Detailed changelog
└── pom.xml                           # Maven config
```

---

## 🎯 What's New (v2.0)

| Component | Change | Benefit |
|-----------|--------|---------|
| **Loan Model** | String → LocalDate | Proper date handling, overdue detection |
| **LoanController** | Basic → Advanced | Search, filter, status tracking, statistics |
| **add_loan.fxml** | Plain form → Professional UI | Date pickers, validation, better UX |
| **loan.fxml** | Minimal table → Full dashboard | Search bar, stats, color-coded status |
| **Validation** | None → Strict | Prevents invalid data entry |
| **Error Messages** | Generic → Specific | Users know exactly what's wrong |

---

## 📞 Support

If you encounter issues:
1. Check the **Troubleshooting** section above
2. Read `IMPROVEMENTS.md` for detailed feature docs
3. Share terminal output if building/running fails
4. Check `target/` folder exists after `mvn package`

---

## ✅ Checklist

Before using in production:
- [ ] Built successfully: `mvn package` runs without errors
- [ ] App starts: Run `mvn -q exec:java "-Dexec.mainClass=com.library.App"`
- [ ] Can see Loan Management screen
- [ ] Can create a test loan
- [ ] Search works
- [ ] Can return a loan
- [ ] Overdue detection works (create loan with past due date, verify status is red)

---

**Enjoy your improved Library Management System! 🎉**

For more details, see `IMPROVEMENTS.md` in the project root.
