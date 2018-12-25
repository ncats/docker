#
# Building MetaMap docker image. First, download a full version of MetaMap
# and Java API at https://metamap.nlm.nih.gov/MainDownload.shtml. To build
# the image, run the following command:
#   docker build --build-arg VERSION=2016v2 -t ncats/metamap .
# change VERSION to reflect your downloaded version
FROM ubuntu:latest
ARG VERSION=2016v2
ARG MM=public_mm_linux_main_$VERSION.tar.bz2
ARG API=public_mm_linux_javaapi_$VERSION.tar.bz2
ARG INSTALL=/metamap
RUN apt-get update && \
    apt-get install -y openjdk-8-jre nginx libnginx-mod-stream && \
    rm -rf /var/lib/apt/lists/* && \
    mkdir $INSTALL
ADD $MM $INSTALL
ADD $API $INSTALL
ADD setup.sh $INSTALL
ADD nginx.conf /etc/nginx
ADD start.sh /
WORKDIR $INSTALL/public_mm
RUN /bin/bash -c '../setup.sh'
EXPOSE 18066
ENTRYPOINT ["/start.sh"]
