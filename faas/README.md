# Faas (Serverless)

## Objective
* Send email to the user based on the timestamp of the last email sent
* The function subscribes to the AWS - SNS service

## Prerequisites for building and deploying application locally

* Insall AWS CLI
```
$ curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
$ unzip awscliv2.zip
$ sudo ./aws/install
```
* Configuring AWS CLI by setting up profile 
* Create your Stack on AWS

## Build and Deploy using CircleCI (CI\CD)

* Currently using Cirle CI tools to build new artifacts of function on each commit on GitHub
* Configure your faas repository in Circle CI and follow the project
* Setup project environment variable for aws_access_key, aws_secret_key and aws_region
