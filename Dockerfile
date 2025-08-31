FROM openjdk:17-jdk-slim

# 設置工作目錄
WORKDIR /app

# 複製 Maven 包裝器和 pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 賦予 Maven 包裝器執行權限
RUN chmod +x ./mvnw

# 下載依賴（利用 Docker 層緩存）
RUN ./mvnw dependency:go-offline -B

# 複製源代碼
COPY src ./src

# 構建應用
RUN ./mvnw clean package -DskipTests

# 暴露端口
EXPOSE 8080

# 運行應用
CMD ["java", "-jar", "target/playground-module-0.0.1-SNAPSHOT.jar"]