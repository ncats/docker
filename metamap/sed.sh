#!/bin/sh
sed 's/^read/##/g' bin/install.sh > bin/install_ncats.sh
chmod +x bin/install_ncats.sh
./bin/install_ncats.sh
