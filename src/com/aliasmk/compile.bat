@echo off
:start
echo Compling Resistor.java...
javac -classpath ../../ resistor.java
echo Compling gui.java...
javac -classpath ../../ gui.java
echo Compile Attempt Complete
pause
goto start
