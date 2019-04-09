
IF %1.==. GOTO NoParam1
javac *.java
java GraphicalApp "%*"
GOTO End

:NoParam1
ECHO Requires Car As Argument

:End