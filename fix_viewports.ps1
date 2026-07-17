Get-ChildItem -Path core\src\main\java\frontend -Filter *.java -Exclude TelaBatalha.java | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    $newContent = $content -replace 'import com\.badlogic\.gdx\.utils\.viewport\.ScreenViewport;', 'import com.badlogic.gdx.utils.viewport.FitViewport;'
    $newContent = $newContent -replace 'new ScreenViewport\(\)', 'new FitViewport(1920, 1080)'
    Set-Content -Path $_.FullName -Value $newContent -Encoding UTF8
}
