# Run the Library Management Application

To run the application, use one of these commands:

## Option 1: Using Maven (Recommended - Easiest)
```
mvn clean package
java -jar target/LibraryManagement.jar
```

## Option 2: Using PowerShell (Windows)
```powershell
cd D:\College\Semester-3\PPBO\Praktikum\Project\LibraryManagement
$libs = Get-ChildItem "target\libs\*.jar" -ErrorAction SilentlyContinue
$cp = "target\classes"
foreach ($lib in $libs) { $cp += ";$($lib.FullName)" }
java -cp $cp com.library.App
```

## Option 3: Using CMD (Windows)
```cmd
cd /d D:\College\Semester-3\PPBO\Praktikum\Project\LibraryManagement
mvn dependency:copy-dependencies -DoutputDirectory=target/libs -q
java -cp "target\classes;target\libs\*" com.library.App
```

## Option 4: Run JAR directly
```
java -jar target/LibraryManagement.jar
```

The application window will open with the Library Management UI.
