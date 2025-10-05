@echo off
echo Building Docker image for Food Order System...

REM Build the Docker image
docker build -t food-order-system:latest .

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Docker image built successfully!
    echo.
    echo To run the application:
    echo   docker-compose up -d
    echo.
    echo To run only the app:
    echo   docker run -p 8080:8080 food-order-system:latest
    echo.
) else (
    echo.
    echo ❌ Docker build failed!
    echo.
)

pause


