#
# Building MetaMap docker image. First, download a full version of MetaMap
# at https://metamap.nlm.nih.gov/MainDownload.shtml. To build the image,
# run the following command:
# docker build --build-arg MM=public_mm_linux_main_XXXXX.tar.bz2 \
#     -t ncats/metamap .

FROM ubuntu:latest
ARG MM=public_mm_linux_main_2016v2.tar.bz2
ARG INSTALL=/metamap
RUN apt-get update && apt-get install -y openjdk-8-jre
RUN mkdir $INSTALL
ADD $MM $INSTALL
ADD sed.sh $INSTALL
ADD start.sh $INSTALL
WORKDIR $INSTALL/public_mm
RUN /bin/bash -c '../sed.sh'
VOLUME $INSTALL
CMD ["/bin/bash"]
