cd .. & ^
mvnw clean package -DskipTests && ^
copy target\moneymanager-0.0.1-SNAPSHOT.jar docker\services\app && ^
cd docker/services/app && ^
docker build -t money-manager-backend .