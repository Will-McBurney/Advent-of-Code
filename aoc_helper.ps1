$year = $Args[0]
$day = $Args[1]
mkdir src\main\kotlin\year$year\day$day\
mkdir src\main\resources\year$year\day$day\
New-Item src\main\resources\year$year\day$day\input.txt
New-Item src\main\resources\year$year\day$day\test_input.txt
New-Item src\main\kotlin\year$year\day$day\Main.kt
Add-Content -Path src\main\kotlin\year$year\day$day\Main.kt -Value "package year$year.day$day`n`n" -NoNewLine
Add-Content -Path src\main\kotlin\year$year\day$day\Main.kt -Value "import AoCResultPrinter`n" -NoNewline
Add-Content -Path src\main\kotlin\year$year\day$day\Main.kt -Value "import Reader`n" -NoNewline
$template = Get-Content -Path src\main\kotlin\MainTemplate.kt
Add-Content -Path src\main\kotlin\year$year\day$day\Main.kt -Value $template
git add src\main\kotlin\year$year\day$day\Main.kt