# AMI

## Objective
Building Custom AMI using Packer

### Prerequisites for building and deploying application locally
- Download Packer from offical website HarshiCorp
```bash
$ sudo apt-get update && sudo apt-get install packer
```
- Unzip it in working directory
- Give it permission by running command 
```bash
$ chmod +x packer
```
- Pass the aws_access_ key & aws_secret_key of the IAM user, aws_region and subnet_id
- Run commands
-  ./ packer validate ami-template.json
-  ./packer build \
       -var 'aws_access_key=REDACTED' \
       -var 'aws_secret_key=REDACTED' \
       -var 'aws_region=REDACTED' \
       -var 'subnet_id=REDACTED' \
       ami-template.json

### Build and Deploy using CircleCI (CI\CD)
- Currently using Cirle CI tools to build new artifacts on each commit on GitHub
- Configure your ami repository in Circle CI and follow the project
- Setup project environment variable for aws_access_key, aws_secret_key, aws_region and subnet_id


