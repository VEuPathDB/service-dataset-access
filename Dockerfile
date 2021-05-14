# # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
#
#   Build Service & Dependencies
#
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
FROM veupathdb/alpine-dev-base:jdk-15 AS prep

LABEL service="demo-service"

WORKDIR /workspace
RUN jlink --compress=2 --module-path /opt/jdk/jmods \
       --add-modules java.base,java.logging,java.xml,java.desktop,java.management,java.sql,java.naming,java.security.jgss \
       --output /jlinked \
    && apk add --no-cache git sed findutils coreutils make npm curl \
    && git config --global advice.detachedHead false \
    && wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub \
    && wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.33-r0/glibc-2.33-r0.apk \
    && rm -rf lib64/ld-linux-x86-64.so.2 \
    && apk add -f glibc-2.33-r0.apk

#RUN ls /opt/jdk; exit 1

ENV DOCKER=build \
    JAVA_HOME=/opt/jdk

COPY makefile .

#RUN update-ca-certificates -f

RUN make install-dev-env

COPY . .

RUN mkdir -p vendor \
    && cp -n /jdbc/* vendor \
    && echo Installing Gradle \
    && ./gradlew dependencies --info --configuration runtimeClasspath \
    && make jar

# # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
#
#   Run the service
#
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
FROM foxcapades/alpine-oracle:1.3

LABEL service="demo-service"

ENV JAVA_HOME=/opt/jdk \
    PATH=/opt/jdk/bin:$PATH

COPY --from=prep /jlinked /opt/jdk
COPY --from=prep /workspace/build/libs/service.jar /service.jar

CMD java -jar /service.jar


# Hi, this is Ellie Harper.  I am calling to set up a new patient appointment.  I was recommended
# Dr. Compendio by my therapist, Sarah Leboon.  I am specifically looking for continued med
# management as my psych retired.  My phone number is 302-525-1151.  Thank you.