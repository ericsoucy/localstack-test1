resource "aws_sns_topic" "order_created" {
  name = "order-created-topic"
}

resource "aws_sqs_queue" "order_queue" {
  name = "order-queue"
}

resource "aws_sqs_queue" "order_queue_2" {
  name = "order-queue-2"
}

resource "aws_sns_topic_subscription" "sub_queue_order_created_topic" {
  endpoint  = aws_sqs_queue.order_queue.arn
  protocol  = "sqs"
  topic_arn = aws_sns_topic.order_created.arn
}

resource "aws_sns_topic_subscription" "sub_queue2_order_created_topic" {
  endpoint  = aws_sqs_queue.order_queue_2.arn
  protocol  = "sqs"
  topic_arn = aws_sns_topic.order_created.arn
}