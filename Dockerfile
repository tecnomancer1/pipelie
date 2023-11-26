# Use an official Maven runtime as a parent image
FROM amazoncorretto:21-al2023 AS build

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the project files into the container at /usr/src/app
COPY . .

# Install Maven (if not already installed in the base image)
RUN dnf update && \
    dnf install -y maven

# Build the application
RUN mvn clean install

FROM amazonlinux:2023

ARG version=21.0.1.12-1
ARG package_version=2

RUN set -eux \
    && rpm --import file:///etc/pki/rpm-gpg/RPM-GPG-KEY-amazon-linux-2023 \
    && echo "localpkg_gpgcheck=1" >> /etc/dnf/dnf.conf \
    && CORRETO_TEMP=$(mktemp -d) \
    && pushd ${CORRETO_TEMP} \
    && RPM_LIST=("java-21-amazon-corretto-headless-$version.amzn2023.${package_version}.$(uname -m).rpm" "java-21-amazon-corretto-$version.amzn2023.${package_version}.$(uname -m).rpm" "java-21-amazon-corretto-devel-$version.amzn2023.${package_version}.$(uname -m).rpm" "java-21-amazon-corretto-jmods-$version.amzn2023.${package_version}.$(uname -m).rpm") \
    && for rpm in ${RPM_LIST[@]}; do \
    curl --fail -O https://corretto.aws/downloads/resources/$(echo $version | tr '-' '.')/${rpm} \
    && rpm -K "${CORRETO_TEMP}/${rpm}" | grep -F "${CORRETO_TEMP}/${rpm}: digests signatures OK" || exit 1; \
    done \
    && dnf install -y ${CORRETO_TEMP}/*.rpm \
    && popd \
    && rm -rf /usr/lib/jvm/java-21-amazon-corretto.$(uname -m)/lib/src.zip \
    && rm -rf ${CORRETO_TEMP} \
    && dnf clean all \
    && sed -i '/localpkg_gpgcheck=1/d' /etc/dnf/dnf.conf

# Install essential utilities
RUN dnf install -y procps net-tools

# Install Nginx
RUN dnf install -y nginx

# Remove the default Nginx configuration
RUN rm -f /etc/nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Copy the custom Nginx configuration
COPY nginx/nginx.conf /etc/nginx/nginx.conf

# Set environment variables
ENV LANG C.UTF-8
ENV JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the JAR file from the build stage to the runtime image
COPY --from=build /usr/src/app/target/pipeline.jar ./pipeline.jar

# Copy the target directory
COPY --from=build /usr/src/app/target /usr/src/app/target

# Expose the port the app runs on
EXPOSE 8080
EXPOSE 80

# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]

# Define the command to run the application
CMD ["java", "-jar", "pipeline.jar"]

# Run the Java application in an infinite loop to keep the container running
CMD ["sh", "-c", "while :; do sleep 60; done"]
