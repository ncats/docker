#
# Building SemRep docker image. This docker file assumes that you've already installed
# the [MetaMap docker image](../metamap/Dockerfile). To build this image, please download
# the following files from https://semrep.nlm.nih.gov/SemRep.v1.7_Installation.html:
# + https://semrep.nlm.nih.gov/download/public_semrep_v1.7_Linux.tar.bz2
# + https://semrep.nlm.nih.gov/download/public_semrep_v1.7_lex_db_15_Linux.tar.bz2
# + https://semrep.nlm.nih.gov/download/public_semrep_v1.7_hier_15_Linux.tar.bz2
# These files must be in the same directory as the Dockerfile. Now run
#  docker build -t ncats/semrep .
# to build the image.
FROM ncats/metamap
ARG INSTALL=/metamap
ARG SEMREP_DIR=$INSTALL/public_semrep
ARG SEMREP_DB=public_semrep_v1.7_lex_db_15_Linux.tar.bz2
ARG SEMREP_HIER=public_semrep_v1.7_hier_15_Linux.tar.bz2
ARG SEMREP=public_semrep_v1.7_Linux.tar.bz2
ADD $SEMREP $INSTALL
ADD $SEMREP_DB $INSTALL
ADD $SEMREP_HIER $INSTALL
ADD setup.sh $INSTALL
ADD start.sh /
# this assumes (cd semrep-rest-api && ./gradlew dist)
ADD semrep-rest-api/build/distributions/playBinary.tar $SEMREP_DIR
WORKDIR $SEMREP_DIR
RUN /bin/bash -c '../setup.sh'
EXPOSE 8067
ENTRYPOINT ["/start.sh"]
