$year = $Args[0]
$day = $Args[1]
mkdir src\main\kotlin\year$year\day$day\
mkdir src\main\resources\year$year\day$day\
New-Item src\main\kotlin\year$year\day$day\Main.kt
New-Item src\main\resources\year$year\day$day\input.txt
New-Item src\main\resources\year$year\day$day\test_input.txt