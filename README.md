# Test localstack

```bash

// run localstack
podman run --rm -it -p 4566:4566 -p 4571:4571 localstack/localstack
curl http://localhost:4566/health | jq

// install aws cli
sudo curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
sudo unzip awscliv2.zip
sudo ./aws/install
aws --version
aws configure --profile local
aws --endpoint-url=http://localhost:4566 kinesis list-streams --profile local
aws --endpoint-url=http://localhost:4566 lambda list-functions --profile local
```

## Infrastructure Setup using Localstack and Terraform
<https://www.youtube.com/watch?v=2IUrUkH4mvQ>

<https://www.unixarena.com/2021/06/terraform-x509-certificate-error-terraform-in-an-offline-mode.html/>

wget https://releases.hashicorp.com/terraform-provider-aws/3.68.0/terraform-provider-aws_3.68.0_linux_amd64.zip --no-check-certificate
mkdir -p terraform.d/plugins/registry.terraform.io/terraform-provider-aws/3.68.0/linux_amd64

```bash
terraform init
terraform plan
```