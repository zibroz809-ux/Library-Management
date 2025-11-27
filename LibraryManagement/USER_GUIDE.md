# 🎬 How to Navigate Your Newly Redesigned App

## Starting the Application

```powershell
mvn -q exec:java "-Dexec.mainClass=com.library.App"
```

**You'll see:**
- Beautiful Dashboard with sidebar navigation
- Real-time statistics
- Quick action buttons
- Professional styling

---

## 📍 Navigation Guide

### **Main Screen: Dashboard**
```
┌─────────────────────────────────────────────────────┐
│  📚 LIBRARY                                         │
│                                                     │
│  🏠 Dashboard (YOU ARE HERE)                       │
│  📕 Books           ────────────────────────────────┤
│  👥 Borrowers       │ 📊 Welcome to Library Mgmt  │
│  🏢 Branches        │                             │
│  💳 Loans           │ Statistics Cards:           │
│  ⚙️ Settings        │ ┌─────────────────────────┐ │
│  ℹ️ About           │ │ Books: 10               │ │
│  🚪 Logout          │ └─────────────────────────┘ │
│                     │ ┌─────────────────────────┐ │
│                     │ │ Borrowers: 5           │ │
│                     │ └─────────────────────────┘ │
│                     │ ┌─────────────────────────┐ │
│                     │ │ Active Loans: 7        │ │
│                     │ └─────────────────────────┘ │
│                     │ ┌─────────────────────────┐ │
│                     │ │ Overdue: 2             │ │
│                     │ └─────────────────────────┘ │
│                     │                             │
│                     │ Quick Actions:              │
│                     │ [➕ New Loan]              │
│                     │ [📕 Add Book]              │
│                     │ [👥 Add Borrower]          │
│                     │ [✓ Return Book]            │
└─────────────────────────────────────────────────────┘
```

---

## 🎯 What Each Menu Item Does

### **1️⃣ Dashboard** (Current Page)
- **Shows:** Overall statistics and overview
- **Has:** Quick action buttons
- **Purpose:** Get quick overview of library status

### **2️⃣ Books**
- **Shows:** All books in library
- **Features:** 
  - Search by title, author, publisher
  - Add new book
  - Edit book details
  - Delete book (with confirmation)

### **3️⃣ Borrowers**
- **Shows:** All borrower members
- **Features:**
  - View all borrowers
  - Add new borrower
  - Edit borrower info
  - Delete borrower

### **4️⃣ Branches**
- **Shows:** All library branches
- **Features:**
  - View all branches
  - Add new branch
  - Edit branch info
  - Delete branch

### **5️⃣ Loans**
- **Shows:** All active and returned loans
- **Features:**
  - Create new loan (with date pickers)
  - Track book returns
  - Automatic overdue detection
  - Search loans
  - Real-time statistics

### **6️⃣ Settings**
- **Purpose:** Configure application settings
- **Status:** Ready for future enhancements

### **7️⃣ About**
- **Purpose:** View application information
- **Status:** Ready for future enhancements

### **8️⃣ Logout**
- **Purpose:** Exit the application
- **Action:** Closes the application

---

## 📕 Detailed: Books Module

```
Books Management Screen
─────────────────────────────────────────────────────
┌ Header ─────────────────────────────────────────┐
│ 📕 Manage Books                                │
│ Total Books: 25                                │
└─────────────────────────────────────────────────┘

┌ Search Bar ─────────────────────────────────────┐
│ 🔍 Search: [________________]  [Clear]          │
│                    Showing 25 of 25 books       │
└─────────────────────────────────────────────────┘

┌ Table ──────────────────────────────────────────┐
│ ID │ Book Title      │ Publisher    │ Authors   │
├────┼─────────────────┼──────────────┼───────────┤
│ 1  │ Clean Code      │ Prentice Hall│ Uncle Bob │
│ 2  │ Design Patterns │ Addison-Wesley│ Gang of 4│
│ ... more books ...                              │
└─────────────────────────────────────────────────┘

┌ Action Buttons ─────────────────────────────────┐
│ [➕ Add Book] [✏️ Edit] [🗑️ Delete]            │
│                      ✓ Book updated             │
└─────────────────────────────────────────────────┘
```

### **How to Search for a Book:**
1. Click on the search field
2. Type book title, author, or publisher
3. Results filter in real-time
4. Click "Clear" to reset

### **How to Add a Book:**
1. Click [➕ Add Book] button
2. Fill in book details
3. Click [✓ Create]
4. Book appears in table

### **How to Edit a Book:**
1. Select book in table
2. Click [✏️ Edit] button
3. Modify details
4. Click [✓ Update]

### **How to Delete a Book:**
1. Select book in table
2. Click [🗑️ Delete] button
3. Confirm in popup dialog
4. Book is removed

---

## 💳 Detailed: Loans Module

```
Loans Management Screen
─────────────────────────────────────────────────────
┌ Header ─────────────────────────────────────────┐
│ 💳 Manage Loans                                │
│ Active Loans: 12 | Overdue: 2                  │
└─────────────────────────────────────────────────┘

┌ Search Bar ─────────────────────────────────────┐
│ 🔍 Search: [________________]  [Clear]          │
│                    Showing 12 of 14 loans       │
└─────────────────────────────────────────────────┘

┌ Table ──────────────────────────────────────────┐
│ ID │ Book │ Borrower│ Branch│ Out   │ Due  │Ret│Stat│
├────┼──────┼─────────┼────────┼───────┼──────┼───┼────┤
│ 1  │Clean │John    │Main   │11-01  │11-15 │ - │📚  │
│ 2  │Design│Mary    │East   │10-20  │11-03 │ - │⚠️  │
│ ... more loans ...                              │
└─────────────────────────────────────────────────┘

┌ Action Buttons ─────────────────────────────────┐
│ [➕ Add Loan] [✓ Return] [🗑️ Delete]          │
│                      ✓ Loan returned            │
└─────────────────────────────────────────────────┘
```

