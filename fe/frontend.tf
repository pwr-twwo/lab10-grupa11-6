terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

# Configure the AWS Provider
provider "aws" {
  region = "us-east-1"
}

resource "aws_s3_bucket" "terrafrombucket" {
  bucket = "resumebucketbyterraformuoihiuhha" 
}

resource "aws_s3_bucket_cors_configuration" "terrafrombucket" {
  bucket = aws_s3_bucket.terrafrombucket.id

  cors_rule {
    allowed_headers = ["*"]
    allowed_methods = ["GET", "HEAD"]
    allowed_origins = ["*"]
    expose_headers  = ["ETag", "x-amz-meta-custom-header", "Authorization", "Content-Length", "Content-Type"]
    max_age_seconds = 3000
  }
}

resource "aws_s3_bucket_ownership_controls" "ownership" {
  bucket = aws_s3_bucket.terrafrombucket.id
  rule {
    object_ownership = "BucketOwnerPreferred"
  }
}

resource "aws_s3_bucket_public_access_block" "publiceaccess" {
  bucket = aws_s3_bucket.terrafrombucket.id
  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_acl" "bucket_acl" {
  depends_on = [
    aws_s3_bucket_ownership_controls.ownership,
    aws_s3_bucket_public_access_block.publiceaccess,
  ]

  bucket = aws_s3_bucket.terrafrombucket.id
  acl    = "public-read"
}

resource "aws_s3_object" "index" {
  bucket = aws_s3_bucket.terrafrombucket.id
  key    = "index.html"
  source = "index.html"
  content_type = "text/html"
  
  metadata = {
  content-security-policy = "default-src https: http:;"
  }
}


resource "aws_s3_object" "assets_folder" {
  for_each = fileset("assets", "**/*")
  bucket = aws_s3_bucket.terrafrombucket.id
  key    = each.key
  source = "assets/${each.key}"
  
  metadata = {
  content-security-policy = "default-src https: http:;"
  }
}

resource "aws_s3_bucket_website_configuration" "example" {
  bucket = aws_s3_bucket.terrafrombucket.id

  index_document {
    suffix = "index.html"
  }


  routing_rule {
    condition {
      key_prefix_equals = "assets/"
    }
    redirect {
      replace_key_prefix_with = "assets/"
    }
  }
}

resource "aws_s3_bucket_policy" "public_read_access" {
  bucket = aws_s3_bucket.terrafrombucket.id
  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:*",
      "Resource": [
        "${aws_s3_bucket.terrafrombucket.arn}",
        "${aws_s3_bucket.terrafrombucket.arn}/*"
      ]
    }
  ]
}
EOF
}


