# Library Management - Run Script for PowerShell

# Get all jar files from target/libs
$jars = @()
if (Test-Path "target/libs") {
    $jars = Get-ChildItem "target/libs/*.jar" | ForEach-Object { $_.FullName }
}

# Build classpath
$classpath = "target/classes"
foreach ($jar in $jars) {
    $classpath += ";$jar"
}

# Run the application
java -cp $classpath com.library.App
