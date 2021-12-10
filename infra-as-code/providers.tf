terraform {
  required_providers {
    aws = {
      source  = "terraform-local/aws"
      version = "3.68.0"

    }
  }
}
variable "awsregion" {
  type    = string
  default = "ca-central-1"
}
provider "aws" {
  profile                     = "local"
  region                      = var.awsregion
  access_key                  = "test"
  secret_key                  = "test"
  s3_force_path_style         = true
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    acm             = "http://localhost:4566"
    apigateway      = "http://localhost:4566"
    cloudformation  = "http://localhost:4566"
    cloudwatch      = "http://localhost:4566"
    config          = "http://localhost:4566"
    dynamodb        = "http://localhost:4566"
    dynamodbstreams = "http://localhost:4566"
    ec2             = "http://localhost:4566"
    es              = "http://localhost:4566"
    events          = "http://localhost:4566"
    firehose        = "http://localhost:4566"
    iam             = "http://localhost:4566"
    kinesis         = "http://localhost:4566"
    kms             = "http://localhost:4566"
    lambda          = "http://localhost:4566"
    #logs = "http://localhost:4566"
    redshift = "http://localhost:4566"
    #resource-groups = "http://localhost:4566"
    resourcegroupstaggingapi = "http://localhost:4566"
    route53                  = "http://localhost:4566"
    route53resolver          = "http://localhost:4566"
    s3                       = "http://localhost:4566"
    secretsmanager           = "http://localhost:4566"
    ses                      = "http://localhost:4566"
    sns                      = "http://localhost:4566"
    sqs                      = "http://localhost:4566"
    ssm                      = "http://localhost:4566"
    stepfunctions            = "http://localhost:4566"
    sts                      = "http://localhost:4566"
    support                  = "http://localhost:4566"
    swf                      = "http://localhost:4566"

  }

}

# Create Bucket
resource "aws_s3_bucket" "b" {
  bucket = "my-shitty-bucket-terraform"
  acl    = "public-read"
}
