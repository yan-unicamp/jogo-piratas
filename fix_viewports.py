import os
import glob

directory = r"c:\Users\berna\Desktop\MC322\Jogo Piratas\jogo-piratas\core\src\main\java\frontend"

for filepath in glob.glob(os.path.join(directory, "*.java")):
    if "TelaBatalha.java" in filepath:
        continue
    
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
    
    if "ScreenViewport" in content:
        content = content.replace("import com.badlogic.gdx.utils.viewport.ScreenViewport;", "import com.badlogic.gdx.utils.viewport.FitViewport;")
        content = content.replace("new ScreenViewport()", "new FitViewport(1920, 1080)")
        
        with open(filepath, "w", encoding="utf-8") as f:
            f.write(content)
        print(f"Updated {filepath}")
