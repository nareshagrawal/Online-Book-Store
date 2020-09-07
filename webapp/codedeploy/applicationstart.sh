#!/bin/bash
cd /var
sudo java -jar webapps-0.0.1-SNAPSHOT.war > /dev/null 2> /dev/null < /dev/null &
cd ~
sleep 130
sudo systemctl restart amazon-cloudwatch-agent
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/opt/amazon-cloudwatch-agent.json \
    -s
