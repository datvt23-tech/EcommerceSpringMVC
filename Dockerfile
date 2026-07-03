# ===========================
# Build
# ===========================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# ===========================
# Runtime
# ===========================
FROM tomcat:9.0-jdk17-temurin

# Xóa ứng dụng mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Render sẽ truyền biến PORT
ENV PORT=8080

# Đổi port Tomcat thành port Render cấp
RUN sed -i 's/port="8080"/port="${PORT}"/' /usr/local/tomcat/conf/server.xml

EXPOSE 8080

CMD ["catalina.sh","run"]