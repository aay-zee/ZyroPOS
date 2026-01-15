@echo off
echo Running ZyroPOS...
java "-Dmaven.multiModuleProjectDirectory=." -classpath ".mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain javafx:run
if %ERRORLEVEL% NEQ 0 (
    echo Application exited with error!
    pause
    exit /b %ERRORLEVEL%
)
pause
