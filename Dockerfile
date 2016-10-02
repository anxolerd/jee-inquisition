FROM anapsix/alpine-java:8_jdk
RUN apk add --update python
RUN mkdir /inquisition
WORKDIR /inquisition
ADD . /inquisition
