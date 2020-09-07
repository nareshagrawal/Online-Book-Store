# AWS Networking Setup

## Infrastructure as Code with Terraform

### Prerequisites for building infrastructure stack locally
- Download terrafrom from offical website HashiCrop
- Unzip it in root folder
- Install AWS cli
```
$ curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
$ unzip awscliv2.zip
$ sudo ./aws/install
```
- Configuring AWS cli by setting up profile 

### Build Instructions
- Run commands 
```
 $ terraform initi
 $ terraform plan
 $ terraform apply
```

### Destroy Instruction 
- Run command
```
 $ terraform destroy
```

### Import Ceritificate Using AWS CLI
Use following command to import a certificate using the AWS Command Line Interface (AWS CLI)

```bash
$ openssl x509 -in <youCrtFile>.crt -out <youCertName>.pem
$ openssl x509 -in <yourCaBundleFile>.ca-bundle -out <yourCertChainName>.pem
$ sudo aws acm import-certificate --certificate file://<youCertName>.pem --certificate-chain file://<yourCertChainName>.pem --private-key file:/<yourPrivateKey>.key
```