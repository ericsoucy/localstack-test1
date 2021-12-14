# Create Bucket
resource "aws_s3_bucket" "b" {
  bucket = "my-shitty-bucket-terraform"
  acl    = "public-read"
}