$ErrorActionPreference = 'Continue'
$base = 'http://localhost:8080/GestionSeries'
$results = New-Object System.Collections.Generic.List[object]

function Add-Result($name, $ok, $detail) {
    $results.Add([pscustomobject]@{ Test = $name; OK = [bool]$ok; Detail = "$detail" }) | Out-Null
    $tag = if ($ok) { 'OK' } else { 'FAIL' }
    Write-Host "[$tag] $name :: $detail"
}

# 1 GET all
try {
    $r = Invoke-WebRequest -Uri "$base/api/serie/all" -UseBasicParsing -TimeoutSec 30
    Add-Result 'GET /api/serie/all' ($r.StatusCode -eq 200) "status=$($r.StatusCode)"
} catch {
    Add-Result 'GET /api/serie/all' $false $_.Exception.Message
}

# 2 login admin
$tokenAdmin = $null
try {
    $r = Invoke-WebRequest -Uri "$base/api/auth/login" -Method POST -Body 'user=admin&pwd=admin' -ContentType 'application/x-www-form-urlencoded' -UseBasicParsing -TimeoutSec 30
    $tokenAdmin = ($r.Content).Trim().Trim('"')
    Add-Result 'POST login admin' (($r.StatusCode -eq 200) -and ($tokenAdmin.Length -gt 20)) "tokenLen=$($tokenAdmin.Length)"
} catch {
    $code = $null
    try { $code = [int]$_.Exception.Response.StatusCode } catch {}
    Add-Result 'POST login admin' $false "status=$code $($_.Exception.Message)"
}

# 3 add serie admin
$serieName = 'TestAPI_' + (Get-Date -Format 'HHmmss')
try {
    if (-not $tokenAdmin) { throw 'missing token' }
    $body = "nom=$serieName&token=$([uri]::EscapeDataString($tokenAdmin))"
    $r = Invoke-WebRequest -Uri "$base/api/serie/add" -Method POST -Body $body -ContentType 'application/x-www-form-urlencoded' -UseBasicParsing -TimeoutSec 30
    Add-Result 'POST serie/add admin' (($r.StatusCode -eq 201) -or ($r.StatusCode -eq 200)) "status=$($r.StatusCode)"
} catch {
    $code = $null
    try { $code = [int]$_.Exception.Response.StatusCode } catch {}
    $errBody = ''
    try {
        $stream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($stream)
        $errBody = $reader.ReadToEnd()
    } catch {}
    Add-Result 'POST serie/add admin' $false "status=$code body=$errBody msg=$($_.Exception.Message)"
}

# 4 visible in list
try {
    $r = Invoke-WebRequest -Uri "$base/api/serie/all" -UseBasicParsing -TimeoutSec 30
    Add-Result 'Serie appears in GET all' ($r.Content.Contains($serieName)) "name=$serieName"
} catch {
    Add-Result 'Serie appears in GET all' $false $_.Exception.Message
}

# 5 login user
$tokenUser = $null
try {
    $r = Invoke-WebRequest -Uri "$base/api/auth/login" -Method POST -Body 'user=user&pwd=user' -ContentType 'application/x-www-form-urlencoded' -UseBasicParsing -TimeoutSec 30
    $tokenUser = ($r.Content).Trim().Trim('"')
    Add-Result 'POST login user' (($r.StatusCode -eq 200) -and ($tokenUser.Length -gt 20)) "tokenLen=$($tokenUser.Length)"
} catch {
    $code = $null
    try { $code = [int]$_.Exception.Response.StatusCode } catch {}
    Add-Result 'POST login user' $false "status=$code"
}

# 6 user forbidden
try {
    if (-not $tokenUser) { throw 'missing user token' }
    $body = "nom=HackUser&token=$([uri]::EscapeDataString($tokenUser))"
    $r = Invoke-WebRequest -Uri "$base/api/serie/add" -Method POST -Body $body -ContentType 'application/x-www-form-urlencoded' -UseBasicParsing -TimeoutSec 30
    Add-Result 'POST add as user expect 403' $false "got $($r.StatusCode)"
} catch {
    $code = $null
    try { $code = [int]$_.Exception.Response.StatusCode } catch {}
    Add-Result 'POST add as user expect 403' ($code -eq 403) "status=$code"
}

# 7 UI
try {
    $r = Invoke-WebRequest -Uri "$base/liste" -UseBasicParsing -TimeoutSec 30
    Add-Result 'GET /liste UI' ($r.StatusCode -eq 200) "status=$($r.StatusCode) len=$($r.RawContentLength)"
} catch {
    Add-Result 'GET /liste UI' $false $_.Exception.Message
}

# 8 bad password
try {
    $r = Invoke-WebRequest -Uri "$base/api/auth/login" -Method POST -Body 'user=admin&pwd=wrong' -ContentType 'application/x-www-form-urlencoded' -UseBasicParsing -TimeoutSec 30
    Add-Result 'login bad password expect 400' $false "got $($r.StatusCode)"
} catch {
    $code = $null
    try { $code = [int]$_.Exception.Response.StatusCode } catch {}
    Add-Result 'login bad password expect 400' ($code -eq 400) "status=$code"
}

$pass = @($results | Where-Object OK).Count
$total = $results.Count
Write-Host "SUMMARY $pass/$total"
$results | Format-Table -AutoSize
$results | ConvertTo-Json | Set-Content -Encoding utf8 "c:\Users\edoua\Downloads\ExempleGestionSeries\GestionSeries\docs\test_results.json"