### **Loan Status Colors:**
- 🟢 **✓ Returned** (Green) — Book has been returned
- 🟠 **📚 Active** (Orange) — Loan is current
- 🔴 **⚠️ OVERDUE** (Red) — Past due date

### **How to Create a Loan:**
1. Click [➕ Add Loan]
2. Select Book (e.g., "Clean Code")
3. Select Borrower (e.g., "John")
4. Select Branch (e.g., "Main")
5. Pick "Date Out" (default: today)
6. Pick "Due Date" (default: +14 days)
7. Click [✓ Create Loan]

### **How to Return a Book:**
1. Select loan with status "📚 Active"
2. Click [✓ Return Book]
3. Enter return date in dialog
4. Click OK
5. Status changes to "✓ Returned" (green)

### **How to Track Overdue:**
1. Check Status column
2. Red "⚠️ OVERDUE" = past due date
3. Dashboard shows total overdue count
4. Sort by due date to see soonest first

---

## 🎨 Color Guide

### **Button Colors Mean:**
```
🟢 GREEN  [➕ Add]      = Create new items
🔵 BLUE   [✏️ Edit]     = Modify existing
🟠 ORANGE [✓ Return]   = Return/confirm
🔴 RED    [🗑️ Delete]  = Remove items
⚫ GRAY   [Clear]      = Reset/cancel
```

### **Status Colors Mean:**
```
🟢 GREEN  = Complete/Success (Returned)
🔵 BLUE   = Active/In Progress
🟠 ORANGE = Warning/Attention (Overdue)
🔴 RED    = Error/Critical
```

### **Dashboard Card Colors Mean:**
```
🔵 Blue   = Primary info (total books, borrowers)
🟠 Orange = Active loans (currently borrowed)
🔴 Red    = Overdue loans (needs attention)
```

---

## ⌨️ Keyboard Shortcuts

```
No special shortcuts yet, but planned for future:
Ctrl+N  = New item
Ctrl+S  = Save
Ctrl+D  = Delete
Ctrl+F  = Search/Find
Ctrl+Q  = Quit
```

---

## 💡 Pro Tips

### **Working with Books:**
- Use search to quickly find books
- Search works on title, author, AND publisher
- Add books before creating loans

### **Working with Borrowers:**
- Create borrower profiles first
- Can track which borrower has which book
- Use borrower info to manage returns

### **Working with Loans:**
- Always set a due date (helps track overdue)
- Return books promptly to free up copies
- Overdue loans highlighted in red
- Dashboard shows overdue count at top

### **General Tips:**
- Confirmation dialogs prevent accidents
- Green checkmarks show success
- Sort tables by clicking column headers
- Search is case-insensitive

---

## ❓ Common Tasks

### **Task: Add a New Book**
1. Click sidebar "📕 Books"
2. Click [➕ Add Book]
3. Fill in Title, Author(s), Publisher
4. Click [✓ Add]

### **Task: Create a Loan**
1. Click sidebar "💳 Loans"
2. Click [➕ Add Loan]
3. Select Book, Borrower, Branch
4. Select Due Date (+14 days default)
5. Click [✓ Create Loan]

### **Task: Track an Overdue Loan**
1. Look at Dashboard → "Overdue: X"
2. Click sidebar "💳 Loans"
3. Look for red "⚠️ OVERDUE" in Status column
4. Follow up with borrower
5. Process return when book arrives

### **Task: Return a Book**
1. Click sidebar "💳 Loans"
2. Find the loan (look for 🟠 Active status)
3. Click [✓ Return Book]
4. Enter actual return date
5. Click OK
6. Status changes to 🟢 Returned

### **Task: Find a Book Quickly**
1. Click sidebar "📕 Books"
2. Click search field
3. Type book title
4. Results show instantly

---

## 📊 Dashboard Explained

### **4 Key Statistics:**
1. **Total Books** — How many books in library
2. **Total Borrowers** — How many members
3. **Active Loans** — Books currently borrowed (🟠)
4. **Overdue Loans** — Books not returned on time (🔴)

### **Quick Actions:**
- [➕ New Loan] — Create loan quickly
- [📕 Add Book] — Add new book
- [👥 Add Borrower] — Add new member
- [✓ Return Book] — Process return quickly

### **System Info:**
- Java Version
- Data Location
- Getting Started Guide

---

## 🔄 Typical Workflow

```
1. Start Dashboard
   ↓
2. Add Borrowers (if new)
   ↓
3. Add Books (if new)
   ↓
4. Create Loans (when borrowing)
   ↓
5. Track Loans (monitor due dates)
   ↓
6. Process Returns (when books come back)
   ↓
7. View Statistics (track library health)
```

---

## ✅ You're Ready!

You now understand:
- How to navigate the app ✅
- What each module does ✅
- What the colors mean ✅
- How to perform common tasks ✅
- How to track loans ✅

**Start using your professional library management system!** 🎉

---

**Questions? Check QUICKSTART.md for detailed guide**
**Need help? Check documentation files**
**Want to extend? Foundation is ready!** 🚀
