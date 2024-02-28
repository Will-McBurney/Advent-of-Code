$year = $Args[0]
$day = $Args[1]
Get-ChildItem src\main\kotlin\year$year\day$day -Filter *.kt |
        ForEach-Object {
            Write-Output "Opening $_ ..."
            idea64 $_.FullName
            Start-Sleep -Milliseconds 250
        }
Start-Sleep -Milliseconds 250
Write-Output "Opening Input file ..."
idea64 src\main\resources\year$year\day$day\input.txt
Start-Sleep -Milliseconds 250
Write-Output "Opening Test Input file ..."
idea64 src\main\resources\year$year\day$day\test_input.txt

