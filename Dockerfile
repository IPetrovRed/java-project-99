FROM gradle:8.6.0-jdk21

WORKDIR /app

COPY / .

RUN gradle installDist

CMD ./build/install/app/bin/app