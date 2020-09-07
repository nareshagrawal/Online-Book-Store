#!/bin/bash
set -x
sudo chmod 755 /var/applicationstart.sh
sudo cp /var/restartwebapp.service /etc/systemd/system/
sudo chmod +x /etc/systemd/system/restartwebapp.service
sudo systemctl daemon-reload
sudo systemctl enable restartwebapp.service
sudo kill -9 $(sudo lsof -t -i:8080)
sudo kill -9 $(sudo lsof -t -i:8080)
set +x