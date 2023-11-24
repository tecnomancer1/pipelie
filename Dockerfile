# Use an official Maven runtime as a parent image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the project files into the container at /usr/src/app
COPY . .

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

# Install Python
RUN dnf install -y python3

# Set environment variables for Python
ENV PYTHONPATH=/usr/src/app/python_modules
ENV PYTHONUNBUFFERED=1
ENV PATH=${PATH}:/usr/bin/python3

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

CMD java -jar pipeline.jar

# Define the command to run the application
#CMD ["java", "-jar", "pipeline.jar"]

CMD ["sh", "-c", "java -jar pipeline.jar & sleep 60"]

# Run the Java application in an infinite loop to keep the container running
#CMD ["sh", "-c", "java -jar pipeline.jar"]
#CMD ["sh", "-c", "java -jar app.jar && tail -f /dev/null"]
