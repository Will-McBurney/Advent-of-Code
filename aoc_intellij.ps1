$year = $Args[0]
$day = $Args[1]
Get-ChildItem src\main\kotlin\year$year\day$day -Filter *.kt |
        ForEach-Object {
            Write-Output "Opening $_ ..."
            idea64 $_.FullName
            Start-Sleep -Milliseconds 250
        };

Get-ChildItem src\main\resources\year$year\day$day -Filter *.txt |
        ForEach-Object {
            Write-Output "Opening $_ ..."
            idea64 $_.FullName
            Start-Sleep -Milliseconds 250
        };

