@echo off
echo Starting Food Order System with Docker Compose...

REM Start all services
docker-compose up -d

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Services started successfully!
    echo.
    echo 🌐 Application: http://localhost:8080
    echo 📊 Swagger UI: http://localhost:8080/swagger-ui.html
    echo 🗄️  H2 Console: http://localhost:8080/h2-console
    echo.
    echo To view logs:
    echo   docker-compose logs -f
    echo.
    echo To stop services:
    echo   docker-compose down
    echo.
) else (
    echo.
    echo ❌ Failed to start services!
    echo.
)

pause


