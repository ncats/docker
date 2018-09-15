#!/bin/sh
sed 's/^AVAILABLE_DATA="2006 2015"/AVAILABLE_DATA="2015"/g' bin/install.sh  | sed 's/^read DELETE_DATA/DELETE_DATA="y"/g' | sed 's/^read/##read/g' > bin/install_ncats.sh
chmod +x bin/install_ncats.sh
./bin/install_ncats.sh

