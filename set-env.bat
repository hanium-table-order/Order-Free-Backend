@echo off
set DB_HOST=your-aws-rds-endpoint.amazonaws.com
set DB_PORT=3306
set DB_NAME=your_database_name
set DB_USER=your_username
set DB_PASS=your_password

echo Environment variables set:
echo DB_HOST=%DB_HOST%
echo DB_PORT=%DB_PORT%
echo DB_NAME=%DB_NAME%
echo DB_USER=%DB_USER%

gradlew bootRun
