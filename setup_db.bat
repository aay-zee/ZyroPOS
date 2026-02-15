@echo off
echo Building and Running Database Setup...
java "-Dmaven.multiModuleProjectDirectory=." -classpath ".mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain compile exec:java -Dexec.mainClass="database.DatabaseSetup"
if %ERRORLEVEL% NEQ 0 (
    echo Database Setup Failed!
    pause
    exit /b %ERRORLEVEL%
)
echo Database Setup Completed Successfully.
pause
