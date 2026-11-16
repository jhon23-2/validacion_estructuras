@echo off
echo Compilando proyecto...
javac -encoding UTF-8 -d bin -sourcepath src src/app/Main.java
if %errorlevel% equ 0 (
    echo Compilacion exitosa!
) else (
    echo Error en la compilacion
    exit /b 1
)
