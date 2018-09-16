#!/bin/sh
cd /metamap/public_mm
./bin/skrmedpostctl start
./bin/wsdserverctl start
# allow time for the two servers to start
sleep 30
./bin/mmserver16 &
cd /metamap/public_semrep
# now invoke rest api server
./playBinary/bin/playBinary
