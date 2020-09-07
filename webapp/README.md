# webapp

## Technology Stack
- Operating System - Linux (Ubuntu 18.04.3 LTS)
- Programming Language - Java
- Relational Database - MySQL
- Backend Framework - Spring and Hibernate

## Building and deploying application locally
- Build the Application using Maven Build
- Run the "webapps.java" as Java Application
- When the Application successfully deployes open web browser and go to "localhost:8080"
- You will need to register first and you can login

## Running Tests
- Run the "webappsTest.java" file as a JUnit Test

## Build and Deploy using CircleCI (CI\CD)
- Currently using Cirle CI tools to build new artifacts on each commit and run test cases on GitHub
- Configure your webapp repository in Circle CI and follow the project
- Setup project environment variable for aws_access_key, aws_secret_key and aws_region
- Create circleci user in AWS IAM
- Create New S3 Bucket to hold the build artifacts
- Assign appropriate policies to allow circleci user to access and upload artifacts in S3 Bucket
- Create Code Deploy Application to deploy new revisions onto EC2 instances for each new build