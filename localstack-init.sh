#!/bin/bash

echo "Initializing LocalStack S3..."

export AWS_ACCESS_KEY_ID=connect-pro
export AWS_SECRET_ACCESS_KEY=connect-pro
export AWS_DEFAULT_REGION=us-east-1

AWS_CMD="aws --endpoint-url=http://localhost:4566 --region us-east-1"

# Attendre que LocalStack soit prêt
until $AWS_CMD s3 ls 2>/dev/null; do
  echo "Waiting for LocalStack..."
  sleep 2
done

# Créer le bucket seulement s'il n'existe pas déjà
if $AWS_CMD s3api head-bucket --bucket connect-pro-images 2>/dev/null; then
  echo "Bucket 'connect-pro-images' already exists, skipping creation."
else
  $AWS_CMD s3 mb s3://connect-pro-images
  echo "Bucket 'connect-pro-images' created successfully!"

  $AWS_CMD s3api put-bucket-acl \
      --bucket connect-pro-images \
      --acl public-read

  echo "Permissions set."
fi

echo "LocalStack S3 initialization complete."



##!/bin/bash
#
#echo "Initializing LocalStack S3..."
#
## Attendre que LocalStack soit prêt
#until aws --endpoint-url=http://localhost:4566 s3 ls 2>/dev/null; do
#  echo "Waiting for LocalStack..."
#  sleep 2
#done
#
## Créer le bucket seulement s'il n'existe pas déjà
#if aws --endpoint-url=http://localhost:4566 \
#       --region us-east-1 \
#       s3api head-bucket --bucket connect-pro-images 2>/dev/null; then
#  echo "Bucket 'connect-pro-images' already exists, skipping creation."
#else
#  aws --endpoint-url=http://localhost:4566 \
#      --region us-east-1 \
#      s3 mb s3://connect-pro-images
#
#  echo "Bucket 'connect-pro-images' created successfully!"
#
#  # Configurer les permissions publiques
#  aws --endpoint-url=http://localhost:4566 \
#      --region us-east-1 \
#      s3api put-bucket-acl \
#      --bucket connect-pro-images \
#      --acl public-read
#fi