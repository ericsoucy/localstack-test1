# dynamodb
resource "aws_dynamodb_table" "ProductInfo_table" {
  name           = "ProductInfo"
  hash_key       = "id"
  billing_mode   = "PROVISIONED"
  write_capacity = 10
  read_capacity  = 10
  attribute {
    name = "id"
    type = "S"
  }

}