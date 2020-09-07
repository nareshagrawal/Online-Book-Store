#!/bin/bash
set -x
cd /var
sudo rm -f webapps-0.0.1-SNAPSHOT.war
sudo rm -f webapps.log
cd ~
sleep 5
set +x